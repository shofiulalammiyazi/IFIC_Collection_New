function addCardSpouseInfo() {

    if (spouseInfoValidation()==false){
        return;
    }

    var formValues = $('#spouseInfoForm').serializeJSON();
    var spouseInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/card/customer-info/spouse-info/save',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: spouseInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //alert(data.successMsg);
            $('#spouseInfoFormDiv').hide();
            $('#spouseInfoViewDiv').show();
            $('#VspoInfId').val(data.spouseInfo.spoInfId);
            $('#VspouseNId').text(data.spouseInfo.spouseNId);
            $('#VspouseName').text(data.spouseInfo.spouseName);
            $('#VspouseDOB').text(data.spouseInfo.spouseDOB);
            $('#VspouseProfession').text(data.spouseInfo.spouseProfession);
            $('#VspouseFatherName').text(data.spouseInfo.spouseFatherName);
            $('#VspouseMotherName').text(data.spouseInfo.spouseMotherName);
            $('#VspsMobile').text(data.spouseInfo.spsMobile);
            $('#VspsCompanyName').text(data.spouseInfo.spsCompanyName);
            $('#VspsDesignation').text(data.spouseInfo.spsDesignation);
            $('#VspsBusinessLength').text(data.spouseInfo.spsBusinessLength);
            $('#VspsIncome').text(data.spouseInfo.spsIncome);
            $('#VspouseAsset').text(data.spouseInfo.spouseAsset);
            $('#VspouseLiability').text(data.spouseInfo.spouseLiability);
            $('#VspouseNetWorth').text(data.spouseInfo.spouseNetWorth);
            $('#VspsOfficePhoneNo').text(data.spouseInfo.spsOfficePhoneNo);
            $('#VspouseAddress').text(data.spouseInfo.spouseAddress);
            $('#VappMotherName').text(data.spouseInfo.appMotherName);
            $('#VappFatherName').text(data.spouseInfo.appFatherName);

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Spouce info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#spouseInfoCommitBtn').click(function(event) {
    event.preventDefault();
    $('#parentInfoFormDiv').hide();
    $('#parentInfoViewDiv').hide();
    addCardSpouseInfo();
});


$('#spouseInfoViewEditBtn').click(function(event) {
    event.preventDefault();
    $('#spouseInfoFormDiv').show();
    $('#spouseInfoViewDiv').hide();
});


function spouseInfoValidation() {
	 if ($("#appFatherName").val()==""){
	        alert("Applicants Father Name must be filled out!");
	        $("#appFatherName").focus();
	        return false;
	    }
	 if ($("#appMotherName").val()==""){
	        alert("Applicants Mother Name must be filled out!");
	        $("#appMotherName").focus();
	        return false;
	    }
    if ($("#spouseName").val()==""){
        alert("Spouse Name must be filled out!");
        $("#spouseName").focus();
        return false;
    }

    if ($("#spouseDOB").val()==""){
        alert("Date of birth must be filled out!");
        $("#spouseDOB").focus();
        return false;
    }

    var x = $("#spouseNId").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Spouse National Id must be valid..");
        $("#spouseNId").focus();
        return false;
    }

    var x = $("#spsMobile").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Personal Mobile No must be valid..");
        $("#spsMobile").focus();
        return false;
    }

    var x = $("#spsBusinessLength").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Service/Business Length must be valid..");
        $("#spsBusinessLength").focus();
        return false;
    }

    var x = $("#spsIncome").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Monthly income must be valid..");
        $("#spsIncome").focus();
        return false;
    }

    var x = $("#spouseAsset").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Asset must be valid..");
        $("#spouseAsset").focus();
        return false;
    }

    var x = $("#spouseLiability").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Liability must be valid..");
        $("#spouseLiability").focus();
        return false;
    }

    var x = $("#spouseNetWorth").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Net Worth must be valid..");
        $("#spouseNetWorth").focus();
        return false;
    }

    var x = $("#spsOfficePhoneNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Office Phone No must be valid..");
        $("#spsOfficePhoneNo").focus();
        return false;
    }
}



