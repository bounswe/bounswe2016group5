package com.digest;

import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
/*import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;*/

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
		// TODO Auto-generated method stub

		String f = request.getParameter("f");
		if (f == null) {

		} else if (f.contentEquals("create_topic")) {
			String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=create_topic";
			URL connpage = new URL(url);
			HttpURLConnection con = (HttpURLConnection) connpage.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject topic = new JSONObject();
			JSONArray tags = new JSONArray();
			JSONArray quizes = new JSONArray();
			JSONArray media = new JSONArray();
			JSONArray comments = new JSONArray();

			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String attr = names.nextElement();
				/*
				 * if(attr.contentEquals("image")){ AWSCredentials credentials =
				 * new BasicAWSCredentials("AKIAIOUSAJ6QDUPDWJEQ",
				 * "maGgxZxscQaFL37zxM80YMBh7yc9yiiHiY4p6Kxj"); AmazonS3
				 * s3client = new AmazonS3Client(credentials);
				 * s3client.putObject(new PutObjectRequest("suzanuskudarli",
				 * "img/2.jpg", new
				 * File(request.getParameter(attr))).withCannedAcl(
				 * CannedAccessControlList.PublicRead)); String imageUrl =
				 * "https://s3.amazonaws.com/suzanuskudarli/img/2.jpg";
				 * topic.put(attr, imageUrl); } else
				 */
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
			topic.put("image", "url");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			System.out.println(topic.toString());
			wr.writeBytes(topic.toString());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			if (responseCode == 200 && request.getParameter("f") != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer res = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					res.append(inputLine);
				}
				in.close();
				System.out.println("topic_id" + Integer.parseInt(res.toString()));
				request.setAttribute("topic_id", Integer.parseInt(res.toString()));
				request.getRequestDispatcher("/ViewTopicServlet").forward(request, response);
				// response.sendRedirect("index.jsp");
			} else if (responseCode == 400) {
				HttpSession session = request.getSession(true);
				String errMsg = "Unexpected Error occured!!";
				session.setAttribute("error", errMsg);
				response.sendRedirect("topic-creation.jsp");
			}
		} else if (ServletFileUpload.isMultipartContent(request)) {
	

		}
		System.out.println("Post succed with " + f + " " + ServletFileUpload.isMultipartContent(request));

	}

}
