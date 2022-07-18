function addBusinessVrInfo() {
    
    if (businessVrValidation()==false){
        return;
    } 

    var formValues = $('#businessVrInfoForm').serializeJSON();
    var businessVrInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/card/branch-verification/business-vr/save-business-vr',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: businessVrInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#businessVrInfoFormDiv').hide();
            $('#businessVrInfoViewDiv').show();
            $('#bvId').val(data.businessVrInfo.bvId);
            $('#businessVrInfoBusCustName').text(data.businessVrInfo.busCustName);
            $('#businessVrInfoBusVRSiteVisited').text(data.businessVrInfo.busVRSiteVisited);
            $('#businessVrInfoBusVRVisitCondBy').text(data.businessVrInfo.busVRVisitCondBy);
            $('#businessVrInfoBusVRdov').text(data.businessVrInfo.businessVRdov);
            $('#businessVrInfoCompanyName').text(data.businessVrInfo.buscompanyName);
            $('#businessVrInfoNatureOfBusiness').text(data.businessVrInfo.natureOfBusiness);
            $('#businessVrInfoLengthOfBusiness').text(data.businessVrInfo.lengthOfBusiness);
            $('#businessVrInfoBusinessOwnershipType').text(data.businessVrInfo.businessOwnershipType);
            $('#businessVrInfoLocOfBusinessOffice').text(data.businessVrInfo.locOfBusinessOffice);
            $('#businessVrInfoLocOfFactory').text(data.businessVrInfo.locOfFactory);
            $('#businessVrInfoPosOfApplicant').text(data.businessVrInfo.posOfApplicant);
            $('#businessVrInfoOwnershipOfOffice').text(data.businessVrInfo.ownershipOfOffice);
            $('#businessVrInfoOwnershipOfFactory').text(data.businessVrInfo.ownershipOfFactory);
            $('#businessVrInfoMajorClients').text(data.businessVrInfo.majorClients);
            $('#businessVrInfoStockPosition').text(data.businessVrInfo.stockPosition);
            $('#businessVrInfoTotalNoOfEmployees').text(data.businessVrInfo.totalNoOfEmployees);
            $('#businessVrInfoMajorSuppliers').text(data.businessVrInfo.majorSuppliers);
            $('#businessVrInfoBusinessExpense').text(data.businessVrInfo.businessExpense);
            $('#businessVrInfoMonthlyBusinessTurnover').text(data.businessVrInfo.monthlyBusinessTurnover);
            $('#businessVrInfoBankingRelationship').text(data.businessVrInfo.bankingRelationship);
            $('#businessVrInfoMonthlyBusinessIncome').text(data.businessVrInfo.monthlyBusinessIncome);
            $('#businessVrInfoBusinessVROfficeSetup').text(data.businessVrInfo.businessVROfficeSetup);
            $('#businessVrInfoBusinessVROverallComments').text(data.businessVrInfo.businessVROverallComments);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Business Vr!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#businessVrCommitBtn').click(function(event) {
    event.preventDefault();
    addBusinessVrInfo();
});

$('#businessVrEditBtn').click(function(event) {
    event.preventDefault();
    $('#businessVrInfoFormDiv').show();
    $('#businessVrInfoViewDiv').hide();
});


