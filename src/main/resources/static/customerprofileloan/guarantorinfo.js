
var guarantorNode;


$("#tab-gurantor").click(function () {
    //var params = new window.URLSearchParams(window.location.search);
    var accountNo = loanConcatNumber;
    $.ajax({
       // url: "/customerloanprofile/guarantorinfo/view?id="+params.get('account'),
        url: "/customerloanprofile/guarantorinfo/view?id="+accountNo,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if(data.guarantorInfo.length>0){
                $('#guarantorinfo_tBody').replaceWith("<tbody id=\"guarantorinfo_tBody\" >\n" +
                    "\t\t\t\t\t\t\t</tbody>");

                for(var i=0;i<data.guarantorInfo.length;i++){
                    if (data.guarantorInfo[i]!=null){
                        var str="<tr>"+

                            "<td style=\"display:none;\" id=\"guaId\">" + data.guarantorInfo[i].id + "</td>" +
                            "<td>" +( data.guarantorInfo[i].accountNo==null?'':data.guarantorInfo[i].accountNo )+ "</td>" +
                            "<td>" + (data.guarantorInfo[i].name==null?'':data.guarantorInfo[i].name ) + "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].occupation==null?'':data.guarantorInfo[i].occupation )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].companyName==null?'':data.guarantorInfo[i].companyName )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].designation==null?'':data.guarantorInfo[i].designation )+ "</td>" +
                            "<td >" + (data.guarantorInfo[i].phoneNo==null?'':data.guarantorInfo[i].phoneNo ) + "</td>" +
                            "<td>"  +( data.guarantorInfo[i].homeAddress==null?'':data.guarantorInfo[i].homeAddress  )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].officeAddress==null?'':data.guarantorInfo[i].officeAddress )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].permanentAddress==null?'':data.guarantorInfo[i].permanentAddress )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].dob==null?'':data.guarantorInfo[i].dob )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].nidOrPassport==null?'':data.guarantorInfo[i].nidOrPassport )+ "</td>" +
                            "<td>" + (data.guarantorInfo[i].relationGuarantor ==null?'':data.guarantorInfo[i].relationGuarantor ) + "</td>" +
                            "<td style=\"display:none;\">" +( data.guarantorInfo[i].facilityWithBank==null?'':data.guarantorInfo[i].facilityWithBank )+ "</td>" +
                            "<td>" + (data.guarantorInfo[i].status==null?'':data.guarantorInfo[i].status ) + "</td>" +
                            "<td style=\"display:none;\">" + (data.guarantorInfo[i].guarantorFatherName == null ? '' : data.guarantorInfo[i].guarantorFatherName) + "</td>" +
                            "<td style=\"display:none;\">" + (data.guarantorInfo[i].guarantorMotherName == null ? '' : data.guarantorInfo[i].guarantorMotherName) + "</td>" +
                            "<td>"+(data.guarantorInfo[i].status == 'PENDING' ? '': '<a class=\"btn btn-xs\" onclick=\"editGuarantorRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>') +
                            "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"viewGuarantorRow(this)\"> <i class=\"fa fa-trash\"></i>view</a></td>" +
                            "</tr>";
                        $('#guarantorinfo_tBody').append(str);
                    }
                }
            }

        },
        error: function (data) {
            //alert("Failure!");
        }
    });
})

