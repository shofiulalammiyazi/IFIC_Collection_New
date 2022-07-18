var globalGurantorRow;


function showCityForPresent(id) {
    $("#grPrCity").html("");
    $("#prmCity").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#grPrCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#grPrCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }
            $("#grPmCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#grPmCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }
        },
        error: function (err) {
        }
    });
}
function showCityForPermanent(id) {
    $("#grPmCity").html("");
    //$("#prmCity").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#grPmCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#grPmCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }
            //
            //
            // $("#prmCity").append("<option selected=\"selected\">Select</option>");
            // for (i=0;i<data.cityList.length;i++){
            //     $("#prmCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            // }
        },
        error: function (err) {
        }
    });
}

function addGurantorInfo() {

    /*if (gurantorValidation()==false){
        return;
    }*/

    var formValues = $('#gurantorInfoForm').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    var decision=0;
    if ($("#guarantorId").val()!=""){
        decision=1;
    }

    $.ajax({
        url: '/card/gurantor-info/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonEmploymentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-guarantor-info').modal('hide');
            clearGurantorData();

            if (data.gurantorInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.guarantorId + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.customerId + "</td>" +
                    "<td>" + data.gurantorInfo.grFullName + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grName + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grNID + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grResidentialStatus + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grEduLevel + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grGender + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grTin + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grMaritalStatus + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grChildNo + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPassNo + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grDlNo + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grCarOwnership + "</td>" +
                    "<td>" + data.gurantorInfo.grRelationship + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grDob + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grMobile + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grOffice + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.guarantorPresentId + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrline1 + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrline2 + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrpostCode + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrCoutnry + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrCity + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrlivingPeriodYear + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPrlivingPeriodMonth + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.guarantorPermanentId + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grpmLine1 + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grpmLine2 + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPmpostCode + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPmCoutnry + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPmCity + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPmlivingPeriodYear + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grPmlivingPeriodMonth + "</td>" +
                    "<td>" + data.gurantorInfo.grOrgId + "</td>" +
                    "<td>" + data.gurantorInfo.grProId + "</td>" +
                    "<td>" + data.gurantorInfo.grDesignation + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grBusinessNature + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grDepartment + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grFrom + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.grdetails + "</td>" +
                    "<td style=\"display:none;\">" + data.gurantorInfo.cardId + "</td>" +

                    "<td><a class=\"btn btn-xs\" onclick=\"editGurantorRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteGurantorRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#guarantatorInfo_tBody').append(str);
            }
            else if(data.gurantorInfo != null && decision==1){
                globalGurantorRow.cells[0].innerHTML=data.gurantorInfo.guarantorId;
                globalGurantorRow.cells[1].innerHTML=data.gurantorInfo.customerId;
                globalGurantorRow.cells[2].innerHTML=data.gurantorInfo.grFullName;
                globalGurantorRow.cells[3].innerHTML=data.gurantorInfo.grName;
                globalGurantorRow.cells[4].innerHTML=data.gurantorInfo.grNID;
                globalGurantorRow.cells[5].innerHTML=data.gurantorInfo.grResidentialStatus;
                globalGurantorRow.cells[6].innerHTML=data.gurantorInfo.grEduLevel;
                globalGurantorRow.cells[7].innerHTML=data.gurantorInfo.grGender;
                globalGurantorRow.cells[8].innerHTML=data.gurantorInfo.grTin;
                globalGurantorRow.cells[9].innerHTML=data.gurantorInfo.grMaritalStatus;
                globalGurantorRow.cells[10].innerHTML=data.gurantorInfo.grChildNo;
                globalGurantorRow.cells[11].innerHTML=data.gurantorInfo.grPassNo;
                globalGurantorRow.cells[12].innerHTML=data.gurantorInfo.grDlNo;
                globalGurantorRow.cells[13].innerHTML=data.gurantorInfo.grCarOwnership;
                globalGurantorRow.cells[14].innerHTML=data.gurantorInfo.grRelationship;
                globalGurantorRow.cells[15].innerHTML=data.gurantorInfo.grDob;
                globalGurantorRow.cells[16].innerHTML=data.gurantorInfo.grMobile;
                globalGurantorRow.cells[17].innerHTML=data.gurantorInfo.grOffice;
                globalGurantorRow.cells[18].innerHTML=data.gurantorInfo.guarantorPresentId;
                globalGurantorRow.cells[19].innerHTML=data.gurantorInfo.grPrline1;
                globalGurantorRow.cells[20].innerHTML=data.gurantorInfo.grPrline2;
                globalGurantorRow.cells[21].innerHTML=data.gurantorInfo.grPrpostCode;
                globalGurantorRow.cells[22].innerHTML=data.gurantorInfo.grPrCoutnry;
                globalGurantorRow.cells[23].innerHTML=data.gurantorInfo.grPrCity;
                globalGurantorRow.cells[24].innerHTML=data.gurantorInfo.grPrlivingPeriodYear;
                globalGurantorRow.cells[25].innerHTML=data.gurantorInfo.grPrlivingPeriodMonth;
                globalGurantorRow.cells[26].innerHTML=data.gurantorInfo.guarantorPermanentId;
                globalGurantorRow.cells[27].innerHTML=data.gurantorInfo.grpmLine1;
                globalGurantorRow.cells[28].innerHTML=data.gurantorInfo.grpmLine2;
                globalGurantorRow.cells[29].innerHTML=data.gurantorInfo.grPmpostCode;
                globalGurantorRow.cells[30].innerHTML=data.gurantorInfo.grPmCoutnry;
                globalGurantorRow.cells[31].innerHTML=data.gurantorInfo.grPmCity;
                globalGurantorRow.cells[32].innerHTML=data.gurantorInfo.grPmlivingPeriodYear;
                globalGurantorRow.cells[33].innerHTML=data.gurantorInfo.grPmlivingPeriodMonth;
                globalGurantorRow.cells[34].innerHTML=data.gurantorInfo.grOrgId;
                globalGurantorRow.cells[35].innerHTML=data.gurantorInfo.grProId;
                globalGurantorRow.cells[36].innerHTML=data.gurantorInfo.grDesignation;
                globalGurantorRow.cells[37].innerHTML=data.gurantorInfo.grBusinessNature;
                globalGurantorRow.cells[38].innerHTML=data.gurantorInfo.grDepartment;
                globalGurantorRow.cells[39].innerHTML=data.gurantorInfo.grFrom;
                globalGurantorRow.cells[40].innerHTML=data.gurantorInfo.cardId;
            }
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Gurantor Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
           // console.log(err);
        }
    });
}

