
function setChild() {
    $("#inchildrenNo").val('0');
}

function introduceInfo() {
    if (validateIntroducer()==false){
        return;
    }

    var decision=0;
    if($("#inId").val()!="") decision=1;
    var formValues = $('#introduceInfoForm').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/branch-level-comments/introducer-info/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonEmploymentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-introduce-info').modal('hide');
            crearIntroduceData();
            if (data.introduceInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inId + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.incustomerCode1 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.innickName1 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inintroDob + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.innid1 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.ineducationLevel1 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.ingender + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.intinNo + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inmaritalStatus + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inchildrenNo + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inpassportNo + "</td>" +

                    "<td style=\"display:none;\">" + data.introduceInfo.inexpDate + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.incarOwnership + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.indrivingLicenseNo + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.indlDate + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inresidentialStatus + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inaddress11 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inaddress12 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inpostCode11 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.incountry11 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.incity11 + "</td>" +

                    "<td style=\"display:none;\">" + data.introduceInfo.inlivingPeriodYear11 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inlivingPeriodMonth11 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inaddress21 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inaddress22 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inpostCode22 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.incountry22 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.incity22 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inlivingPeriodYear22 + "</td>" +
                    "<td style=\"display:none;\">" + data.introduceInfo.inlivingPeriodMonth22 + "</td>" +
                    "<td>" + data.introduceInfo.infullName1+ "</td>" +
                    "<td>" + data.introduceInfo.inrelationship + "</td>" +
                    "<td><a class=\"btn btn-xs\" onclick=\"editIntroducerRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteIntroducerRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#introduceInfo_tBody').append(str);
            }
            else if (data.introduceInfo != null && decision==1){
            	//incustomerCode1
                //globalRow.cells[1].innerHTML=data.introduceInfo.infullName1;
                globalRow.cells[1].innerHTML=data.introduceInfo.incustomerCode1;
                globalRow.cells[2].innerHTML=data.introduceInfo.innickName1;
                globalRow.cells[3].innerHTML=data.introduceInfo.inintroDob;
                globalRow.cells[4].innerHTML=data.introduceInfo.innid1;
                globalRow.cells[5].innerHTML=data.introduceInfo.ineducationLevel1;
                globalRow.cells[6].innerHTML=data.introduceInfo.ingender;
                globalRow.cells[7].innerHTML=data.introduceInfo.intinNo;
                globalRow.cells[8].innerHTML=data.introduceInfo.inmaritalStatus;
                globalRow.cells[9].innerHTML=data.introduceInfo.inchildrenNo;
                globalRow.cells[10].innerHTML=data.introduceInfo.inpassportNo;

                globalRow.cells[11].innerHTML=data.introduceInfo.inexpDate;
                globalRow.cells[12].innerHTML=data.introduceInfo.incarOwnership;
                globalRow.cells[13].innerHTML=data.introduceInfo.indrivingLicenseNo;
                globalRow.cells[14].innerHTML=data.introduceInfo.indlDate;
                globalRow.cells[15].innerHTML=data.introduceInfo.inresidentialStatus;
                globalRow.cells[16].innerHTML=data.introduceInfo.inaddress11;
                globalRow.cells[17].innerHTML=data.introduceInfo.inaddress12;
                globalRow.cells[18].innerHTML=data.introduceInfo.inpostCode11;
                globalRow.cells[19].innerHTML=data.introduceInfo.incountry11;
                globalRow.cells[20].innerHTML=data.introduceInfo.incity11;

                globalRow.cells[21].innerHTML=data.introduceInfo.inlivingPeriodYear11;
                globalRow.cells[22].innerHTML=data.introduceInfo.inlivingPeriodMonth11;
                globalRow.cells[23].innerHTML=data.introduceInfo.inaddress21;
                globalRow.cells[24].innerHTML=data.introduceInfo.inaddress22;
                globalRow.cells[25].innerHTML=data.introduceInfo.inpostCode22;
                globalRow.cells[26].innerHTML=data.introduceInfo.incountry22;
                globalRow.cells[27].innerHTML=data.introduceInfo.incity22;
                globalRow.cells[28].innerHTML=data.introduceInfo.inlivingPeriodYear22;
                globalRow.cells[29].innerHTML=data.introduceInfo.inlivingPeriodMonth22;
                globalRow.cells[30].innerHTML=data.introduceInfo.infullName1;
                globalRow.cells[31].innerHTML=data.introduceInfo.inrelationship;
            }
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Introducer Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}


$('#introduceInfoSaveButton').click(function(event) {
    event.preventDefault();
    introduceInfo();
});


$('#introduceInfoCloseButton, #introduceInfoCloseButtonTop').click(function(event) {
    event.preventDefault();
    $('#modal-introduce-info').modal('hide');
    crearIntroduceData();
});


