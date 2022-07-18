
function addcreditCardDetails() {
    if (autoDebitValidate()==false){
        return;
    }
    var decision=0;
    if($("#crdCardDetId").val()!="") decision=1;

    var formValues = $('#crdAutoInfoForm').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/card/customer-info/cr-card-details/save',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: jsonEmploymentInfoFormData,
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#crdAuto-Info-Modal').modal('hide');
                clearCrData();
                if (data.crCardDetailsInfo != null && decision==0) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.crCardDetailsInfo.crdCardDetId + "</td>" +
                        "<td>" + data.crCardDetailsInfo.monthlyPayment + "</td>" +
                        "<td>" + data.crCardDetailsInfo.accountName + "</td>" +
                        "<td>" + data.crCardDetailsInfo.accountNo + "</td>" +
                        "<td>" + data.crCardDetailsInfo.currencyType + "</td>" +
                        "<td>" + data.crCardDetailsInfo.accountType + "</td>" +
                        "<td style=\"display:none;\">" + data.crCardDetailsInfo.branchName + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editCrRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteCrRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#crdAutoInfo_tBody').append(str);
                }
                else if (data.crCardDetailsInfo != null && decision==1){
                    globalRow.cells[1].innerHTML=data.crCardDetailsInfo.monthlyPayment;
                    globalRow.cells[2].innerHTML=data.crCardDetailsInfo.accountName;
                    globalRow.cells[3].innerHTML=data.crCardDetailsInfo.accountNo;
                    globalRow.cells[4].innerHTML=data.crCardDetailsInfo.currencyType;
                    globalRow.cells[5].innerHTML=data.crCardDetailsInfo.accountType;
                    globalRow.cells[6].innerHTML=data.crCardDetailsInfo.branchName;
                }
            },
            error: function (err) {
                $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                    "\t\t\t\t\t</div>");
                var message = "<ul style='list-style-type:disc'><li>Error in saving Auto Debit Instruction Info!</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);
            }
        });
}

$('#creditCardDetailsSave').click(function(event) {
    event.preventDefault();
    addcreditCardDetails();
    $("#crdAutoInfoForm")[0].reset();
});


$('#creditCardDetailsClose').click(function(event) {
    event.preventDefault();
    clearCrData();
});

function autoDebitValidate() {
    if ($("#monthlyPayment").val()==""){
        alert("Monthly Payment Must be filled out!");
        $("#monthlyPayment").focus();
        return false;
    }

    if ($("#accountName").val()==""){
        alert("Account Name Must be filled out!");
        $("#accountName").focus();
        return false;
    }


    var x = $("#accountNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Account No must be valid..");
        $("#accountNo").focus();
        return false;
    }
}

function clearCrData() {
    $("#crdCardDetId").val("");
    $("#monthlyPayment").val("");
    $("#accountName").val("");
    $("#accountNo").val("");
    $("#currencyType").val("");
    $("#accountType").val("");
    $("#branchNameC").val("");

}

function editCrRow(node){
    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#crdCardDetId").val(row.cells[0].innerHTML);
    $("#monthlyPayment").val(row.cells[1].innerHTML);
    $("#accountName").val(row.cells[2].innerHTML);
    $("#accountNo").val(row.cells[3].innerHTML);
    $("#currencyType").val(row.cells[4].innerHTML);
    $("#accountType").val(row.cells[5].innerHTML);
    $("#branchNameC").val(row.cells[6].innerHTML);

    $('#crdAuto-Info-Modal').modal('show');
}


function deleteCrRow(node){

    var id=node.parentNode.parentNode.cells[0].innerHTML;
  //  console.log("crdCardDetId= "+id);
    //alert("Delete Button Clicked! "+id);

    $.ajax({
        url: "/card/customer-info/cr-card-details/remove?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            //alert("Successfully Removed!");
        },
        error: function (data) {
            //alert("Error!");
        }
    });

    $(node).closest("tr").remove();
}

$('#tab-crdCardDetails').click(function () {
    //alert("cr card clicked...");
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    var id = $("#personalCustomerIdEdit").val();

    $.ajax({
        url: '/card/cr-card/homepreview',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            $('#crCardHomeId').val(data.crCardHomeInfo.crCardHomeId);
            $('#smsAlert').val(data.crCardHomeInfo.smsAlert);
            $('#mailingAddress').val(data.crCardHomeInfo.mailingAddress);
            $('#detailsAddress').val(data.crCardHomeInfo.mailingAddressDetails);
        },
        error: function () {
            ////alert("Error");
        }
    });

    $.ajax({
        url: "/card/customer-info/cr-card-details/list?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#crdAutoInfo_tBody').replaceWith("<tbody id=\"crdAutoInfo_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.crCardDetailsInfoList.length;i++) {
                if (data.crCardDetailsInfoList[i] != null) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.crCardDetailsInfoList[i].crdCardDetId + "</td>" +
                        "<td>" + data.crCardDetailsInfoList[i].monthlyPayment + "</td>" +
                        "<td>" + data.crCardDetailsInfoList[i].accountName + "</td>" +
                        "<td>" + data.crCardDetailsInfoList[i].accountNo + "</td>" +
                        "<td>" + data.crCardDetailsInfoList[i].currencyType + "</td>" +
                        "<td>" + data.crCardDetailsInfoList[i].accountType + "</td>" +
                        "<td style=\"display:none;\">" + data.crCardDetailsInfoList[i].branchName + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editCrRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteCrRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#crdAutoInfo_tBody').append(str);
                }
            }
        },
        error: function () {
            //alert("Error");
        }
    });
});
