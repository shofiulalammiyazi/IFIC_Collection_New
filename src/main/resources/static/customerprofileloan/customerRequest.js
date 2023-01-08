
$(document).ready(function () {
    $('#customerRequestInfo input[name="_csrf"]').val(csrfToken);
    $('#customerRequestInfoSaveForm input[name="customerBasicInfo"]').val(customerId)
    customerReqeustInfoList(customerId);
})

// $('#tab-gurantor').on('click', function() {
//     customerReqeustInfoList(customerId);
// })

$('#add-customer-request').on('click', function() {
    var formdatalist = $('#customerRequestInfoSaveForm');
    formdatalist.find('input[name="id"]').val('');
    formdatalist.find('input[name="custDate"]').val('');
    formdatalist.find('input[name="custTime"]').val('');
    formdatalist.find('input[name="reqThough"]').val('');
    formdatalist.find('input[name="reqDetails"]').val('');
    formdatalist.find('input[name="mobileNo"]').val('');
    formdatalist.find('input[name="reqTime"]').val('');
})

function customerReqeustInfoList() {
    $('#customerReq_tBody').html('')
    // $.getJSON('/collection/setting/customerRequests/list?id='+customerId, function (json) {
    $.getJSON('/collection/setting/customerRequests/list?customerId='+customerId, function (json) {
        requestList = json;
        var tr = [];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + (json[i].custDate==null?"":json[i].custDate) + '</td>');
            tr.push('<td>' + (json[i].custDate == null?"": json[i].custDate) + '</td>');
            tr.push('<td>' + (json[i].reqThough ==null?"":json[i].reqThough) + '</td>');
            tr.push('<td>' + (json[i].reqDetails==null?"":json[i].reqDetails) + '</td>');
            tr.push('<td>' + (json[i].mobileNo==null?"":json[i].mobileNo) + '</td>');
            tr.push('<td>' + (json[i].reqTime==null?"":json[i].reqTime) + '</td>');
            tr.push('<td><a class="btn" href="/collection/dms/document/preview?id=' + json[i].dmsFileId + '&type='+json[i].dmsFileType +'" data-index = "'+i+'">' + (json[i].fileName==null?"":json[i].fileName) + '</a></td>');
            tr.push('<td>' + json[i].status + '</td>');
            tr.push('<td>' +
                (json[i].status == 'PENDING' ? '': '<button onclick="editCustomer(' + json[i].id + ')" class=\'custEdit btn btn-primary btn-xs\'>Edit</button>') +
                ' <button onclick="viewCustomer(' + json[i].id + ')" class=\'btn btn-success btn-xs\'>View</button>' +
                '</td>');
            tr.push('</tr>');
        }

        $('#custRequestInfoTable').append($(tr.join('')));
    })
}

function custReqestInfoSave(event, e) {
    event.preventDefault();
    // var formValue = $("#customerRequestInfoSaveForm").serialize();
    var formValue = new FormData(e);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var formdatalist = $('#customerRequestInfoSaveForm');



    var custDate = formdatalist.find('input[name="custDate"]').val();
    var custTime = formdatalist.find('input[name="custTime"]').val();
    var reqThough =  formdatalist.find('input[name="reqThough"]').val();
    var reqDetails =  formdatalist.find('input[name="reqDetails"]').val();
    var mobileNo =  formdatalist.find('input[name="mobileNo"]').val();
    var reqTime =  formdatalist.find('input[name="reqTime"]').val();

    // if (accountNo ==''){
    //     $('#customer-error-messages').text("Account number must not be black.").removeClass('hidden')
    //     return ;
    // }else if(accountNo.length != 16){
    //     $('#customer-error-messages').text("Account no must be 15 digits.").removeClass('hidden')
    //     return ;
    // }
    //
    // if (name ==''){
    //     $('#customer-error-messages').text("Name must not be black.").removeClass('hidden')
    //     return ;
    // }else if($.isNumeric(name)){
    //     $('#customer-error-messages').text("Name can not contains numeric value.").removeClass('hidden')
    //     return ;
    // } else if(name.length <3){
    //     $('#customer-error-messages').text("Please minimum 3 character.").removeClass('hidden')
    //     return ;
    // }
    //
    // // if (address ==''){
    // //     $('#customer-error-messages').text("Address must not be black.").removeClass('hidden')
    // //     return;
    // // }
    //

    if (custDate == ''){
        $('#customer-error-messages').text("Date must not be blank.").removeClass('hidden')
        return;
    }

   /* var isValidPhone = true;
    if (isPhoneValid(mobileNo)){
        isValidPhone = false;
    }
    if (mobileNo !=''){
        if (isValidPhone){
            console.log(isValidPhone);
            $('#customer-error-messages').text("Invalid Phone Number!.").removeClass('hidden')
            return;
        }
    }*/


    $.ajax({
        type:"post",
        url:"/collection/setting/customerRequests/save",
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data:formValue,
        success:function (data) {
            swal("Success!", "Successfully Saved!", "success");
            $('custDate,custTime,reqThough,reqDetails,mobileNo,reqTime').val('');
            $('#modal-customer-request').modal('toggle')
            $('#customerReq_tBody').html("");
            customerReqeustInfoList(id);
            // customerReqeustInfoList(customerId);

        },
        error:function (error) {
            $('#modal-customer-request').modal('toggle')

            customerReqeustInfoList(id);
        }
    }).done(function(){
        // Clear Form
        $('#customerRequestInfoSaveForm')[0].reset();

    });

    return false;
}

$("#customerReq_tBody").delegate("#file-event","click", function () {
    var host = window.location.host;
    $.ajax({
        url: 'https://'+host+'/collection/setting/customerRequests/download-file'
    })

});

function editCustomer(id){
    $('#customerRequestInfo input[name="_csrf"]').val(csrfToken);
    var url = '/collection/setting/customerRequests/findByCustomerId?id='+id;
    $.getJSON(url, function(data){
        var formdatalist = $('#customerRequestInfoSaveForm');
        formdatalist.find('input[name="id"]').val(data.id);
        formdatalist.find('input[name="custDate"]').val(data.custDate);
        formdatalist.find('input[name="custTime"]').val(data.custTime);
        formdatalist.find('input[name="reqThough"]').val(data.reqThough);
        formdatalist.find('input[name="reqDetails"]').val(data.reqDetails);
        formdatalist.find('input[name="mobileNo"]').val(data.mobileNo);
        formdatalist.find('input[name="reqTime"]').val(data.reqTime);
        $('#modal-customer-request').modal('toggle');
    });
}


function viewCustomer(id) {
    $('#customerRequestInfo input[name="_csrf"]').val(csrfToken);
    var url = '/collection/setting/customerRequests/findByCustomerId?id='+id;
    $.getJSON(url, function(data){
        formData = data;
        $('#custrDate').text(data.custDate);
        $('#custrTime').text(data.custTime);
        $('#custReqThrough').text(data.reqThough);
        $('#custreqDetails').text(data.reqDetails);
        $('#custmobileNo').text(data.mobileNo);
        $('#custreqTime').text(data.reqTime);

        $('#modal-customer_req-info-view').modal('toggle');
    });
}