function crearIntroduceData() {
    $("#inrelationship").val("");
    $("#infullName1").val("");
    $("#innickName1").val("");
    $("#inintroDob").val("");
    $("#innid1").val("");
    $("#ineducationLevel1").val("");
    $("#ingender").val("");
    $("#intinNo").val("");
    $("#inmaritalStatus").val("");
    $("#inchildrenNo").val("");
    $("#inpassportNo").val("");
    $("#inexpDate").val("");
    $("#incarOwnership").val("");
    $("#indrivingLicenseNo").val("");
    $("#indlDate").val("");
    $("#inresidentialStatus").val("");
    $("#inaddress11").val("");
    $("#inaddress12").val("");
    $("#inpostCode11").val("");
    $("#incountry11").val("");
    $("#incity11").val("");
    $("#inlivingPeriodYear11").val("");
    $("#inlivingPeriodMonth11").val("");
    $("#inaddress21").val("");
    $("#inaddress22").val("");
    $("#inpostCode22").val("");
    $("#incountry22").val("");
    $("#incity22").val("");
    $("#inlivingPeriodYear22").val("");
    $("#inlivingPeriodMonth22").val("");
}

function editIntroducerRow(node){
    //alert("Edit Button Clicked!");
    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#inId").val(row.cells[0].innerHTML);
    $("#incustomerCode1").val(row.cells[1].innerHTML);
    $("#innickName1").val(row.cells[2].innerHTML);
    $("#inintroDob").val(row.cells[3].innerHTML);
    $("#innid1").val(row.cells[4].innerHTML);
    $("#ineducationLevel1").val(row.cells[5].innerHTML);
    $("#ingender").val(row.cells[6].innerHTML);
    $("#intinNo").val(row.cells[7].innerHTML);
    $("#inmaritalStatus").val(row.cells[8].innerHTML);
    $("#inchildrenNo").val(row.cells[9].innerHTML);
    $("#inpassportNo").val(row.cells[10].innerHTML);

    $("#inexpDate").val(row.cells[11].innerHTML);
    $("#incarOwnership").val(row.cells[12].innerHTML);
    $("#indrivingLicenseNo").val(row.cells[13].innerHTML);
    $("#indlDate").val(row.cells[14].innerHTML);
    $("#inresidentialStatus").val(row.cells[15].innerHTML);
    $("#inaddress11").val(row.cells[16].innerHTML);
    $("#inaddress12").val(row.cells[17].innerHTML);
    $("#inpostCode11").val(row.cells[18].innerHTML);
    $("#incountry11").val(row.cells[19].innerHTML);
    $("#incity11").val(row.cells[20].innerHTML);

    $("#inlivingPeriodYear11").val(row.cells[21].innerHTML);
    $("#inlivingPeriodMonth11").val(row.cells[22].innerHTML);
    $("#inaddress21").val(row.cells[23].innerHTML);
    $("#inaddress22").val(row.cells[24].innerHTML);
    $("#inpostCode22").val(row.cells[25].innerHTML);
    $("#incountry22").val(row.cells[26].innerHTML);
    $("#incity22").val(row.cells[27].innerHTML);
    $("#inlivingPeriodYear22").val(row.cells[28].innerHTML);
    $("#inlivingPeriodMonth22").val(row.cells[29].innerHTML);
    $("#infullName1").val(row.cells[30].innerHTML);
    $("#inrelationship").val(row.cells[31].innerHTML);

    $('#modal-introduce-info').modal('show');
}


