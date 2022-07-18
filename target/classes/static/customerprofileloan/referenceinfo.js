//Setu Bishwas, Nabil (On 25th August,2019)
//Updated by Abu Salek Shaon,  (On 10th april,2021)
//Updated by tajkia
var globalRow;
var refrencenode;

/*function isPhoneValid(phoneNo) {
    if (phoneNo.match("[+\\s]\\d{2}\\d{5}[-\\ \\s]\\d{6}")) return true;
    //validating phone number with extension length from 3 to 5
    else if (phoneNo.match("\\d{2}\\d{5}[-\\ \\s]\\d{6}")) return true;
    //validating phone number where area code is in braces ()
    else if (phoneNo.match("\\d{5}[-\\ \\s]\\d{6}")) return true;
    else if (phoneNo.match("\\d{5}\\d{6}")) return true;
    //return false if nothing matches the input
    else return false;
}*/

$("#tab-referenceInfo").click(function () {
        var params = new window.URLSearchParams(window.location.search);
        $.ajax({
        url: "/customerloanprofile/referenceinfo/view?id="+params.get('account'),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            if(data.referenceInfo.length>0){
                $('#referenceinfo_tBody').replaceWith("<tbody id=\"referenceinfo_tBody\" >\n" +
                    "\t\t\t\t\t\t\t</tbody>");

                for(var i=0;i<data.referenceInfo.length;i++){
                    if (data.referenceInfo[i]!=null){
                        var str="<tr>"+

                            "<td style=\"display:none;\" id=\"refId\">" + data.referenceInfo[i].id + "</td>" +
                            "<td>" +( data.referenceInfo[i].accountNo==null?'':data.referenceInfo[i].accountNo )+ "</td>" +
                            "<td>" + (data.referenceInfo[i].name==null?'':data.referenceInfo[i].name ) + "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].occupation==null?'':data.referenceInfo[i].occupation )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].companyName==null?'':data.referenceInfo[i].companyName )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].designation==null?'':data.referenceInfo[i].designation )+ "</td>" +
                            "<td >" + (data.referenceInfo[i].phoneNo==null?'':data.referenceInfo[i].phoneNo ) + "</td>" +
                            "<td>"  +( data.referenceInfo[i].homeAddress==null?'':data.referenceInfo[i].homeAddress  )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].officeAddress==null?'':data.referenceInfo[i].officeAddress )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].permanentAddress==null?'':data.referenceInfo[i].permanentAddress )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].dob==null?'':data.referenceInfo[i].dob )+ "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].nidOrPassport==null?'':data.referenceInfo[i].nidOrPassport )+ "</td>" +
                            "<td>" + (data.referenceInfo[i].relationReference ==null?'':data.referenceInfo[i].relationReference ) + "</td>" +
                            "<td style=\"display:none;\">" +( data.referenceInfo[i].facilityWithUcbl==null?'':data.referenceInfo[i].facilityWithUcbl )+ "</td>" +
                            "<td>" + (data.referenceInfo[i].status==null?'':data.referenceInfo[i].status ) + "</td>" +
                            "<td>"+(data.referenceInfo[i].status == 'PENDING' ? '': '<a class=\"btn btn-xs\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>') +
                            "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"viewRow(this)\"> <i class=\"fa fa-eye\"></i>view</a></td>" +
                            "</tr>";
                        $('#referenceinfo_tBody').append(str);
                    }
                }
            }

        },
        error: function (data) {
            //alert("Failure!");
        }
    });
})


function isValidData(){
    if($('input[id="referenceName"]').val() == ""){
        alert("Name must not be empty")
        return false;
    }
    return true;
}

