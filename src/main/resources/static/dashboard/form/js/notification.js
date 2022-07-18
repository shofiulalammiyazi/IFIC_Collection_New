$('#notification_tab').click(function(){
	$.get('/card/notification/update', function(){});
	$("#total_notification").html("");
	$("#total_notification2").html("no new");
});