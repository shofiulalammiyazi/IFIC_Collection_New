$(document).ready(function() {

	$('#exe-other-tab-head').click(function(event) {

		var _loanId = $('.gen-loanId-basic').val();

		event.preventDefault();

		$.ajax({
			url: '/exctv/loan/exsum/other/view',
			type: 'GET',
			//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
			data: {'lId': _loanId},
		})
		.done(function(data) {

			var table = $('.executive-other-info').find('tbody');
			table.find('#lloan').text(data.lLoan);
			table.find('#esb').text(data.esb);
			table.find('#der').text(data.der);
			table.find('#cib').text(data.cib);
			table.find('#crg').text(data.crg);
			table.find('#dfis').text(data.dfis);

		})
		.fail(function() {
			console.log("error");
		})
		.always(function() {
			console.log("complete");
		});
		

	});

	var lib_header = true;
	$('#exe-group-liab').click(function(event) {

		if(lib_header){
			lib_header=false;
			var scope = $('#groupLiability');
			var _loanId = $('.gen-loanId-basic').val();
			scope.find('.executive-groupLiability').removeClass('hide');

			$.ajax({
				url: '/pp/liability/view',
				type: 'GET',
				data: {'lId': _loanId},
			})
			.done(function(data) {
				var tbody = scope.find('tbody');

	            //clear table
	            /*tbody.find('#wob-borrower').nextAll().remove();
	            tbody.find('#wob-allied').nextAll().remove();
	            tbody.find('#fiu-borrower').nextAll().remove();
	            tbody.find('#fiu-allied').nextAll().remove();*/


	            $.each(data, function(index, val) {
	                if(val.origin=='wob'){
	                    switch(val.groupType){
	                        case 'borrower':
	                            var totalLimit =  parseInt(val.funLimit) + parseInt(val.nonLimit);
	                            var totalos =  parseInt(val.funOs) + parseInt(val.nonOs);
	                            var str =   '<td>'+val.funLimit+'</td>'+
	                                    '<td>'+val.funOs+'</td>'+
	                                    '<td>'+val.nonLimit+'</td>'+
	                                    '<td>'+val.nonOs+'</td>'+
	                                    '<td>'+totalLimit+'</td>'+
	                                    '<td>'+totalos+'</td>';
	                                    console.log(str);
	                            tbody.find('#wob-borrower').append(str);
	                            break;
	                        case 'allied':
	                            var totalLimit =  parseInt(val.funLimit) + parseInt(val.nonLimit);
	                            var totalos =  parseInt(val.funOs) + parseInt(val.nonOs);
	                            var str =   '<td>'+val.funLimit+'</td>'+
	                                    '<td>'+val.funOs+'</td>'+
	                                    '<td>'+val.nonLimit+'</td>'+
	                                    '<td>'+val.nonOs+'</td>'+
	                                    '<td>'+totalLimit+'</td>'+
	                                    '<td>'+totalos+'</td>';
	                                    console.log(str);
	                            tbody.find('#wob-allied').append(str);
	                            break;
	                        default :
	                            break;
	                    }
	                }else{
	                    switch(val.groupType){

	                        case 'borrower':
	                            var totalLimit =  parseInt(val.funLimit) + parseInt(val.nonLimit);
	                            var totalos =  parseInt(val.funOs) + parseInt(val.nonOs);
	                            var str =   '<td>'+val.funLimit+'</td>'+
	                                    '<td>'+val.funOs+'</td>'+
	                                    '<td>'+val.nonLimit+'</td>'+
	                                    '<td>'+val.nonOs+'</td>'+
	                                    '<td>'+totalLimit+'</td>'+
	                                    '<td>'+totalos+'</td>';
	                                    console.log(str);
	                            tbody.find('#fiu-borrower').append(str);
	                            break;
	                        case 'allied':
	                            var totalLimit =  parseInt(val.funLimit) + parseInt(val.nonLimit);
	                            var totalos =  parseInt(val.funOs) + parseInt(val.nonOs);
	                            var str =   '<td>'+val.funLimit+'</td>'+
	                                    '<td>'+val.funOs+'</td>'+
	                                    '<td>'+val.nonLimit+'</td>'+
	                                    '<td>'+val.nonOs+'</td>'+
	                                    '<td>'+totalLimit+'</td>'+
	                                    '<td>'+totalos+'</td>';
	                            console.log(str);
	                            tbody.find('#fiu-allied').append(str);
	                            break;
	                        default :
	                            break;
	                    }
	                }
	            });
			})
			.fail(function() {
			})
			.always(function() {
			});

		}

	});


});