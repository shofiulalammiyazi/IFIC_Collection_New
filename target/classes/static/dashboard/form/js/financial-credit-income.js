var globalCreditRow;

function existingCreditInfo() {

    if (creditFacilityValidation()==false){
        return;
    }

    var formValues = $('#existingCreditInfo').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var decision=0;
    if($("#cfId").val()!="") decision=1;

    console.log(jsonEmploymentInfoFormData);
    $.ajax({
        url: '/card/customer-info/financial-info/save-existing-financial-info',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonEmploymentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-monthly-credit').modal('hide');
            clearCreditData();
            if (data.creditFacilityInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.cfId + "</td>" +
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.customerId + "</td>" +
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.cardId + "</td>" +

                    "<td>" + data.creditFacilityInfo.facilityType + "</td>" +
                    "<td>" + data.creditFacilityInfo.branchName + "</td>" +
                    "<td>" + data.creditFacilityInfo.cardNo + "</td>" +
                    "<td>" + data.creditFacilityInfo.loanAccNo + "</td>" +
                    "<td>" + data.creditFacilityInfo.sanctionLimit + "</td>" +
                    "<td>" + data.creditFacilityInfo.obligation + "</td>" +
                    "<td>" + data.creditFacilityInfo.presentOutstanding1 + "</td>" +
                    
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.bankName1 + "</td>" +
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.facilityDate + "</td>" +
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.expiryDate + "</td>" +
                    "<td style=\"display:none;\">" + data.creditFacilityInfo.securityColl + "</td>" +

                    "<td><a class=\"btn btn-xs\" onclick=\"editCreditRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteCreditRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#creditFacility_tBody').append(str);
            }
            else if (data.creditFacilityInfo != null && decision==1){
                globalCreditRow.cells[3].innerHTML=data.creditFacilityInfo.facilityType;
                /*globalCreditRow.cells[4].innerHTML=data.creditFacilityInfo.bankName1;*/
                globalCreditRow.cells[4].innerHTML=data.creditFacilityInfo.branchName;
                globalCreditRow.cells[5].innerHTML=data.creditFacilityInfo.cardNo;
                globalCreditRow.cells[6].innerHTML=data.creditFacilityInfo.loanAccNo;
                globalCreditRow.cells[7].innerHTML=data.creditFacilityInfo.sanctionLimit;
                globalCreditRow.cells[8].innerHTML=data.creditFacilityInfo.obligation;
                globalCreditRow.cells[9].innerHTML=data.creditFacilityInfo.presentOutstanding1;
            }

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Credit Facility Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

function editCreditRow(node){
    var row=node.parentNode.parentNode;
    globalCreditRow=row;

    $("#cfId").val(row.cells[0].innerHTML);

    $("#facilityType").val(row.cells[3].innerHTML);
 	$("#bankName1").val(row.cells[10].innerHTML);
    $("#presentOutstanding1").val(row.cells[9].innerHTML);
    $("#branchName").val(row.cells[4].innerHTML);
    $("#sanctionLimit").val(row.cells[7].innerHTML);
    $("#facilityDate").val(row.cells[11].innerHTML);
    $("#cardNo").val(row.cells[5].innerHTML);
    $("#obligation").val(row.cells[8].innerHTML);
    $("#expiryDate").val(row.cells[12].innerHTML);
    $("#loanAccNo").val(row.cells[6].innerHTML);
    
    
    $("#securityColl").val(row.cells[13].innerHTML);

    $('#modal-monthly-credit').modal('show');
}

function deleteCreditRow(node){
    var dt={
        cfId: node.parentNode.parentNode.cells[0].innerHTML
    };

    $.ajax({
       url: "/card/customer-info/financial-info/remove-existing-financial-info",
        type: "GET",
        data: dt,
        success: function (data) {
            $(node).closest("tr").remove();
        },
        error: function (data) {

        }
    });
}

$("#tab-financial").click(function (event){
    var dt={
        cardId: $("#cardId").val()
    };

    $.ajax({
        url: "/card/customer-info/financial-info/credit/list",
        type: "GET",
        data: dt,
        success: function (data) {

            $("#creditFacility_tBody").replaceWith("<tbody id=\"creditFacility_tBody\" >\n" +
                "\t\t\t\t\t\t</tbody>");

            for (var i=0;i<data.creditIncomeList.length;i++){
                if (data.creditIncomeList[i]!=null){
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.creditIncomeList[i].cfId + "</td>" +
                        "<td style=\"display:none;\">" + data.creditIncomeList[i].customerId + "</td>" +
                        "<td style=\"display:none;\">" + data.creditIncomeList[i].cardId + "</td>" +

                        "<td>" + data.creditIncomeList[i].facilityType + "</td>" +
                        "<td>" + data.creditIncomeList[i].branchName + "</td>" +
                        "<td>" + data.creditIncomeList[i].cardNo + "</td>" +
                        "<td>" + data.creditIncomeList[i].loanAccNo + "</td>" +
                        "<td>" + data.creditIncomeList[i].sanctionLimit + "</td>" +
                        "<td>" + data.creditIncomeList[i].obligation + "</td>" +
                        "<td>" + data.creditIncomeList[i].presentOutstanding1 + "</td>" +

                        "<td style=\"display:none;\">" + data.creditIncomeList[i].bankName1 + "</td>" +
                        "<td style=\"display:none;\">" + data.creditIncomeList[i].facilityDate + "</td>" +
                        "<td style=\"display:none;\">" + data.creditIncomeList[i].expiryDate + "</td>" +
                        "<td style=\"display:none;\">" + data.creditIncomeList[i].securityColl + "</td>" +

                        "<td><a class=\"btn btn-xs\" onclick=\"editCreditRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteCreditRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#creditFacility_tBody').append(str);
                }
            }
        },
        error: function (data) {

        }
    });
});

function clearCreditData(){
    $("#cfId").val("");

    $("#facilityType").val("");
    $("#bankName1").val("");
    $("#branchName").val("");
    $("#cardNo").val("");
    $("#loanAccNo").val("");
    $("#sanctionLimit").val("");
    $("#obligation").val("");
    $("#presentOutstanding1").val("");
    $("#facilityDate").val("");
    $("#expiryDate").val("");
    $("#securityColl").val("");
}




$('#creditFacilitySaveButton').click(function(event) {
    event.preventDefault();
    existingCreditInfo();
});

$('#creditFacilityCloseButton').click(function(event) {
    event.preventDefault();
    clearCreditData();
});

function creditFacilityValidation() {
    if ($("#facilityType").val()==0){
        alert("Facility Type must be filled out!");
        $("#facilityType").focus();
        return false;
    }

    if ($("#bankName1").val()==0){
        alert("Bank Name must be filled out!");
        $("#bankName1").focus();
        return false;
    }

    var x = $("#sanctionLimit").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Sanction Limit must be valid..");
        $("#sanctionLimit").focus();
        return false;
    }

    var x = $("#cardNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Card Number must be valid..");
        $("#cardNo").focus();
        return false;
    }

    var x = $("#obligation").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Monthly Installment/Obligation (BDT) must be valid..");
        $("#obligation").focus();
        return false;
    }

    var x = $("#loanAccNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Loan Account Number must be valid..");
        $("#loanAccNo").focus();
        return false;
    }
}