function guarantorInformationSave() {

    if($("#guarantorName").val() == ""){
        alert("Name must not be null");
        return;
    }

    var formValue = $('#guarantorInfoSaveForm').serializeJSON();
    var loanAccountNo =$('input[name="account"]').val();
    formValue.loanAccountNo=loanAccountNo;
    var jsonGuarantorInfo = JSON.stringify(formValue);
    jsonGuarantorInfo.loanAccountNo =$('input[name="account"]').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var decision=0;
    if($("#guarantor-id").val()!="")
        decision=1;

    $.ajax({
        url: '/customerloanprofile/guarantorinfo/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonGuarantorInfo,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);
            $('#modal-guarantor-info').modal('hide');
            enableFormAndClearFormData();
            if(data.guarantorInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\" id=\"guaId\">" + data.guarantorInfo.id + "</td>" +
                    "<td>" + (data.guarantorInfo.accountNo == null ? '' : data.guarantorInfo.accountNo) + "</td>" +
                    "<td>" + (data.guarantorInfo.name == null ? '' : data.guarantorInfo.name) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.occupation == null ? '' : data.guarantorInfo.occupation) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.companyName == null ? '' : data.guarantorInfo.companyName) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.designation == null ? '' : data.guarantorInfo.designation) + "</td>" +
                    "<td >" + (data.guarantorInfo.phoneNo == null ? '' : data.guarantorInfo.phoneNo) + "</td>" +
                    "<td>" + (data.guarantorInfo.homeAddress == null ? '' : data.guarantorInfo.homeAddress) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.officeAddress == null ? '' : data.guarantorInfo.officeAddress) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.permanentAddress == null ? '' : data.guarantorInfo.permanentAddress) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.dob == null ? '' : data.guarantorInfo.dob) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.nidOrPassport == null ? '' : data.guarantorInfo.nidOrPassport) + "</td>" +
                    "<td>" + (data.guarantorInfo.relationGuarantor == null ? '' : data.guarantorInfo.relationGuarantor) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.facilityWithBank == null ? '' : data.guarantorInfo.facilityWithBank) + "</td>" +
                    "<td>" + (data.guarantorInfo.status == null ? '' : data.guarantorInfo.status) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.guarantorFatherName == null ? '' : data.guarantorInfo.guarantorFatherName) + "</td>" +
                    "<td style=\"display:none;\">" + (data.guarantorInfo.guarantorMotherName == null ? '' : data.guarantorInfo.guarantorMotherName) + "</td>" +
                    "<td>" + (data.guarantorInfo.status == 'PENDING' ? '' : '<a class=\"btn btn-xs\" onclick=\"editGuarantorRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>') +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"viewGuarantorRow(this)\"> <i class=\"fa fa-trash\"></i>view</a></td>" +
                    "</tr>";
                $("#guarantorinfo_tBody").append(str);
            } else if (data.guarantorInfo != null && decision==1){
                globalRow.cells[0].innerHTML=data.guarantorInfo.id;
                globalRow.cells[1].innerHTML=data.guarantorInfo.accountNo;
                globalRow.cells[2].innerHTML=data.guarantorInfo.name;
                globalRow.cells[3].innerHTML=data.guarantorInfo.occupation;
                globalRow.cells[4].innerHTML=data.guarantorInfo.companyName;
                globalRow.cells[5].innerHTML=data.guarantorInfo.designation;
                globalRow.cells[6].innerHTML=data.guarantorInfo.phoneNo;
                globalRow.cells[7].innerHTML=data.guarantorInfo.homeAddress;
                globalRow.cells[8].innerHTML=data.guarantorInfo.officeAddress;
                globalRow.cells[9].innerHTML=data.guarantorInfo.permanentAddress;
                globalRow.cells[10].innerHTML=data.guarantorInfo.dob;
                globalRow.cells[11].innerHTML=data.guarantorInfo.nidOrPassport;
                globalRow.cells[12].innerHTML=data.guarantorInfo.relationGuarantor;
                globalRow.cells[13].innerHTML=data.guarantorInfo.facilityWithBank;
                globalRow.cells[14].innerHTML=data.guarantorInfo.status;
                globalRow.cells[15].innerHTML=data.guarantorInfo.guarantorFatherName;
                globalRow.cells[16].innerHTML=data.guarantorInfo.guarantorMotherName;
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


function editGuarantorRow(node){

    var row=node.parentNode.parentNode;
    globalRow=row;
    guarantorNode=node;
    $("#guarantor-id").val(row.cells[0].innerHTML);
    $("#guaccNo").val(row.cells[1].innerHTML);
    $("#guarantorName").val(row.cells[2].innerHTML);
    $("#guarantor-occupation").val(row.cells[3].innerHTML);
    $("#guarantor-companyName").val(row.cells[4].innerHTML);
    $("#guarannntorDesignation").val(row.cells[5].innerHTML);
    $("#guarantorPhone").val(row.cells[6].innerHTML);
    $("#gurantorHomeAddress").val(row.cells[7].innerHTML);
    $("#guarantor-OfficeAddress").val(row.cells[8].innerHTML);
    $("#guarantor-PermanentAddress").val(row.cells[9].innerHTML);
    $("#guarantor-dob").val(row.cells[10].innerHTML);
    $("#guarantorNid").val(row.cells[11].innerHTML);
    $("#relGuarantor").val(row.cells[12].innerHTML);
    $("#facilityWithBank").val(row.cells[13].innerHTML);
    $("#guaranntorFather").val(row.cells[15].innerHTML);
    $("#guarantorMother").val(row.cells[16].innerHTML);

    $('#modal-guarantor-info').modal('show');
}

function viewGuarantorRow(node) {
    var row=node.parentNode.parentNode;
    $("#guarantor-id").val(row.cells[0].innerHTML);
    $("#guaccNo").val(row.cells[1].innerHTML);
    $("#guarantorName").val(row.cells[2].innerHTML);
    $("#guarantor-occupation").val(row.cells[3].innerHTML);
    $("#guarantor-companyName").val(row.cells[4].innerHTML);
    $("#guarannntorDesignation").val(row.cells[5].innerHTML);
    $("#guarantorPhone").val(row.cells[6].innerHTML);
    $("#gurantorHomeAddress").val(row.cells[7].innerHTML);
    $("#guarantor-OfficeAddress").val(row.cells[8].innerHTML);
    $("#guarantor-PermanentAddress").val(row.cells[9].innerHTML);
    $("#guarantor-dob").val(row.cells[10].innerHTML);
    $("#guarantorNid").val(row.cells[11].innerHTML);
    $("#relGuarantor").val(row.cells[12].innerHTML);
    $("#facilityWithBank").val(row.cells[13].innerHTML);
    $("#guaranntorFather").val(row.cells[15].innerHTML);
    $("#guarantorMother").val(row.cells[16].innerHTML);

    $("#modal-guarantor-info").appendTo("body");
    $("#guarantorInfoSaveForm").find(':input').attr("disabled", "disabled");
    $("#guarantor-cancel-btn").removeAttr("disabled");
    $("#guarantor-info-save-btn").hide();
    $('#modal-guarantor-info').modal({ show: true, backdrop: false, keyboard: false });


}



function enableFormAndClearFormData() {
    $("#guarantorInfoSaveForm").find(':input').attr("disabled", false);
    $("#guarantor-info-save-btn").show();

    $("#guarantor-id").val('');
    $("#guaccNo").val('');
    $("#guarantorName").val('');
    $("#guarantor-occupation").val('');
    $("#guarantor-companyName").val('');
    $("#guarannntorDesignation").val('');
    $("#guarantorPhone").val('');
    $("#gurantorHomeAddress").val('');
    $("#guarantor-OfficeAddress").val('');
    $("#guarantor-PermanentAddress").val('');
    $("#guarantor-dob").val('');
    $("#guarantorNid").val('');
    $("#relGuarantor").val('');
    $("#facilityWithBank").val('');
    $("#guaranntorFather").val('');
    $("#guarantorMother").val('');
}