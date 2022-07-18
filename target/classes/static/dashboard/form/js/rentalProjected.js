function addProjRentalInfo() {

    if (rentalProjectedValidation()==false){
        return;
    }
    var decision=0;
    if($("#projId").val()!="") decision=1;
    var formValues = $('#projectedRentalIncomeForm').serializeJSON();
    var jsonData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/branch-verification/rental-verification/projected/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-projectedRentalIncome-info').modal('hide');
            clearProj();
            //alert(data.message);
            if (data.projRentalInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.projRentalInfo.projId + "</td>" +
                    "<td>" + data.projRentalInfo.propertyType2 + "</td>" +
                    "<td>" + data.projRentalInfo.propertyAddress2 + "</td>" +
                    "<td>" + data.projRentalInfo.ownerName2 + "</td>" +
                    "<td>" + data.projRentalInfo.floorLevel2 + "</td>" +
                    "<td>" + data.projRentalInfo.builtUpArea2 + "</td>" +
                    "<td>" + data.projRentalInfo.noOfUnit2 + "</td>" +
                    "<td>" + data.projRentalInfo.perUnitRent2 + "</td>" +
                    "<td>" + data.projRentalInfo.totalRent2 + "</td>" +
                    "<td style=\"display:none;\">" + data.projRentalInfo.projIncome2 + "</td>" +
                    "<td><a class=\"btn btn-xs\" onclick=\"editProjRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteProjRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#projectedRentalIncome_tBody').append(str);
            }
            else if (data.projRentalInfo != null && decision==1){
                globalRow.cells[0].innerHTML=data.projRentalInfo.projId;
                globalRow.cells[1].innerHTML=data.projRentalInfo.propertyType2;
                globalRow.cells[2].innerHTML=data.projRentalInfo.propertyAddress2;
                globalRow.cells[3].innerHTML=data.projRentalInfo.ownerName2;
                globalRow.cells[4].innerHTML=data.projRentalInfo.floorLevel2;
                globalRow.cells[5].innerHTML=data.projRentalInfo.builtUpArea2;
                globalRow.cells[6].innerHTML=data.projRentalInfo.noOfUnit2;
                globalRow.cells[7].innerHTML=data.projRentalInfo.perUnitRent2;
                globalRow.cells[8].innerHTML=data.projRentalInfo.totalRent2;
                globalRow.cells[9].innerHTML=data.projRentalInfo.projIncome2;
            }
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Projected Rental Income!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#projectedRentalIncomeSaveButton').click(function(event) {
    event.preventDefault();
    addProjRentalInfo();
});
$('#projectedRentalIncomeCloseButton').click(function(event) {
    event.preventDefault();
    $('#modal-projectedRentalIncome-info').modal('hide');
    clearProj();
});

function clearProj() {
    $("#propertyType2").val("");
    $("#propertyAddress2").val("");
    $("#ownerName2").val("");
    $("#floorLevel2").val("");
    $("#builtUpArea2").val("");
    $("#noOfUnit2").val("");
    $("#perUnitRent2").val("");
    $("#totalRent2").val("");
    $("#projIncome2").val("");
}

function editProjRow(node){
    //alert("Edit Button Clicked!");
    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#projId").val(row.cells[0].innerHTML);
    $("#propertyType2").val(row.cells[1].innerHTML);
    $("#propertyAddress2").val(row.cells[2].innerHTML);
    $("#ownerName2").val(row.cells[3].innerHTML);
    $("#floorLevel2").val(row.cells[4].innerHTML);
    $("#builtUpArea2").val(row.cells[5].innerHTML);
    $("#noOfUnit2").val(row.cells[6].innerHTML);
    $("#perUnitRent2").val(row.cells[7].innerHTML);
    $("#totalRent2").val(row.cells[8].innerHTML);
    $("#projIncome2").val(row.cells[9].innerHTML);

    $('#modal-projectedRentalIncome-info').modal('show');
}

function deleteProjRow(node){
    var id=node.parentNode.parentNode.cells[0].innerHTML;
    $.ajax({
        url: "/card/branch-verification/rental-verification/projected/remove?id="+id,
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

function rentalProjectedValidation() {
    var x=$("#propertyType2").val();
    if (x==""){
        alert("Property type must be filled out!");
        $("#propertyType2").focus();
        return false;
    }

    x=$("#builtUpArea2").val();
    if (x==""){
        alert("Property type must be filled out!");
        $("#builtUpArea2").focus();
        return false;
    }

    x=$("#floorLevel2").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#floorLevel2").focus();
        return false;
    }

    x=$("#noOfUnit2").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#noOfUnit2").focus();
        return false;
    }

    x=$("#perUnitRent2").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#perUnitRent2").focus();
        return false;
    }

    x=$("#totalRent2").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalRent2").focus();
        return false;
    }

    x=$("#projIncome2").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#projIncome2").focus();
        return false;
    }
}

$('#tab-rentalincomeVR').click(function () {
    //alert("cr card clicked...");
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    var id = $("#personalCustomerIdEdit").val();

    $.ajax({
        url: "/card/branch-verification/rental-verification/projected/list?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            //alert("Success");
            $('#projectedRentalIncome_tBody').replaceWith("<tbody id=\"projectedRentalIncome_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.projRentalInfoList.length;i++) {
                if (data.projRentalInfoList[i] != null) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.projRentalInfoList[i].projId + "</td>" +
                        "<td>" + data.projRentalInfoList[i].propertyType2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].propertyAddress2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].ownerName2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].floorLevel2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].builtUpArea2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].noOfUnit2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].perUnitRent2 + "</td>" +
                        "<td>" + data.projRentalInfoList[i].totalRent2 + "</td>" +
                        "<td style=\"display:none;\">" + data.projRentalInfoList[i].projIncome2 + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editProjRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteProjRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#projectedRentalIncome_tBody').append(str);
                }
            }
        },
        error: function () {
            //alert("Error");
        }
    });
});

function hideShowProjectedTable() {
    if($("#projectedIncome").val()==1){
        $("#projectedRentalIncome_info").show();
        $("#projectedIncomeAdd").show();

    }
    else {
        $("#projectedRentalIncome_info").hide();
        $("#projectedIncomeAdd").hide();
    }

}