$(document).ready(function() {

    $("#tab-family").click(function() {

        var martialStatus=$('#martialStatus').val();


        var customerId = $("#personalCustomerId").val();


        if ($('#maritalStatus').val()=='Single'){
            $('#parentInfoFormDiv').show();
            $('#spouseInfoFormDiv').hide();
        }
        else {
            $('#spouseInfoFormDiv').show();
            $('#parentInfoFormDiv').hide();
        }

        if(martialStatus=='Single'){

            $.ajax({
                url : '/card/customer-info/parent-info/view?customerId='+customerId,
                type : 'GET',

            }).done(function(data) {

                $('#spouseInfoViewDiv').hide();
                $('#customerParentId').val(data.parentInfo.customerParentId);
                //for parentInfo Input
                $('#fatherNamexx').val(data.parentInfo.fatherName);
                $('#motherNamexx').val(data.parentInfo.motherName);
                $('#motherProfessionIdxx').val(data.parentInfo.motherProfessionId);
                $('#fatherProfessionIdxx').val(data.parentInfo.fatherProfessionId);
                $('#fatherNidxx').val(data.parentInfo.fatherNid);
                $('#motherNidxx').val(data.parentInfo.motherNid);
                $('#fatherCompanyNamexx').val(data.parentInfo.fatherCompanyName);
                $('#motherCompanyNamexx').val(data.parentInfo.motherCompanyName);
                $('#fatherContactNumberxx').val(data.parentInfo.fatherContactNumber);
                $('#motherContactNumberxx').val(data.parentInfo.motherContactNumber);


            }).always(function() {

            });
        }
        else{
            $.ajax({
                url : '/card/customer-info/spouse-info/view?customerId=' + customerId,
                type : 'GET',
                data : 'noData',

            }).done(function(data) {

                $('#parentInfoViewDiv').hide();
                $('#spoInfId').val(data.spouseInfo.spoInfId);
                $('#spouseName').val(data.spouseInfo.spouseName);
                $('#spouseDOB').val(data.spouseInfo.spouseDOB);
                $('#spouseNId').val(data.spouseInfo.spouseNId);
                $('#spouseProfession').val(data.spouseInfo.spouseProfession);
                $('#spouseFatherName').val(data.spouseInfo.spouseFatherName);
                $('#spouseMotherName').val(data.spouseInfo.spouseMotherName);
                $('#spsMobile').val(data.spouseInfo.spsMobile);
                $('#spsCompanyName').val(data.spouseInfo.spsCompanyName);
                $('#spsDesignation').val(data.spouseInfo.spsDesignation);
                $('#spsBusinessLength').val(data.spouseInfo.spsBusinessLength);
                $('#spsIncome').val(data.spouseInfo.spsIncome);
                $('#spouseAsset').val(data.spouseInfo.spouseAsset);
                $('#spouseLiability').val(data.spouseInfo.spouseLiability);
                $('#spouseNetWorth').val(data.spouseInfo.spouseNetWorth);
                $('#spsOfficePhoneNo').val(data.spouseInfo.spsOfficePhoneNo);
                $('#spouseAddress').val(data.spouseInfo.spouseAddress);
                $('#appFatherName').val(data.spouseInfo.appFatherName);
                $('#appMotherName').val(data.spouseInfo.appMotherName);

            }).always(function() {

            });
        }

    });

});


function netWorthAmount() {
    var asset = parseFloat($("#spouseAsset").val());
    var liability = parseFloat($("#spouseLiability").val());
    if (asset != "" && liability != ""){
        $("#spouseNetWorth").val(parseFloat(asset) - parseFloat(liability));
    } else {
        $("#spouseNetWorth").val(0);
    }
}

$("#spouseLiability").keyup(function () {
    netWorthAmount();
});

