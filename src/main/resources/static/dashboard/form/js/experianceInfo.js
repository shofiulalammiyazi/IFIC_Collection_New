function addPreviousExperianceInfo() {
    if (employmentValidate()==false){
        return;
    }
    var decision=0;
    if($("#expInfoId").val()!="") decision=1;
    var formValues = $('#prevExperianceInfoForm').serializeJSON();
    var jsonExperianceInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/card/customer-info/experiance-info/save',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: jsonExperianceInfoFormData,
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#Previous-Experiance-Modal').modal('hide');
                clearExp();
                if (data.experianceInfo != null && decision==0) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expInfoId + "</td>" +
                        "<td>" + data.experianceInfo.expProfession + "</td>" +
                        "<td>" + data.experianceInfo.expCompName + "</td>" +
                        "<td>" + data.experianceInfo.expDepartment + "</td>" +
                        "<td>" + data.experianceInfo.expDesignation + "</td>" +
                        "<td>" + data.experianceInfo.expBusinessNature + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expCompAddress + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expCompPhoneNo + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expCompEmail + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expCompStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expFromDate + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.expToFrom + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfo.eserviceLength + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editExpRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteExpRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#pExperianceInfo_tBody').append(str);
                }
                else if (data.experianceInfo != null && decision==1){
                    globalRow.cells[0].innerHTML=data.experianceInfo.expInfoId;
                    globalRow.cells[1].innerHTML=data.experianceInfo.expProfession;
                    globalRow.cells[2].innerHTML=data.experianceInfo.expCompName;
                    globalRow.cells[3].innerHTML=data.experianceInfo.expDepartment;
                    globalRow.cells[4].innerHTML=data.experianceInfo.expDesignation;
                    globalRow.cells[5].innerHTML=data.experianceInfo.expBusinessNature;
                    globalRow.cells[6].innerHTML=data.experianceInfo.expCompAddress;
                    globalRow.cells[7].innerHTML=data.experianceInfo.expCompPhoneNo;
                    globalRow.cells[8].innerHTML=data.experianceInfo.expCompEmail;
                    globalRow.cells[9].innerHTML=data.experianceInfo.expCompStatus;
                    globalRow.cells[10].innerHTML=data.experianceInfo.expFromDate;
                    globalRow.cells[11].innerHTML=data.experianceInfo.expToFrom;
                    globalRow.cells[12].innerHTML=data.experianceInfo.eserviceLength;
                }
            },
            error: function (err) {
                $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                    "\t\t\t\t\t</div>");
                var message = "<ul style='list-style-type:disc'><li>Error in saving Experience info!</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);
            }
        });
}

$('#prevExperianceSaveButton').click(function(event) {
    event.preventDefault();
    addPreviousExperianceInfo();
});

$('#prevExperianceCloseButton').click(function(event) {
    event.preventDefault();
    $('#Previous-Experiance-Modal').modal('hide');
    clearExp();
});

function editExpRow(node){
    //alert("Edit Button Clicked!");
    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#expInfoId").val(row.cells[0].innerHTML);
    $("#expProfession").val(row.cells[1].innerHTML);
    $("#expCompName").val(row.cells[2].innerHTML);
    $("#expDepartment").val(row.cells[3].innerHTML);
    $("#expDesignation").val(row.cells[4].innerHTML);
    $("#expBusinessNature").val(row.cells[5].innerHTML);
    $("#expCompAddress").val(row.cells[6].innerHTML);
    $("#expCompPhoneNo").val(row.cells[7].innerHTML);
    $("#expCompEmail").val(row.cells[8].innerHTML);
    $("#expCompStatus").val(row.cells[9].innerHTML);
    $("#expFromDate").val(row.cells[10].innerHTML);
    $("#expToFrom").val(row.cells[11].innerHTML);
    $("#EserviceLengthV").val(row.cells[12].innerHTML);

    $('#Previous-Experiance-Modal').modal('show');
}

function deleteExpRow(node){
    var id=node.parentNode.parentNode.cells[0].innerHTML;
    alert("Delete Button Clicked! "+id);
    $.ajax({
        url: "/card/customer-info/experiance-info/remove?id="+id,
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

$('#tab-employment').click(function () {
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    var id = $("#personalCustomerIdEdit").val();

    $.ajax({
        url: "/card/customer-info/experiance-info/list?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#pExperianceInfo_tBody').replaceWith("<tbody id=\"pExperianceInfo_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.experianceInfoList.length;i++) {
                if (data.experianceInfoList[i] != null) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expInfoId + "</td>" +
                        "<td>" + data.experianceInfoList[i].expProfession + "</td>" +
                        "<td>" + data.experianceInfoList[i].expCompName + "</td>" +
                        "<td>" + data.experianceInfoList[i].expDepartment + "</td>" +
                        "<td>" + data.experianceInfoList[i].expDesignation + "</td>" +
                        "<td>" + data.experianceInfoList[i].expBusinessNature + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expCompAddress + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expCompPhoneNo + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expCompEmail + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expCompStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expFromDate + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].expToFrom + "</td>" +
                        "<td style=\"display:none;\">" + data.experianceInfoList[i].eserviceLength + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editExpRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteExpRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#pExperianceInfo_tBody').append(str);
                }
            }
        },
        error: function () {
            //alert("Error");
        }
    });
});

function clearExp() {
    $("#expProfession").val("");
    $("#expCompName").val("");
    $("#expDepartment").val("");
    $("#expDesignation").val("");
    $("#expBusinessNature").val("");
    $("#expCompAddress").val("");
    $("#expCompPhoneNo").val("");
    $("#expCompEmail").val("");
    $("#expCompStatus").val("");
    $("#expFromDate").val("");
    $("#expToFrom").val("");
    $("#EserviceLengthV").val("");
}

function employmentValidate() {
    if ($("#expProfession").val()==""){
        alert("Profession Must be filled out!");
        $("#expProfession").focus();
        return false;
    }

    if ($("#expCompName").val()==""){
        alert("Company Name Must be filled out!");
        $("#expCompName").focus();
        return false;
    }

    if ($("#expDesignation").val()==""){
        alert("Designation Must be filled out!");
        $("#expDesignation").focus();
        return false;
    }

    if ($("#expFromDate").val()==""){
        alert("From Date Must be filled out!");
        $("#expFromDate").focus();
        return false;
    }

    if ($("#expToFrom").val()==""){
        alert("To Date Must be filled out!");
        $("#expToFrom").focus();
        return false;
    }

    var x = $("#expCompPhoneNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Company Phone No must be valid..");
        $("#expCompPhoneNo").focus();
        return false;
    }
}
$("#expToFrom").change(function () {
	dateLengthCard();
});

function dateLengthCard() {
		var fromDate=$('#expFromDate').val();
		var toDate =$('#expToFrom').val();
		var date1 = new Date(fromDate);
		var date2 = new Date(toDate);
		var timeDiff = Math.abs(date2.getTime() - date1.getTime());
		var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24 *30)); 
		var dateLength = diffDays-1;
		 $("#EserviceLengthV").val(dateLength);
}