function businessVrValidation() {
    if ($("#busCustName").val()==""){
        alert("Customer Name must be filled out!");
        $("#busCustName").focus();
        return false;
    }

    if ($("#busVRSiteVisited").val()==""){
        alert("Site Visit must be filled out!");
        $("#busVRSiteVisited").focus();
        return false;
    }

    if ($("#busVRVisitCondBy").val()==""){
        alert("Visitor Name must be filled out!");
        $("#busVRVisitCondBy").focus();
        return false;
    }

    if ($("#businessVRdov").val()==""){
        alert("Date of Visit must be filled out!");
        $("#businessVRdov").focus();
        return false;
    }



    if ($("#natureOfBusiness").val()==""){
        alert("Business Nature must be filled out!");
        $("#natureOfBusiness").focus();
        return false;
    }

    if ($("#businessOwnershipType").val()==""){
        alert("Ownership Type must be filled out!");
        $("#businessOwnershipType").focus();
        return false;
    }

    if ($("#locOfBusinessOffice").val()==""){
        alert("Office Location must be filled out!");
        $("#locOfBusinessOffice").focus();
        return false;
    }

    if ($("#posOfApplicant").val()==""){
        alert("Applicant Position must be filled out!");
        $("#posOfApplicant").focus();
        return false;
    }

    if ($("#totalNoOfEmployees").val()==""){
        alert("Employee No must be filled out!");
        $("#totalNoOfEmployees").focus();
        return false;
    }

    if ($("#monthlyBusinessTurnover").val()==""){
        alert("Business Turnover must be filled out!");
        $("#monthlyBusinessTurnover").focus();
        return false;
    }

    if ($("#monthlyBusinessIncome").val()==""){
        alert("Monthly Income must be filled out!");
        $("#monthlyBusinessIncome").focus();
        return false;
    }

    if ($("#businessVROverallComments").val()==""){
        alert("Overall Comments must be filled out!");
        $("#businessVROverallComments").focus();
        return false;
    }

    var x=$("#lengthOfBusiness").val();
    if(isNaN(x) || x<0){
        alert("Input valid number!");
        $("#lengthOfBusiness").focus();
        return false;
    }

    x=$("#totalNoOfEmployees").val();
    if(isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalNoOfEmployees").focus();
        return false;
    }

    x=$("#businessExpense").val();
    if(isNaN(x) || x<0){
        alert("Input valid number!");
        $("#businessExpense").focus();
        return false;
    }

    x=$("#monthlyBusinessTurnover").val();
    if(isNaN(x) || x<0){
        alert("Input valid number!");
        $("#monthlyBusinessTurnover").focus();
        return false;
    }
}


$("#tab-businessVR, #tab_bvr_report").click(function(){

    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    $.ajax({
        url: '/card/branchvr/businessvr',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            $('#customerId').val(data.businessVrInfo.customerId);
            $('#bvId').val(data.businessVrInfo.bvId);
            $('#busCustName').val(data.businessVrInfo.busCustName);
            $('#busVRSiteVisited').val(data.businessVrInfo.busVRSiteVisited);
            $('#busVRVisitCondBy').val(data.businessVrInfo.busVRVisitCondBy);
            $('#businessVRdov').val(data.businessVrInfo.businessVRdov);
            $('#buscompanyName').val(data.businessVrInfo.buscompanyName);
            $('#natureOfBusiness').val(data.businessVrInfo.natureOfBusiness);
            $('#lengthOfBusiness').val(data.businessVrInfo.lengthOfBusiness);
            $('#businessOwnershipType').val(data.businessVrInfo.businessOwnershipType);
            $('#locOfBusinessOffice').val(data.businessVrInfo.locOfBusinessOffice);
            $('#locOfFactory').val(data.businessVrInfo.locOfFactory);
            $('#posOfApplicant').val(data.businessVrInfo.posOfApplicant);
            $('#ownershipOfOffice').val(data.businessVrInfo.ownershipOfOffice);
            $('#ownershipOfFactory').val(data.businessVrInfo.ownershipOfFactory);
            $('#majorClients').val(data.businessVrInfo.majorClients);
            $('#stockPosition').val(data.businessVrInfo.stockPosition);
            $('#totalNoOfEmployees').val(data.businessVrInfo.totalNoOfEmployees);
            $('#majorSuppliers').val(data.businessVrInfo.majorSuppliers);
            $('#businessExpense').val(data.businessVrInfo.businessExpense);
            $('#monthlyBusinessTurnover').val(data.businessVrInfo.monthlyBusinessTurnover);
            $('#bankingRelationship').val(data.businessVrInfo.bankingRelationship);
            $('#monthlyBusinessIncome').val(data.businessVrInfo.monthlyBusinessIncome);
            $('#businessVROfficeSetup').val(data.businessVrInfo.businessVROfficeSetup);
            $('#businessVROverallComments').val(data.businessVrInfo.businessVROverallComments);
        },
        error: function () {
            ////alert("Error");
        }
    });
});