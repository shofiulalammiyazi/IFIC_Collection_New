var globalRow;

function refModalInfo() {

    if (refValidation()==false){
        return;
    }

    var formValues = $('#refModalForm').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    var decision=0;
    if($("#refId").val()!="") decision=1;

    $.ajax({
        url: '/card/reference-info/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonEmploymentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.referenceInfo.refId + "</td>" +
                "<td>" + data.referenceInfo.refName + "</td>" +
                "<td>" + data.referenceInfo.relationship + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.refMobile + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.refTelNo + "</td>" +
                "<td>" + data.referenceInfo.refProfession + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.ext + "</td>" +
                "<td>" + data.referenceInfo.refCompAddress + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.refStatus + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.prmntAddrs + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.resdntlAddrs + "</td>" +
                "<td>" + data.referenceInfo.designation + "</td>" +
                "<td style=\"display:none;\">" + data.referenceInfo.emailId + "</td>" +
                "<td>" +
                "<a class=\"btn btn-xs\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a>" +
                "</td>" +
                "</tr>";
            $('#modal-reference-info').modal('hide');

            clearData();
            if (data.referenceInfo != null && decision==0) {
                $('#referenceInfo_tBody').append(str);
            }
            else if (data.referenceInfo != null && decision==1){

                globalRow.cells[0].innerHTML=data.referenceInfo.refId;
                globalRow.cells[1].innerHTML=data.referenceInfo.refName;
                globalRow.cells[2].innerHTML=data.referenceInfo.relationship;
                globalRow.cells[3].innerHTML=data.referenceInfo.refMobile;
                globalRow.cells[4].innerHTML=data.referenceInfo.refTelNo;
                globalRow.cells[5].innerHTML=data.referenceInfo.refProfession;
                globalRow.cells[6].innerHTML=data.referenceInfo.ext;
                globalRow.cells[7].innerHTML=data.referenceInfo.refCompAddress;
                globalRow.cells[8].innerHTML=data.referenceInfo.refStatus;
                globalRow.cells[9].innerHTML=data.referenceInfo.prmntAddrs;
                globalRow.cells[10].innerHTML=data.referenceInfo.resdntlAddrs;
                globalRow.cells[11].innerHTML=data.referenceInfo.designation;
                globalRow.cells[12].innerHTML=data.referenceInfo.emailId;
            }

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Reference Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

function deleteRow(node){
    var refId=node.parentNode.parentNode.cells[0].innerHTML;
    $.ajax({
        url: "/card/reference-info/remove?refId="+refId,
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

function editRow(node){

    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#refId").val(row.cells[0].innerHTML);
    $("#refName").val(row.cells[1].innerHTML);
    $("#relationship").val(row.cells[2].innerHTML);
    $("#refMobile").val(row.cells[3].innerHTML);
    $("#refTelNo").val(row.cells[4].innerHTML);
    $("#refProfession").val(row.cells[5].innerHTML);
    $("#ext").val(row.cells[6].innerHTML);
    $("#refCompAddress").val(row.cells[7].innerHTML);
    $("#refStatus").val(row.cells[8].innerHTML);
    $("#prmntAddrs").val(row.cells[9].innerHTML);
    $("#resdntlAddrs").val(row.cells[10].innerHTML);
    $("#designation").val(row.cells[11].innerHTML);
    $("#emailId").val(row.cells[12].innerHTML);

    $('#modal-reference-info').modal('show');
}

$('#cardRefModalSubmitBtn').click(function(event) {
    refModalInfo();
});

function clearData() {
    $("#refId").val("");
    $("#refName").val("");
    $("#relationship").val("");
    $("#refCompAddress").val("");
    $("#refProfession").val("");
    $("#designation").val("");
    $("#refTelNo").val("");
    $("#ext").val("");
    $("#refStatus").val("");
    $("#refMobile").val("");
    $("#prmntAddrs").val("");
    $("#resdntlAddrs").val("");
    $("#emailId").val("");
    $("#designation").val("");
}

$('#cardRefModalCancelBtn').click(function(event) {

    clearData();
});

$("#tab_reference").click(function (event) {
    //event.preventDefault();
     geteRefData();
});

function geteRefData() {
    var cardId1=$("#cardId").val();
    $.ajax({
        url: "/card/reference-info/list?cardId="+cardId1,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {

            $('#referenceInfo_tBody').replaceWith("<tbody id=\"referenceInfo_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");

            for(var i=0;i<data.referenceInfoList.length;i++){
                if (data.referenceInfoList[i]!=null){
                    var str="<tr>"+

                        "<td style=\"display:none;\">" + data.referenceInfoList[i].refId + "</td>" +
                        "<td>" + data.referenceInfoList[i].refName + "</td>" +
                        "<td>" + data.referenceInfoList[i].relationship + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].refMobile + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].refTelNo + "</td>" +
                        "<td>" + data.referenceInfoList[i].refProfession + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].ext + "</td>" +
                        "<td>" + data.referenceInfoList[i].refCompAddress + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].refStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].prmntAddrs + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].resdntlAddrs + "</td>" +
                        "<td>" + data.referenceInfoList[i].designation + "</td>" +
                        "<td style=\"display:none;\">" + data.referenceInfoList[i].emailId + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#referenceInfo_tBody').append(str);
                }
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function refValidation() {

    if($("#refName").val()==""){
        alert("Name must be filled out!");
        $("#refName").focus();
        return false;
    }

    if($("#refMobile").val()==""){
        alert("Mobile must be filled out!");
        $("#refMobile").focus();
        return false;
    }

    if($("#refProfession").val()==""){
        alert("Profession must be filled out!");
        $("#refProfession").focus();
        return false;
    }

    if($("#refCompAddress").val()==""){
        alert("Address must be filled out!");
        $("#refCompAddress").focus();
        return false;
    }

}