$('#gurantorInfoSaveButton').click(function(event) {
    event.preventDefault();
    addGurantorInfo();
});

$("#gurantorInfoCancelButton").click(function (event) {
    clearGurantorData();
});

$("#gurantorBtn").click(function (event) {
    //event.preventDefault();
    // //alert("modal clicked!");
    clearGurantorData();
});

function editGurantorRow(node){
    var row=node.parentNode.parentNode;
    globalGurantorRow=row;

    $("#guarantorId").val(row.cells[0].innerHTML);
    $("#customerId").val(row.cells[1].innerHTML);
    $("#grFullName").val(row.cells[2].innerHTML);
    $("#grName").val(row.cells[3].innerHTML);
    $("#grNID").val(row.cells[4].innerHTML);
    $("#grResidentialStatus").val(row.cells[5].innerHTML);
    $("#grEduLevel").val(row.cells[6].innerHTML);
    $("#grGender").val(row.cells[7].innerHTML);
    $("#grTin").val(row.cells[8].innerHTML);
    $("#grMaritalStatus").val(row.cells[9].innerHTML);
    $("#grChildNo").val(row.cells[10].innerHTML);
    $("#grPassNo").val(row.cells[11].innerHTML);
    $("#grDlNo").val(row.cells[12].innerHTML);
    $("#grCarOwnership").val(row.cells[13].innerHTML);
    $("#grRelationship").val(row.cells[14].innerHTML);
    $("#grDob").val(row.cells[15].innerHTML);
    $("#grMobile").val(row.cells[16].innerHTML);
    $("#grOffice").val(row.cells[17].innerHTML);
    $("#guarantorPresentId").val(row.cells[18].innerHTML);
    $("#grPrline1").val(row.cells[19].innerHTML);
    $("#grPrline2").val(row.cells[20].innerHTML);
    $("#grPrpostCode").val(row.cells[21].innerHTML);
    $("#grPrCoutnry").val(row.cells[22].innerHTML);
    $("#grPrCity").val(row.cells[23].innerHTML);
    $("#grPrlivingPeriodYear").val(row.cells[24].innerHTML);
    $("#grPrlivingPeriodMonth").val(row.cells[25].innerHTML);
    $("#guarantorPermanentId").val(row.cells[26].innerHTML);
    $("#grpmline1").val(row.cells[27].innerHTML);
    $("#grpmLine2").val(row.cells[28].innerHTML);
    $("#grPmpostCode").val(row.cells[29].innerHTML);
    $("#grPmCoutnry").val(row.cells[30].innerHTML);
    $("#grPmCity").val(row.cells[31].innerHTML);
    $("#grPmlivingPeriodYear").val(row.cells[32].innerHTML);
    $("#grPmlivingPeriodMonth").val(row.cells[33].innerHTML);
    $("#grOrgId").val(row.cells[34].innerHTML);
    $("#grProId").val(row.cells[35].innerHTML);
    $("#grDesignation").val(row.cells[36].innerHTML);
    $("#grBusinessNature").val(row.cells[37].innerHTML);
    $("#grDepartment").val(row.cells[38].innerHTML);
    $("#grFrom").val(row.cells[39].innerHTML);
    $("#cardId").val(row.cells[40].innerHTML);

    $("#modal-guarantor-info").modal("show");
}

