$(document).ready(function() {
	//Show infomation tab panel after general information submission
	/*$("#gen").click(function(event) {
		
	});*/

	
	$('#info-tabs a').click(function (e) {
		alert("hello");
  		e.preventDefault();
  		$(this).tab('show');
	});

});