function deleteIntroducerRow(node){
    var id=node.parentNode.parentNode.cells[0].innerHTML;
    $.ajax({
        url: "/card/branch-level-comments/introducer-info/remove?id="+id,
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


function validateIntroducer() {
    if ($("#infullName1").val()==""){
        alert("Name Must be filled out!");
        $("#infullName1").focus();
        return false;
    }

    if ($("#innid1").val()==""){
        alert("National Id Must be filled out!");
        $("#innid1").focus();
        return false;
    }

    if ($("#ineducationLevel1").val()==""){
        alert("Education Level Must be filled out!");
        $("#ineducationLevel1").focus();
        return false;
    }

    if ($("#ingender").val()==""){
        alert("Gender Must be filled out!");
        $("#ingender").focus();
        return false;
    }

    /*if ($("#tinNo").val()==""){
        alert("TIN Must be filled out!");
        $("#tinNo").focus();
        return false;
    }
*/
    if ($("#inmaritalStatus").val()==""){
        alert("Marital Status Must be filled out!");
        $("#inmaritalStatus").focus();
        return false;
    }

    if ($("#inchildrenNo").val()==""){
        alert("Number Of Children Must be filled out!");
        $("#inchildrenNo").focus();
        return false;
    }

    /* if ($("#passportNo").val()==""){
         alert("Passport No Must be filled out!");
         $("#passportNo").focus();
         return false;
     }
 */
    if ($("#incarOwnership").val()==""){
        alert("Car Ownership Status Must be filled out!");
        $("#incarOwnership").focus();
        return false;
    }

    /*if ($("#drivingLicenseNo").val()==""){
        alert("Driving License Must be filled out!");
        $("#drivingLicenseNo").focus();
        return false;
    }*/

    if ($("#inresidentialStatus").val()==""){
        alert("Residential Status Must be filled out!");
        $("#inresidentialStatus").focus();
        return false;
    }

    if ($("#inpostCode11").val()==""){
        alert("Present Post Code Must be filled out!");
        $("#inpostCode11").focus();
        return false;
    }

    if ($("#incountry11").val()==""){
        alert("Present Country Must be filled out!");
        $("#incountry11").focus();
        return false;
    }

    if ($("#incity11").val()==""){
        alert("Present City Must be filled out!");
        $("#incity11").focus();
        return false;
    }

    if ($("#inlivingPeriodYear11").val()==""){
        alert("Present Living Year Must be filled out!");
        $("#inlivingPeriodYear11").focus();
        return false;
    }

    if ($("#inlivingPeriodMonth11").val()==""){
        alert("Present Living Month Must be filled out!");
        $("#inlivingPeriodMonth11").focus();
        return false;
    }

    if ($("#inpostCode22").val()==""){
        alert("Post Code Must be filled out!");
        $("#inpostCode22").focus();
        return false;
    }

    if ($("#incountry22").val()==""){
        alert("Country Must be filled out!");
        $("#incountry22").focus();
        return false;
    }

    if ($("#incity22").val()==""){
        alert("City Must be filled out!");
        $("#incity22").focus();
        return false;
    }

    if ($("#inlivingPeriodYear22").val()==""){
        alert("Year Must be filled out!");
        $("#inlivingPeriodYear22").focus();
        return false;
    }

    if ($("#inlivingPeriodMonth22").val()==""){
        alert("Month Must be filled out!");
        $("#inlivingPeriodMonth22").focus();
        return false;
    }

    // number validation

    var x = $("#inchildrenNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Children No must be valid..");
        $("#inchildrenNo").focus();
        return false;
    }

    var x = $("#innid1").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("National ID must be valid..");
        $("#innid1").focus();
        return false;
    }

    var x = $("#intinNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("TIN must be valid..");
        $("#intinNo").focus();
        return false;
    }

    var x = $("#inpassportNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Passport No must be valid..");
        $("#inpassportNo").focus();
        return false;
    }


    var x = $("#inpostCode11").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Present Post Code must be valid..");
        $("#inpostCode11").focus();
        return false;
    }

    var x = $("#inpostCode22").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Parmanent Post Code must be valid..");
        $("#inpostCode22").focus();
        return false;
    }
}



$('#tab-introducer, #tab_branch_level').click(function () {
    //alert("cr card clicked...");
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    var id = $("#personalCustomerIdEdit").val();

    $.ajax({
        url: "/card/branch-level-comments/introducer-info/list?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            //alert("Success");
            $('#introduceInfo_tBody').replaceWith("<tbody id=\"introduceInfo_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.introduceInfoList.length;i++) {
                if (data.introduceInfoList[i] != null) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inId + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].incustomerCode1 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].innickName1 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inintroDob + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].innid1 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].ineducationLevel1 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].ingender + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].intinNo + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inmaritalStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inchildrenNo + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inpassportNo + "</td>" +

                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inexpDate + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].incarOwnership + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].indrivingLicenseNo + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].indlDate + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inresidentialStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inaddress11 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inaddress12 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inpostCode11 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].incountry11 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].incity11 + "</td>" +

                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inlivingPeriodYear11 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inlivingPeriodMonth11 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inaddress21 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inaddress22 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inpostCode22 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].incountry22 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].incity22 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inlivingPeriodYear22 + "</td>" +
                        "<td style=\"display:none;\">" + data.introduceInfoList[i].inlivingPeriodMonth22 + "</td>" +
                        "<td>" + data.introduceInfoList[i].infullName1+ "</td>" +
                        "<td>" + data.introduceInfoList[i].inrelationship + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editIntroducerRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteIntroducerRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#introduceInfo_tBody').append(str);
                }
            }
        },
        error: function () {
            //alert("Error");
        }
    });
});



// Present address to Permanent address
$(document).ready(function () {
    $('#check22').on('click',function () {

        if (this.checked){
            $('#inaddress21').val($('#inaddress11').val());
            $('#inaddress22').val($('#inaddress12').val());
            $('#inpostCode22').val($('#inpostCode11').val());
            $('#incountry22').val($('#incountry11').val());
            $('#incity22').val($('#incity11').val());
            $('#inlivingPeriodYear22').val($('#inlivingPeriodYear11').val());
            $('#inlivingPeriodMonth22').val($('#inlivingPeriodMonth11').val());
        }
        else {
            $('#inaddress21').val('');
            $('#inaddress22').val('');
            $('#inpostCode22').val('');
            $('#incountry22').val('');
            $('#incity22').val('');
            $('#inlivingPeriodYear22').val('');
            $('#inlivingPeriodMonth22').val('');
        }
    })
})