function deleteGurantorRow(node){
    var gurantorId=node.parentNode.parentNode.cells[0].innerHTML;
    $.ajax({
        url: "/card/gurantor-info/remove?gurantorId="+gurantorId,
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

$(document).ready(function () {
    $('#gurantatorCheck').on('click',function () {
        if(this.checked){
            $('#grpmline1').val($('#grPrline1').val());
            $('#grpmLine2').val($('#grPrline2').val());
            $('#grPmpostCode').val($('#grPrpostCode').val());
            $('#grPmCoutnry').val($('#grPrCoutnry').val());
            $('#grPmCity').val($('#grPrCity').val());
            $('#grPmlivingPeriodYear').val($('#grPrlivingPeriodYear').val());
            $('#grPmlivingPeriodMonth').val($('#grPrlivingPeriodMonth').val());
        }
        else{
            $('#grpmline1').val('');
            $('#grpmLine2').val('');
            $('#grPmpostCode').val('');
            $('#grPmCoutnry').val('');
            $('#grPmCity').val('');
            $('#grPmlivingPeriodYear').val('');
            $('#grPmlivingPeriodMonth').val('');
        }
    })
});

function clearGurantorData() { ////alert("modal clicked!");
    $("#guarantorId").val("");
    $("#grFullName").val("");
    $("#grName").val("");
    $("#grNID").val("");
    $("#grResidentialStatus").val("");
    $("#grEduLevel").val("");
    $("#grGender").val("");
    $("#grTin").val("");
    $("#grMaritalStatus").val("");
    $("#grChildNo").val("");
    $("#grPassNo").val("");
    $("#grDlNo").val("");
    $("#grCarOwnership").val("");
    $("#grRelationship").val("");
    $("#grDob").val("");
    $("#grMobile").val("");
    $("#grOffice").val("");
    $("#guarantorPresentId").val("");
    $("#grPrline1").val("");
    $("#grPrline2").val("");
    $("#grPrpostCode").val("");
    $("#grPrCoutnry").val("");
    $("#grPrCity").val("");
    $("#grPrlivingPeriodYear").val("");
    $("#grPrlivingPeriodMonth").val("");
    $("#guarantorPermanentId").val("");
    $("#grpmline1").val("");
    $("#grpmLine2").val("");
    $("#grPmpostCode").val("");
    $("#grPmCoutnry").val("");
    $("#grPmCity").val("");
    $("#grPmlivingPeriodYear").val("");
    $("#grPmlivingPeriodMonth").val("");
    $("#grProId").val("");
    $("#grOrgId").val("");
    $("#grDesignation").val("");
    $("#grBusinessNature").val("");
    $("#grDepartment").val("");
    $("#grFrom").val("");
    //$("#gurantatorCheck").prop('checked', false);
    
}

$("#tab_guarantor").click(function (event) {

    var cardId= $("#cardId").val();
    $.ajax({
        url: "/card/gurantor-info/list?cardId="+cardId,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {

            $('#guarantatorInfo_tBody').replaceWith("<tbody id=\"guarantatorInfo_tBody\" >\n" +
                "\t\t\t\t</tbody>");
            
            for (var i=0;i<data.gurantorInfoList.length;i++){
                if (data.gurantorInfoList[i]!= null){
                    var str="<tr>"+

                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].guarantorId + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].customerId + "</td>" +
                        "<td>" + data.gurantorInfoList[i].grFullName + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grName + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grNID + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grResidentialStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grEduLevel + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grGender + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grTin + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grMaritalStatus + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grChildNo + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPassNo + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grDlNo + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grCarOwnership + "</td>" +
                        "<td>" + data.gurantorInfoList[i].grRelationship + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grDob + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grMobile + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grOffice + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].guarantorPresentId + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrline1 + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrline2 + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrpostCode + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrCoutnry + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrCity + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrlivingPeriodYear + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPrlivingPeriodMonth + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].guarantorPermanentId + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grpmLine1 + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grpmLine2 + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPmpostCode + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPmCoutnry + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPmCity + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPmlivingPeriodYear + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grPmlivingPeriodMonth + "</td>" +
                        "<td>" + data.gurantorInfoList[i].grOrgId + "</td>" +
                        "<td>" + data.gurantorInfoList[i].grProId + "</td>" +
                        "<td>" + data.gurantorInfoList[i].grDesignation + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grBusinessNature + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grDepartment + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grFrom + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].grdetails + "</td>" +
                        "<td style=\"display:none;\">" + data.gurantorInfoList[i].cardId + "</td>" +

                        "<td><a class=\"btn btn-xs\" onclick=\"editGurantorRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteGurantorRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +

                        "</tr>";
                    $('#guarantatorInfo_tBody').append(str);
                }
            }
        },
        error: function (data) {
            console.log("Failure!");
        }
    });
});

