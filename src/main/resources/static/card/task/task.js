$(document).ready(function() {

	//check path url and show/hide the task option in navigation bar
	var urlPath = window.location.pathname;
	var ctgListUrl = '';
	var taskListUrl = '';
	var dTableHref = '';
	console.log("path : "+urlPath);
	if (urlPath==='/corp/task' || urlPath==='/sme/task') {
		$('#tsk-page').closest('li').first().addClass('hide');
		$('#pending-task-tree').removeClass('hide');
	}else{
		$('#pending-task-tree').addClass('hide');
		$('#tsk-page').closest('li').first().removeClass('hide');
	}


	/*
	*	when DOM is ready below code snipet send
	*	a request to server to get task list
	*	for the current candidate
	*/
	if(urlPath==='/corp/task'){
		ctgListUrl = '/corp/task/ctg/list';
		taskListUrl = '/corp/task/list';
		dTableHref = '<a href="/corp/task/view/details?lId=';
	}else{
		ctgListUrl = '/sme/task/ctg/list';
		taskListUrl = '/sme/task/list';
		dTableHref = '<a href="/sme/task/view/details?lId=';
	}

	$.ajax({
		url: ctgListUrl,
		type: 'GET',
		beforeSend: function (xhr) {
            	xhr.setRequestHeader(header, token);
            },
	})
	.done(function(data) {
		var select = $("#pending-task-tree").find('ul');
		select.empty();
		$.each(data, function(i, val) {
			var list = '<li><span class="hide">'+val.code+'</span><a class="pend-task-list" href="#">'+val.name+'</a></li>';
			select.append(list);
			//select.append($('<li></li>').attr('value', val.code).text(val.name));
		});
		
	})
	.fail(function() {
		console.log("error");
	})
	.always(function() {
		console.log("complete");
	});
	

	// trigger a request by selecting a task
	$(document).on('click','.pend-task-list',function(event){
		event.preventDefault();
		var ctg = $(this).siblings('span').first().text();
		console.log("Task Code "+ ctg);
		$.ajax({
			url: taskListUrl,
			type: 'GET',
			data: {'ctg': ctg},
			beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
		})
		.done(function(data) {
			var table = $("#example1").DataTable();
			//remove all row from table
			table.rows().remove().draw();
			
			$.each(data, function(i, val) {
				 if (val.loanRef!=null) {
				 	var href = dTableHref+val.loanId+'&pId='+val.procId+'">'+val.loanRef+'</a>';
				 	var date = new Date(val.createDate).toLocaleDateString();
				 	table.row.add([href,date,val.status]);
				 }
			});
			//repopulate the table
			table.draw();

		})
		.fail(function(jqXHR,exception) {
			var error = errorMessage(jqXHR,exception);
			console.log(error);
		})
		.always(function() {
			console.log("complete");
		});
		
	});

});
	