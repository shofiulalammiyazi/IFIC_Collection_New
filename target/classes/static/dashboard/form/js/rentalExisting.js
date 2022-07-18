function addExistRentalInfo() {

    if (rentalExistingValidation()==false){
        return;
    }
    var decision=0;
    if($("#existId").val()!="") decision=1;
    var formValues = $('#existingRentalIncomeForm').serializeJSON();
    var jsonData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/branch-verification/rental-verification/existing/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-existingRentalIncome-info').modal('hide');
            clearExist();
            if (data.existRentalInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.existRentalInfo.existId + "</td>" +
                    "<td>" + data.existRentalInfo.propertyType + "</td>" +
                    "<td>" + data.existRentalInfo.propertyAddress + "</td>" +
                    "<td>" + data.existRentalInfo.ownerName + "</td>" +
                    "<td>" + data.existRentalInfo.floorLevel + "</td>" +
                    "<td>" + data.existRentalInfo.builtUpArea + "</td>" +
                    "<td>" + data.existRentalInfo.noOfUnit + "</td>" +
                    "<td>" + data.existRentalInfo.perUnitRent + "</td>" +
                    "<td>" + data.existRentalInfo.totalRent + "</td>" +
                    "<td style=\"display:none;\">" + data.existRentalInfo.projIncome + "</td>" +
                    "<td><a class=\"btn btn-xs\" onclick=\"editExistRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteExistRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#existingRentalIncome_tBody').append(str);
            }
            else if (data.existRentalInfo != null && decision==1){
                globalRow.cells[0].innerHTML=data.existRentalInfo.existId;
                globalRow.cells[1].innerHTML=data.existRentalInfo.propertyType;
                globalRow.cells[2].innerHTML=data.existRentalInfo.propertyAddress;
                globalRow.cells[3].innerHTML=data.existRentalInfo.ownerName;
                globalRow.cells[4].innerHTML=data.existRentalInfo.floorLevel;
                globalRow.cells[5].innerHTML=data.existRentalInfo.builtUpArea;
                globalRow.cells[6].innerHTML=data.existRentalInfo.noOfUnit;
                globalRow.cells[7].innerHTML=data.existRentalInfo.perUnitRent;
                globalRow.cells[8].innerHTML=data.existRentalInfo.totalRent;
                globalRow.cells[9].innerHTML=data.existRentalInfo.projIncome;
            }

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Existing Rental Income!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#existingRentalIncomeSaveButton').click(function(event) {
    event.preventDefault();
    addExistRentalInfo();
});
$('#existingRentalIncomeCloseButton').click(function(event) {
    event.preventDefault();
    $('#modal-existingRentalIncome-info').modal('hide');
    clearExist();
});

function editExistRow(node){
    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#existId").val(row.cells[0].innerHTML);
    $("#propertyType").val(row.cells[1].innerHTML);
    $("#propertyAddress").val(row.cells[2].innerHTML);
    $("#ownerName").val(row.cells[3].innerHTML);
    $("#floorLevel").val(row.cells[4].innerHTML);
    $("#builtUpArea").val(row.cells[5].innerHTML);
    $("#noOfUnit").val(row.cells[6].innerHTML);
    $("#perUnitRent").val(row.cells[7].innerHTML);
    $("#totalRent").val(row.cells[8].innerHTML);
    $("#projIncome").val(row.cells[9].innerHTML);

    $('#modal-existingRentalIncome-info').modal('show');
}

function deleteExistRow(node){
    var id=node.parentNode.parentNode.cells[0].innerHTML;
    $.ajax({
        url: "/card/branch-verification/rental-verification/existing/remove?id="+id,
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

function clearExist() {
    $("#propertyType").val("");
    $("#propertyAddress").val("");
    $("#ownerName").val("");
    $("#floorLevel").val("");
    $("#builtUpArea").val("");
    $("#noOfUnit").val("");
    $("#perUnitRent").val("");
    $("#totalRent").val("");
    $("#projIncome").val("");
}

function rentalExistingValidation() {
    var x=$("#propertyType").val();
    if (x==""){
        alert("Property type must be filled out!");
        $("#propertyType").focus();
        return false;
    }

    x=$("#builtUpArea").val();
    if (x==""){
        alert("Built Up Area must be filled out!");
        $("#builtUpArea").focus();
        return false;
    }

    x=$("#floorLevel").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#floorLevel").focus();
        return false;
    }


    x=$("#noOfUnit").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#noOfUnit").focus();
        return;
    }

    x=$("#perUnitRent").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#perUnitRent").focus();
        return false;
    }

    x=$("#totalRent").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalRent").focus();
        return false;
    }

    x=$("#projIncome").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#projIncome").focus();
        return false;
    }
}

$('#tab-rentalincomeVR').click(function () {
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    var id = $("#personalCustomerIdEdit").val();

    $.ajax({
        url: "/card/branch-verification/rental-verification/existing/list?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#existingRentalIncome_tBody').replaceWith("<tbody id=\"existingRentalIncome_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.existRentalInfoList.length;i++) {
                if (data.existRentalInfoList[i] != null) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.existRentalInfoList[i].existId + "</td>" +
                        "<td>" + data.existRentalInfoList[i].propertyType + "</td>" +
                        "<td>" + data.existRentalInfoList[i].propertyAddress + "</td>" +
                        "<td>" + data.existRentalInfoList[i].ownerName + "</td>" +
                        "<td>" + data.existRentalInfoList[i].floorLevel + "</td>" +
                        "<td>" + data.existRentalInfoList[i].builtUpArea + "</td>" +
                        "<td>" + data.existRentalInfoList[i].noOfUnit + "</td>" +
                        "<td>" + data.existRentalInfoList[i].perUnitRent + "</td>" +
                        "<td>" + data.existRentalInfoList[i].totalRent + "</td>" +
                        "<td style=\"display:none;\">" + data.existRentalInfoList[i].projIncome + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editExistRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteExistRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#existingRentalIncome_tBody').append(str);
                }
            }
        },
        error: function () {
            //alert("Error");
        }
    });
});

function hideShowExistingTable() {
    if($("#existingIncome").val()==1){
        $("#existingRentalIncome_info").show();
        $("#existingIncomeAdd").show();

    }
    else {
        $("#existingRentalIncome_info").hide();
        $("#existingIncomeAdd").hide();
    }

}