function referenceInformationSave() {

        // if (refValidation()==false){
        //     return;
        // }
    var formValue = $('#referenceInfoSaveForm').serializeJSON();
    var loanAccountNo =$('input[name="account"]').val();
    formValue.loanAccountNo=loanAccountNo;
    var jsonReferenceInfo = JSON.stringify(formValue);
        jsonReferenceInfo.loanAccountNo =$('input[name="account"]').val();

    if(isValidData() == false){
        return;
    }

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var decision=0;
    if($("#reference-Id").val()!="")
        decision=1;

    $.ajax({
        url: '/customerloanprofile/referenceinfo/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonReferenceInfo,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);
            $('#modal-reference-info').modal('hide');
            if(data.referenceInfo != null && decision==0){
                var str="<tr>"+
                    "<td style=\"display:none;\" id=\"refId\">" + data.referenceInfo.id + "</td>" +
                    "<td>" +( data.referenceInfo.accountNo==null?'':data.referenceInfo.accountNo )+ "</td>" +
                    "<td>" + (data.referenceInfo.name==null?'':data.referenceInfo.name ) + "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.occupation==null?'':data.referenceInfo.occupation )+ "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.companyName==null?'':data.referenceInfo.companyName )+ "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.designation==null?'':data.referenceInfo.designation )+ "</td>" +
                    "<td >" + (data.referenceInfo.phoneNo==null?'':data.referenceInfo.phoneNo ) + "</td>" +
                    "<td>"  +( data.referenceInfo.homeAddress==null?'':data.referenceInfo.homeAddress  )+ "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.officeAddress==null?'':data.referenceInfo.officeAddress )+ "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.permanentAddress==null?'':data.referenceInfo.permanentAddress )+ "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.dob==null?'':data.referenceInfo.dob )+ "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.nidOrPassport==null?'':data.referenceInfo.nidOrPassport )+ "</td>" +
                    "<td>" + (data.referenceInfo.relationReference ==null?'':data.referenceInfo.relationReference ) + "</td>" +
                    "<td style=\"display:none;\">" +( data.referenceInfo.facilityWithUcbl==null?'':data.referenceInfo.facilityWithUcbl )+ "</td>" +
                    "<td>" + (data.referenceInfo.status==null?'':data.referenceInfo.status ) + "</td>" +
                    "<td>"+(data.referenceInfo.status == 'PENDING' ? '': '<a class=\"btn btn-xs\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>') +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"viewRow(this)\"> <i class=\"fa fa-trash\"></i>view</a></td>" +
                    "</tr>";
                $('#referenceinfo_tBody').append(str);

            }

            // $(refrencenode).closest("tr").remove();
            // clearData();
            // if (data.referenceInfo != null && decision==0) {
            // }
            else if (data.referenceInfo != null && decisiondecision==1){

                globalRow.cells[0].innerHTML=data.referenceInfo.id;
                globalRow.cells[1].innerHTML=data.referenceInfo.accountNo;
                globalRow.cells[2].innerHTML=data.referenceInfo.name;
                globalRow.cells[3].innerHTML=data.referenceInfo.occupation;
                globalRow.cells[4].innerHTML=data.referenceInfo.companyName;
                globalRow.cells[5].innerHTML=data.referenceInfo.designation;
                globalRow.cells[6].innerHTML=data.referenceInfo.phoneNo;
                globalRow.cells[7].innerHTML=data.referenceInfo.homeAddress;
                globalRow.cells[8].innerHTML=data.referenceInfo.officeAddress;
                globalRow.cells[9].innerHTML=data.referenceInfo.permanentAddress;
                globalRow.cells[10].innerHTML=data.referenceInfo.dob;
                globalRow.cells[11].innerHTML=data.referenceInfo.nidOrPassport;
                globalRow.cells[12].innerHTML=data.referenceInfo.relationReference;
                globalRow.cells[13].innerHTML=data.referenceInfo.facilityWithUcbl;
                globalRow.cells[14].innerHTML=data.referenceInfo.status;
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


function editRow(node){

    var row=node.parentNode.parentNode;
    globalRow=row;
    refrencenode=node;
    $("#reference-Id").val(row.cells[0].innerHTML);
    $("#accNo").val(row.cells[1].innerHTML);
    $("#referenceName").val(row.cells[2].innerHTML);
    $("#referenceoccupation").val(row.cells[3].innerHTML);
    $("#referencecompanyName").val(row.cells[4].innerHTML);
    $("#referenceDesignation").val(row.cells[5].innerHTML);
    $("#referencePhone").val(row.cells[6].innerHTML);
    $("#referenceHomeAddress").val(row.cells[7].innerHTML);
    $("#referenceOfficeAddress").val(row.cells[8].innerHTML);
    $("#referencePermanentAddress").val(row.cells[9].innerHTML);
    $("#refdob").val(row.cells[10].innerHTML);
    $("#refNid").val(row.cells[11].innerHTML);
    $("#relReference").val(row.cells[12].innerHTML);
    $("#reffacilityWithUcbl").val(row.cells[13].innerHTML);

    $('#modal-reference-info').modal('show');
}

function viewRow(node) {
    var row=node.parentNode.parentNode;
    $("#reference-Id").val(row.cells[0].innerHTML);
    $("#accNo").val(row.cells[1].innerHTML);
    $("#referenceName").val(row.cells[2].innerHTML);
    $("#referenceoccupation").val(row.cells[3].innerHTML);
    $("#referencecompanyName").val(row.cells[4].innerHTML);
    $("#referenceDesignation").val(row.cells[5].innerHTML);
    $("#referencePhone").val(row.cells[6].innerHTML);
    $("#referenceHomeAddress").val(row.cells[7].innerHTML);
    $("#referenceOfficeAddress").val(row.cells[8].innerHTML);
    $("#referencePermanentAddress").val(row.cells[9].innerHTML);
    $("#refdob").val(row.cells[10].innerHTML);
    $("#refNid").val(row.cells[11].innerHTML);
    $("#relReference").val(row.cells[12].innerHTML);

    $("#modal-reference-info").appendTo("body");
    $("#referenceInfoSaveForm").find(':input').attr("disabled", "disabled");
    $("#ref-cancel-btn").removeAttr("disabled");
    $("#ref-info-save-buuton").hide();
    $('#modal-reference-info').modal({ show: true, backdrop: false, keyboard: false });


}


function enableRefFormAndClearFormData() {
    $("#referenceInfoSaveForm").find(':input').attr("disabled", false);
    $("#ref-info-save-buton").show();


    $("#reference-Id").val('');
    $("#accNo").val('');
    $("#referenceName").val('');
    $("#referenceoccupation").val('');
    $("#referencecompanyName").val('');
    $("#referenceDesignation").val('');
    $("#referencePhone").val('');
    $("#referenceHomeAddress").val('');
    $("#referenceOfficeAddress").val('');
    $("#referencePermanentAddress").val('');
    $("#refdob").val('');
    $("#refNid").val('');
    $("#relReference").val('');
}