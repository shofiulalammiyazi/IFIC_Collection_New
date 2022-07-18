function addCardContactInfo() {
    if (contactValidate()==false){
        return;
    }
    var formValues = $('#contactInfoForm').serializeJSON();
    var contactInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/customer-info/contact-info/save-contact-info',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: contactInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#contactInfoFormDiv').hide();
            $('#contactInfoViewDiv').show();
            $('#cntId').val(data.contactInfo.cntId);
            $('#vpersonalNo').text(data.contactInfo.personalNo);
            $('#vofficeNo').text(data.contactInfo.officeNo);
            $('#vpresentNo').text(data.contactInfo.presentNo);
            $('#vmobileNo').text(data.contactInfo.mobileNo);
            $('#vpersonalEmail').text(data.contactInfo.personalEmail);
            $('#vofficeEmail').text(data.contactInfo.officeEmail);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Contact Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            //console.log(err);
        }
    });
}

$('#contactInfoCommitBtn').click(function(event) {
    event.preventDefault();
    addCardContactInfo();
});


$('#contactInfoEditBtn').click(function(event) {
    event.preventDefault();
    $('#contactInfoFormDiv').show();
    $('#contactInfoViewDiv').hide();
});



$("#tab-contactInfo").click(function(){
    ////alert("edit contact info click");
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    $.ajax({
        url: '/card/customer/contactinfo',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            $('#customerId').val(data.contactInfo.customerId);
            $('#cntId').val(data.contactInfo.cntId);
            $('#personalNo').val(data.contactInfo.personalNo);
            $('#officeNo').val(data.contactInfo.officeNo);
            $('#presentNo').val(data.contactInfo.presentNo);
            $('#mobileNo').val(data.contactInfo.mobileNo);
            $('#personalEmail').val(data.contactInfo.personalEmail);
            $('#officeEmail').val(data.contactInfo.officeEmail);
        },
        error: function (err) {
        }
    });
});



function contactValidate() {
    var x = $("#personalNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Personal Phone No must be valid..");
        $("#personalNo").focus();
        return false;
    }

    var x = $("#officeNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Office Phone No must be valid..");
        $("#officeNo").focus();
        return false;
    }

    var x = $("#presentNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Present Res Phone No must be valid..");
        $("#presentNo").focus();
        return false;
    }

    var x = $("#mobileNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Permanent Res  Phone No must be valid..");
        $("#mobileNo").focus();
        return false;
    }
}


//Date picker
/*$('.input-group.date').datepicker({format: "dd.mm.yyyy"});
$('#datetimepicker1').datetimepicker();*/
