package com.digest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;

/**
 * Servlet implementation class CreateTopicServlet
 */
@WebServlet("/CreateTopicServlet")

public class CreateTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTopicServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String f = request.getParameter("f");

		if (f != null && f.contentEquals("create_topic")) {
			String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=create_topic";
			URL connpage = new URL(url);
			HttpURLConnection con = (HttpURLConnection) connpage.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject topic = new JSONObject();
			JSONArray tags = new JSONArray();

			int owner = -1;
			if (session.getAttribute("id") != null)
				owner = (int) session.getAttribute("id");

			topic.put("owner", owner);

			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String attr = names.nextElement();
				if (!(attr.contentEquals("f") || attr.contentEquals("tags"))) {
					String value = request.getParameter(attr);
					topic.put(attr, value);
				} else if (attr.contentEquals("tags")) {
					String tagsString = request.getParameter(attr);
					String[] tagsArray = tagsString.split(",");

					for (String tagString : tagsArray) {
						JSONObject tag = new JSONObject();
						tag.put("tag", tagString);
						tags.put(tag);
					}

					topic.put("tags", tags);
				}
			}
			System.out.println(topic.toString());
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(topic.toString());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			System.out.println(con.getResponseMessage());
			if (responseCode == 200 && request.getParameter("f") != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer res = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					res.append(inputLine);
				}
				in.close();

				if (session.getAttribute("image") != null) {
					session.removeAttribute("image");

				}
				
				if(session.getAttribute("tags")!=null){
					session.removeAttribute("tags");
				}

				session.setAttribute("topic_id", Integer.parseInt(res.toString()));
				response.sendRedirect("ViewTopicServlet");

			} else {
				if (session.getAttribute("image") != null) {
					session.removeAttribute("image");
				}

				String errMsg = "Unexpected Error occured!!";
				request.setAttribute("error", errMsg);
				request.getRequestDispatcher("topic-creation.jsp").forward(request, response);
			}
		} else if (f != null && f.contentEquals("upload_via_url")) {

			if (request.getParameter("image-url") != null && !request.getParameter("image-url").contentEquals("")) {
				session.setAttribute("image", request.getParameter("image-url"));
				response.sendRedirect("topic-creation.jsp");
			}
		} else if (f != null && f.contentEquals("get_tags")) {
			if (request.getParameter("tag") != null && !request.getParameter("tag").contentEquals("")) {
				String tag = request.getParameter("tag");
				String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_tag_entities&tag="
						+ tag;
				
				URL jsonPage = new URL(url);
				HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String line = "";
				String content = "";
				
				while((line = reader.readLine()) != null){
					content += line;
				}
				
				reader.close();
				
				response.getWriter().write(content);
			}
		} else if(f!=null && f.contentEquals("add_tag")){
			if(session.getAttribute("tags") == null){
				ArrayList<String> tags = new ArrayList<String>();
				if(request.getParameter("tag") != null){
					if(request.getParameter("desc")!=null){
						String desc = request.getParameter("desc");
						if(!desc.contentEquals(""))
							tags.add(request.getParameter("tag")+"("+desc+")");
						else
							tags.add(request.getParameter("tag"));
					}
				}
				session.setAttribute("tags", tags);
				
			}else{
				ArrayList<String> tags = (ArrayList<String>) session.getAttribute("tags");
				if(request.getParameter("tag") != null){
					if(request.getParameter("desc")!=null){
						String desc = request.getParameter("desc");
						if(!desc.contentEquals(""))
							tags.add(request.getParameter("tag")+"("+desc+")");
						else
							tags.add(request.getParameter("tag"));
					}
				}
				session.setAttribute("tags", tags);
				
			}
			
			response.sendRedirect(("topic-creation.jsp#show-tags"));
		} else if(f!=null && f.contentEquals("remove_tag")){
			ArrayList<String> tags = (ArrayList<String>) session.getAttribute("tags");
			if(request.getParameter("tag_index") != null){
				int index = Integer.parseInt(request.getParameter("tag_index"));
				tags.remove(index);
			}
			session.setAttribute("tags", tags);
			response.sendRedirect("topic-creation.jsp#show-tags");
			
		} else if(f!=null && f.contentEquals("get_suggested_tags")){
			System.out.println(request.getParameter("body"));
			String body = request.getParameter("body");
			if(body!=null && !body.contentEquals("")){
				String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_tag_suggestions&body="
						+ body;
				
				URL jsonPage = new URL(url);
				HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String line = "";
				String content = "";
				
				while((line = reader.readLine()) != null){
					content += line;
				}
				
				reader.close();
				
				response.getWriter().write(content);
			}
		}

		if (ServletFileUpload.isMultipartContent(request)) {

			int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
			int MAX_FILE_SIZE = 1024 * 1024 * 140; // 140MB
			int MAX_REQUEST_SIZE = 1024 * 1024 * 150; // 150MB
			// needed for cross-domain communication
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Max-Age", "86400");

			// configures upload settings
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(THRESHOLD_SIZE);

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);

			if (session.getAttribute("image") != null) {
				String imageURL = (String) session.getAttribute("image");
				removeImage(imageURL);
			}

			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			String uuidValue = session.getId() + "" + timeStamp.getTime();

			FileItem itemFile = null;

			try {

				// parses the request's content to extract file data
				List formItems = upload.parseRequest(request);
				Iterator iter = formItems.iterator();

				// iterates over form's fields to get UUID Value
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						itemFile = item;
					}
				}

				if (itemFile != null) {
					// get item inputstream to upload file into s3 aws

					AWSCredentials credentials = new BasicAWSCredentials("AKIAIOUSAJ6QDUPDWJEQ",
							"maGgxZxscQaFL37zxM80YMBh7yc9yiiHiY4p6Kxj");
					AmazonS3 s3client = new AmazonS3Client(credentials);
					try {

						ObjectMetadata om = new ObjectMetadata();
						om.setContentLength(itemFile.getSize());
						String ext = FilenameUtils.getExtension(itemFile.getName());
						String keyName = "img/" + uuidValue + '.' + ext;

						s3client.putObject(
								new PutObjectRequest("suzanuskudarli", keyName, itemFile.getInputStream(), om));
						s3client.setObjectAcl("suzanuskudarli", keyName, CannedAccessControlList.PublicRead);

						System.out.println("File upload done");

						session.setAttribute("image", "https://s3.amazonaws.com/suzanuskudarli/" + keyName);
						response.sendRedirect("topic-creation.jsp");

					} catch (AmazonServiceException ase) {
						request.setAttribute("error", ase.getErrorMessage());
						request.getRequestDispatcher("topic-creation.jsp").forward(request, response);

					} catch (AmazonClientException ace) {
						request.setAttribute("error", ace.getMessage());
						request.getRequestDispatcher("topic-creation.jsp").forward(request, response);
					}

				} else {
					String errMsg = "Unexpected Error occured!!";
					request.setAttribute("error", errMsg);
					request.getRequestDispatcher("topic-creation.jsp").forward(request, response);
				}

			} catch (Exception ex) {
				request.setAttribute("error", ex.getMessage());
				request.getRequestDispatcher("topic-creation.jsp").forward(request, response);
			}

		}

	}

	protected void removeImage(String imageURL) {
		String bucketName = "suzanuskudarli";
		String keyName = "";

		Pattern pattern = Pattern.compile("https://s3.amazonaws.com/suzanuskudarli/(.*)$");
		Matcher matcher = pattern.matcher(imageURL);

		if (matcher.find()) {
			keyName = matcher.group(1);

			AWSCredentials credentials = new BasicAWSCredentials("AKIAIOUSAJ6QDUPDWJEQ",
					"maGgxZxscQaFL37zxM80YMBh7yc9yiiHiY4p6Kxj");
			AmazonS3 s3Client = new AmazonS3Client(credentials);

			try {
				s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
			} catch (AmazonServiceException ase) {
				System.out.println("Caught an AmazonServiceException.");
				System.out.println("Error Message:    " + ase.getMessage());
				System.out.println("HTTP Status Code: " + ase.getStatusCode());
				System.out.println("AWS Error Code:   " + ase.getErrorCode());
				System.out.println("Error Type:       " + ase.getErrorType());
				System.out.println("Request ID:       " + ase.getRequestId());
			} catch (AmazonClientException ace) {
				System.out.println("Caught an AmazonClientException.");
				System.out.println("Error Message: " + ace.getMessage());
			}
		}

	}

}