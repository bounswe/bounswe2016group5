	$(document).ready(function() {

		$('#view_topic_form').validate({ // initialize the plugin
			rules : {
				topic_id : {
					required : true
				}
			}
		});
		
		$('#search').keyup(function () {
			var searchField = $('#search').val();
			var showData = $('#show-data');
			
			if(searchField === ""){
				showData.empty();
			}
			

			$.ajax({url:'SearchServlet?f=search_with_tag&tag='+searchField,
					dataType: 'json',
					success: function(data){

						
					showData.empty();
					var content = '';
					$.each(data,function(key,val){	
						//window.alert(val.);
	       				content  += '<a class="list-group-item" href="ViewTopicServlet?topic_id='+val.id+'">' + val.header + '</a>';
				
					});
					var list = $('<div class="list-group" />').html(content);
	        		showData.append(list);

				}
			});
			

		});
		
		$('#search-link').on('click',function(){
			
			 $.post( "SearchServlet", { f: "search_page", tag: $('#search').val()},function( data ) {
			 		
				    });
			/*$('#search-link').attr({
				"href": "search.jsp?search_field=" + $('#search').val()
				
				
			});	*/		
		});
		

			$.getJSON('ChannelServlet?f=get_subscribed_channels',function(data){
				var sub_channels = $('#sub_channels');
				var content = '';
				$.each(data,function(key,val){
					if(val != null){
						content = '<li class="list-group-item"><a href="channel.jsp?cid='+val.id+'">'+val.name+'</a></li>';
						sub_channels.append($('<ul class="list-group" />').html(content));
					};
						
				});
			});
			
		
		
		

		
	});