function gurantorValidation() {
    if ($("#grFullName").val()==""){
        alert("Name must be filled out!");
        $("#grFullName").focus();
        return false;
    }

    if ($("#grName").val()==""){
        alert("Name must be filled out!");
        $("#grName").focus();
        return false;
    }

    if ($("#grNID").val()==""){
        alert("NID must be filled out!");
        $("#grNID").focus();
        return false;
    }

    if ($("#grMaritalStatus").val()==""){
        alert("Marital Status must be filled out!");
        $("#grMaritalStatus").focus();
        return false;
    }

    if ($("#grRelationship").val()==""){
        alert("RelationShip must be filled out!");
        $("#grRelationship").focus();
        return false;
    }

    if ($("#grPrline1").val()==""){
        alert("Address must be filled out!");
        $("#grPrline1").focus();
        return false;
    }

    if ($("#grPrpostCode").val()==""){
        alert("Post Code must be filled out!");
        $("#grPrpostCode").focus();
        return false;
    }

    if ($("#grPrCoutnry").val()==""){
        alert("Country must be filled out!");
        $("#grPrCoutnry").focus();
        return false;
    }

    if ($("#grpmline1").val()==""){
        alert("Address must be filled out!");
        $("#grpmline1").focus();
        return false;
    }

    if ($("#grPmpostCode").val()==""){
        alert("Post Code must be filled out!");
        $("#grPmpostCode").focus();
        return false;
    }

    if ($("#grPmCoutnry").val()==""){
        alert("Country must be filled out!");
        $("#grPmCoutnry").focus();
        return false;
    }

    if ($("#grProId").val()==""){
        alert("Profession must be filled out!");
        $("#grProId").focus();
        return false;
    }

    if ($("#grOrgId").val()==""){
        alert("Company Name must be filled out!");
        $("#grOrgId").focus();
        return false;
    }

    if ($("#grPmpostCode").val()==""){
        alert("Post Code must be filled out!");
        $("#grPmpostCode").focus();
        return false;
    }
}