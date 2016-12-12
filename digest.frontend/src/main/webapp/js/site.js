	$(document).ready(function() {

		$('#view_topic_form').validate({ // initialize the plugin
			rules : {
				topic_id : {
					required : true
				},
			}
		});
		
		$('#search').keyup(function () {
			var searchField = $('#search').val();
			var regex = new RegExp(searchField,"i");
			var showData = $('#show-data');

			$.ajax({url:'SearchServlet?f=get_topics_of_user&ruid='+searchField,
					dataType: 'json',
					success: function(data){
						
					showData.empty();
					var content = '';
					$.each(data,function(key,val){					
	       				content  += '<a class="list-group-item" href="ViewTopicServlet?topic_id='+val.id+'">' + val.header + '</a>';
				
					});
	        		
					var list = $('<div class="list-group" />').html(content);
	        		showData.append(list);

				}
			});

		});
	});