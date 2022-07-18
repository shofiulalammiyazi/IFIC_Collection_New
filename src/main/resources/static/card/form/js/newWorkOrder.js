$(document).ready(function(){

	$('#newWorkOrderSubmit').click(function(event) {
		event.preventDefault();
		
		var response = submitCurrentForm($(this));
		var scope = findViewContainer($(this));
		
		response.done(function(data){
			$('#form-submition-modal').find('.modal-body').first().empty().append('Successful');	
			var body = scope.find('tbody');
			   if(data.entity != null){
	                 $.each(data.entity, function(index, val) {
	                        var str = "<tr>" +                   
	                         "<td>"+val.nworkOrderAcNo+"</td>" +
	                         "<td>"+val.nauthorityName +"</td>" +
	                         "<td>"+val.reference +"</td>" +
	                         "<td>"+val.fundSource +"</td>" +
	                         "<td>"+val.descriptionWork +"</td>" +
	                         "<td>"+val.workOrderValue +"</td>" +
	                         "<td>"+val.apgRequiredAmount +"</td>" +
	                         "<td>"+val.expiry +"</td>" +
	                         "<td>"+val.deductionVat +"</td>" +
	                         "<td>"+val.deductionAip +"</td>" +
	                         "<td>"+val.deductionSecurity +"</td>" +
	                         "<td>"+val.deductionApg +"</td>" +
	                         "<td>"+val.deductionTotal +"</td>" +
	                         "<td>"+val.netWoValue +"</td>" +
	                         "</tr>";
	                        body.append(str);
	                      
	                 });

	             }
			     hideForm(scope);
				 showViewPanel(scope);
		});

		//when fail
		response.fail(function(jqXHR, exception){
			var error = errorMessage(jqXHR,exception);
			//$.setBody('<h1>'+error+'</h1>');
			$('#form-submition-modal').find('.modal-body').first().empty().append(error);
		});

	});


});