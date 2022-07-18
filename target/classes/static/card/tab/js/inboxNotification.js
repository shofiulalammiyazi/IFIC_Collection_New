var inbox_active_counter=-1;

$(document).ready(function() {

		var inbox_active_counter_t= $("#flag_inbox").val();
		console.log("inbox_active="+inbox_active_counter_t)
		if(inbox_active_counter_t!=undefined){
			inbox_active_counter=inbox_active_counter_t;	
		}
	inboxTaskCountList();
	
	//ajax for document List 
	

	/*$(document).on('click','.pend-task-list',function(event){
		
=======
	$(document).on('click','.pend-task-list',function(event){
        /*document.location.href = "/task/list/pending";*/
     /*
		var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
		var ctg = $(this).siblings('span').first().text();
		window.location.href = "http://localhost:8081/task/list/pending";
		
		event.preventDefault();
		//alert("mf"+ctg)
		
		var moduleName=$("#module_name").val();

		var urlp='/task/list/fetch/list_extend?ctg='+ctg;
		$.ajax({
			url: urlp,
			type: 'GET',
			//data: {'ctg': ctg},
			beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
		})
		.done(function(data) {
			var table = $("#example1").DataTable();
			//remove all row from table
			table.rows().remove().draw();
			
			$.each(data, function(i, val) {
				 if (val.refId!=null) {

					 if(moduleName=="card"){
						 var ref_id = '<a href="/card/cardDetails?cdi='+val.loanId+'&pid='+val.procId+'&tc='+ctg+'">'+val.refId+'</a>';	 
					 }
					 else{
						 var ref_id = '<a href="/loan/loanDetails?rdi='+val.loanId+'&pid='+val.procId+'&tc='+ctg+'">'+val.refId+'</a>';
					 }
					 
					
				 	 //console.log(val.customerName+"---"+val.proposedLimit+"---"+val.cardType);
					 table.row.add([ref_id,val.customerName,val.proposedLimit,val.cardType,val.status]);
				 }
			});
			//repopulate the table
			table.draw();
		})
		.fail(function(jqXHR,exception) {
			// var error = errorMessage(jqXHR,exception);
			//console.log(exception.toString());
		})
		.always(function() {
			console.log("complete");
		});
		
	});*/
});



function inboxPopUp(e) {
	/*console.log($("#target_"+e).find("span")[0]);*/
	
	
	var ref="/task/list/fetch/list_extend?ctg="+$("#cat_"+e).text()+"&flag="+e;
	
	window.location.href=ref;
	//$("#target_"+e).addClass("active");
	}


/*$("#inbox-click").click(function (){
	document.location.href = "/task/list/pending";
});*/

function inboxTaskCountList(){

	
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	 var ctg = $(".pend-task-list").siblings('span').first().text();
	 console.log("inbox data="+ctg);
	 var parentInfoFormData="";
	// console.log(parentInfoFormData);
	    $.ajax({
	        url: '/task/task-list-for-inbox',
	        type: 'GET',
	        contentType: 'application/json',
	        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
	        },
	        success: function (data) {

	        	var select = $("#pending-task-tree").find('ul');
	    		select.empty();
	    		$.each(data, function(i, val) {
	    			var list = '<li  id="target_'+i+'"><span class="hide" id="cat_'+i+'">'+val.category+'</span><a href="#" class="pend-task-list" onclick="javascript:inboxPopUp('+i+')" >'+(val.name).replace(/_/g," ")+'  ('+val.count+')</a></li>';
	    			select.append(list);
	    			/*var link = window.location;
	    			console.log("link:" + link);
	    			var menu_link = $('.treeview>.treeview-menu>li');
	    			$(menu_link).each(function(index, element){
	    				var $activeLink = $(element).find('a');
	    				if( $activeLink.attr('href') == link.pathname ) {
	    					$activeLink.parents('li').addClass('active');
	    				}

	    			});*/
	    			//select.append($('<li></li>').attr('value', val.code).text(val.name));
	    		});
	    		
	    		//console.log("in data return ="+inbox_active_counter)
	    		if(inbox_active_counter != -1){
	    			$("#target_"+inbox_active_counter).addClass("active");
	    			$("#pending-task-tree").addClass("active");
	    			
	    			
	    			
	    		}
	    		
	    		
	    		
	        },
	        error: function (err) {
	            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
	                "\t\t\t\t\t</div>");
	            var message = "<ul style='list-style-type:disc'><li>Error in saving my Info!</li></ul>";
	            $("#specialAlert").addClass("errorDiv");
	            $("#specialAlert").append(message);
	            $("#exampleModalLong").modal("toggle");
	            console.log(err);
	        }
	    });

	
}

