//global html form meta data
/*var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

var submitCurrentForm;
var findViewContainer;
var hideForm;
var showViewPanel;
var showModalMessage;

$(function() {
	//form submition function
	submitCurrentForm = function(obj){
		//initial modal body
		var _modal_enim_body = '<img src="/img/loading.gif"/>';
		$('.content-modal').addModal().setTitle('submit').setBody(_modal_enim_body).showModal();
		//form DOM
		var _form_array = obj.closest('.box-footer').siblings('.box-body').eq(0).find('form');
		var _form = _form_array.get(0);
		var _form_action = _form.action;
		var _form_method = _form.method;

		var _data_type = 'JSON';
		//retrive form data
		var _form_values = _form_array.serializeJSON();
		var _json_value = JSON.stringify(_form_values);
		console.log(_json_value);
		var _request = $.ajax({
			url: _form_action,
			type: _form_method,
			contentType: "application/json",
			dataType: _data_type,
			data: _json_value,
			beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
			});
		return _request; //return AJAX object
	}

    findViewContainer = function(el){
        return el.closest('.box').parent('div').children('div').last();
    }

    hideForm = function(el){
        el.parent('div').children('div').first().hide();
    }

    showViewPanel = function(el){
        el.removeClass('hide');
    }
    showSuccessMessage = function(message){
        $('#form-submition-modal').find('.modal-body').first().empty().append(message);
    }



	//Corporate General Form submittion block start
	//$('.generalView-div').hide();
	$('#corp-gen-submit').click(function(event) {
		event.preventDefault();

		var response = submitCurrentForm($(this));
		//when success
		response.done(function(data){
			
            var image = '<img src="/img/success.gif"/>';
			$('#form-submition-modal').find('.modal-body').first().empty().append("Successfully Saved");
			$('#gen-loanId').val(data.loanId);
			$('#layout-form-panel').removeClass('hide');
            $('.gen-loanId-basic').val(data.loanId);
            
            var gen_scope = $("#layout-general");

            gen_scope.children('div').first().hide();
            
            var gen_view_scope = gen_scope.children('div').last();
            gen_view_scope.removeClass('hide');

            
            gen_view_scope.find('#reviewTypeV').text(data.nop);
            gen_view_scope.find('#acooV').text(data.accNo);
            gen_view_scope.find('#sectorTypeV').text(data.sectorType);
            if(data.sancProposals != null){
            	
            	var _sancScope = gen_view_scope.find("#tb-general-id-New");
            	var totalAmount =0;
            	var totalMargin =0;
                $.each(data.sancProposals, function(index, val) {
                    $('#caseNumber').text(data.caseNumber);
                	totalAmount +=val.amount;
                	totalMargin +=val.margin;
                     var str = "<tr>" +                   
                        "<td>"+val.facility.name+"</td>" +
                        "<td>"+val.amount +"</td>" +
                        "<td>"+val.interestRate +"</td>" +
                        "<td>"+val.margin +"</td>" +
                        "</tr>";
                     _sancScope.append(str);
                });
                var str2 = "<tr class='box-title' style='background-color:#3c8dbc5e'>" +                   
				                "<td>Total : </td>" +
				                "<td>"+totalAmount+"</td>" +
				                "<td></td>" +
				                "<td>"+totalMargin+"</td>" +
			                "</tr>";
             _sancScope.append(str2);
             $('#branchName').text($('#branch').val());
            }else{
            	 gen_view_scope.find('.viewHideSancP').hide();
            }
           
            
            if(data.altSancProposals != null){
                var _altSancScope = gen_view_scope.find("#tb-general-id-Others");
                var totalAmount =0;
            	var totalToAmount =0;
                $.each(data.altSancProposals, function(index, val) {
                 $('#caseNumber').text(data.caseNumber);
                	totalAmount+=val.prevAmount;
                	totalToAmount+=val.modAmount;
                     var str = "<tr>" +
                        "<td>"+val.facility.name+"</td>" +
                        "<td>"+val.prevAmount +"</td>" +
                        "<td>"+val.modAmount +"</td>" +
                        "</tr>";

                        _altSancScope.append(str);
                });
                var str3 = "<tr class='box-title' style='background-color:#3c8dbc5e'>" +                   
				                "<td>Total : </td>" +
				                "<td>"+totalAmount+"</td>" +
				                "<td>"+totalToAmount+"</td>" +
                           "</tr>";
                _altSancScope.append(str3);
                $('#branchName').text($('#branch').val());
            }else{
                gen_view_scope.find('.viewHideSancOther').hide();
            }
            
		});
		
		
		
		//when fail
		response.fail(function(jqXHR, exception){
			var error = errorMessage(jqXHR,exception);
			console.log(exception);
			//$.setBody('<h1>'+error+'</h1>');
			$('#form-submition-modal').find('.modal-body').first().empty().append(error);
		});
		
	});
	//Corporate General Form submittion block end


    /!*
        ============ Executive Summary Basic Information submittion form start =========
    *!/
	//$('.executive-basicView-div').hide();
    $('#exe-basic-info-id').click(function(event) {
        event.preventDefault();

        var response = submitCurrentForm($(this));
        var scope = findViewContainer($(this));

        response.done(function(data){

            var _exe_basic_block = scope.find("#executive-basic-info-view");

            //_exe_basic_block.find("#basicExeId").text(data.basicExeId);
            _exe_basic_block.find('#bAge-view').text(data.bage);
            _exe_basic_block.find('#totalCapital').text(data.totalCapital);
            _exe_basic_block.find('#totalDebt').text(data.totalDebt);
            _exe_basic_block.find('#funded').text(data.funded);
            _exe_basic_block.find('#nonFunded').text(data.nonFunded);
            _exe_basic_block.find('#exv-total-view').text(data.funded+data.nonFunded);

            hideForm(scope);
            showViewPanel(scope);
            showSuccessMessage("Save Successful");

        });
        
        response.fail(function(jqXHR, exception){
            var error = errorMessage(jqXHR,exception);
            console.log(exception);
        });
    });

    /!*
        ============ Executive Summary Basic Information submittion form End =========
    *!/

    
    /!*
        ============ Executive Summary Basic Information initial data puller start =========
    *!/

    $("#exe-summary").click(function(event) {

        _loanId = $('.gen-loanId-basic').val();

        $.ajax({
            url: '/exctv/basic',
            type: 'GET',
            data: {'lId': _loanId},
        })
        .done(function(data) {
            var _basic_exc_block = $("#exctv-basic");

            _basic_exc_block.find('#clientName').text(data.clientName);
            $('#clientNameView').text(data.clientName);
            _basic_exc_block.find('#address').text(data.clientAddress);
            _basic_exc_block.find('#legalStatus').text(data.legalStatus);
            _basic_exc_block.find('#clientNob').text(data.nob);
            $('#clientNobView').text(data.nob);
            _basic_exc_block.find('#keyPerson').text(data.keyPerson);
        })
        .fail(function() {
            console.log("error");
        })
        .always(function() {
            console.log("complete");
        });
        
    });

     /!*
        ============ Executive Summary Basic Information initial data puller start =========
    *!/

});


var errorMessage = function(jqXHR, exception){
	var msg = '';
    if (jqXHR.status === 0) {
        msg = 'Not connect.\n Verify Network.';
    } else if (jqXHR.status == 404) {
        msg = 'Requested page not found. [404]';
    } else if (jqXHR.status == 500) {
        msg = 'Internal Server Error [500].';
    } else if (exception === 'parsererror') {
        msg = 'Requested JSON parse failed.';
    } else if (exception === 'timeout') {
        msg = 'Time out error.';
    } else if (exception === 'abort') {
        msg = 'Ajax request aborted.';
    } else {
        msg = 'Uncaught Error.\n' + jqXHR.responseText;
    }

    return msg;
}

$('#corp-gen-submit').click(function(event) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/corp/businessnature/bnlist',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            for (var key=0; key < data.length; key++){
                console.log(data[key][1]);
                $("#cropbusinessNature").append(
                    "<option value='"+data[key][0]+"'>"+data[key][1]+"</option>"

                );

            }
        }
    });

});
$('#sales-marketing-id').click(function(event) {
    //console.log("Hey man");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/corp/sm-particular/list',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
        	$('.sal-mar-cls').empty();
            for (var key=0; key < data.length; key++){
                console.log(data[key][1]);
                $(".sal-mar-cls").append(
                    "<option value='"+data[key][0]+"'>"+data[key][1]+"</option>"

                );

            }
        }
    });


});
$('#dir-pro-id').click(function(event) {
    console.log("Hey man");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var customerId=$(".customer-cls").val();
    $.ajax({
        url: '/corp/cust/basicinfo/management/profile',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data:{"id":customerId},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);
            var select = $(".man-pro-id");
            $.each(data,function (index, value) {
                select.append($('<option></option>').attr("value", value.mngInfoId).text(value.name));
            });
        }
    });


});*/

function number_format(number, decimals, dec_point, thousands_sep) {
    // Strip all characters but numerical ones.
    number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}


