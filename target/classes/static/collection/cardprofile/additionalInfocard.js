
var globalRow

function isValidCardType(){
    if($('#card_card_type').val() == "" || $('#card_card_type').val().toLowerCase() !='basic' ){
        alert("Card Type must be Basic and  Not Empty");
        return false;
    }
    return true;
}
function additionalInfoCardSave() {
    if(isValidCardType()==false){
        return
    }
    var formValue = $('#additional-info-card-form').serializeJSON();
    var searchParams = new URLSearchParams(window.location.search);
         if(searchParams.has('custid')){
             var param = searchParams.get('custid');
         }

        formValue.customerId=param;
        formValue.contractId=$('#card_contract_id').val();
        formValue.clientId=$('#card_client_id').val();
        formValue.cardType=$('#card_card_type').val();
    var additionalInfo = JSON.stringify(formValue);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/collection/card/additionalInfo/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: additionalInfo,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);
            alert("data save sucsessfully");
            var str = "<tr>" +
                "<td>" +(data.occupation==null?'':data.occupation)   + "</td>" +
                "<td>" +(data.orgName==null?'':data.orgName)   + "</td>" +
                "<td>" +(data.contactNo==null?'':data.contactNo)   + "</td>" +
                "<td>" +(data.homeAddress ==null?'':data.homeAddress )  + "</td>" +
                "<td>" +( data.officeAddress==null?'': data.officeAddress)  + "</td>" +
                "<td>" +(data.permanentAddress ==null?'':  data.permanentAddress )+ "</td>" +
                "<td>" + (data.status==null?'':data.status) + "</td>" +
                "<td style=\"display:none;\">" + (data.email==null ?'':data.email)+ "</td>" +
                "<td style=\"display:none;\">" + (data.spouseMblNo==null ?'':data.spouseMblNo)+ "</td>" +
                "<td style=\"display:none;\">" + (data.spouseAcNo==null ?'':data.spouseAcNo)+ "</td>" +
                "<td style=\"display:none;\">" + (data.spouseOccupation==null ?'':data.spouseOccupation)+ "</td>" +
                "<td style=\"display:none;\">" + (data.spouseFatherName==null ?'':data.spouseFatherName)+ "</td>" +
                "<td style=\"display:none;\">" + (data.spouseMotherName==null ?'':data.spouseMotherName)+ "</td>" +
                "<td style=\"display:none;\">" + (data.remarks==null ?'':data.remarks)+ "</td>" +
                "<td>" +
                "<a class=\"btn btn-info\" onclick=\"viewCardAdditionalInfoRow(this)\"><i class=\"fa fa-edit\"></i>View</a>\n" +
                "</td>" +
                "</tr>";
            $('#modal-additional-info-card').modal('hide');
            $('#additionalinfo_card_tBody').prepend(str);
            latestUpdatedAdditionalInfo(data);
            clearCardAdditionalInfoForm();
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving  Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

function latestUpdatedAdditionalInfo(data){
    $("#addiLaOccupation").text(data.occupation);
    $("#addiLaOrgName").text(data.orgName);
    $("#addiLaContact").text(data.contactNo);
    $("#addiLaEmail").text(data.email);
    $("#addiLaSpousembl").text(data.spouseMblNo);
    $("#addiLaSpouseOccu").text(data.spouseOccupation);
    $("#addiLaSpouseAcNo").text(data.spouseAcNo);
    $("#addiLaSpousefaName").text(data.spouseFatherName);
    $("#addiLaSpousemoName").text(data.spouseMotherName);
    $("#addiLaHomeAddress").text(data.homeAddress);
    $("#addiLaOfficeAddress").text(data.officeAddress);
    $("#addiLaPermanentAddress").text(data.permanentAddress);
    $("#addiLaRemarks").text(data.remarks);
}

$("#tab-additional-info-card").on('click',function(){
var dt={
    clientId:$('#card_client_id').val(),
    contractId:$('#card_contract_id').val()
}

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/collection/card/additionalInfo/list',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: dt,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            latestUpdatedAdditionalInfo(data[0]);
            for (var i=0;i<data.length;i++){
                if (data[i]!=null){
                    var str = "<tr>" +
                        "<td>" +( data[i].occupation==null ?'':data[i].occupation )+ "</td>" +
                        "<td>" + (data[i].orgName==null ?'':data[i].orgName) + "</td>" +
                        "<td>" + (data[i].contactNo==null ?'':data[i].contactNo) + "</td>" +
                        "<td>" + (data[i].homeAddress ==null ?'':data[i].homeAddress)+ "</td>" +
                        "<td>" + (data[i].officeAddress==null ?'':data[i].officeAddress) + "</td>" +
                        "<td>" + (data[i].permanentAddress==null ?'':data[i].permanentAddress) + "</td>" +
                        "<td>" + (data[i].status==null ?'':data[i].status) + "</td>" +
                        "<td style=\"display:none;\">" + (data[i].email==null ?'':data[i].email)+ "</td>" +
                        "<td style=\"display:none;\">" + (data[i].spouseMblNo==null ?'':data[i].spouseMblNo)+ "</td>" +
                        "<td style=\"display:none;\">" + (data[i].spouseAcNo==null ?'':data[i].spouseAcNo)+ "</td>" +
                        "<td style=\"display:none;\">" + (data[i].spouseOccupation==null ?'':data[i].spouseOccupation)+ "</td>" +
                        "<td style=\"display:none;\">" + (data[i].spouseFatherName==null ?'':data[i].spouseFatherName)+ "</td>" +
                        "<td style=\"display:none;\">" + (data[i].spouseMotherName==null ?'':data[i].spouseMotherName)+ "</td>" +
                        "<td style=\"display:none;\">" + (data[i].remarks==null ?'':data[i].remarks)+ "</td>" +
                        "<td>" +
                        "<a class=\"btn btn-info\" onclick=\"viewCardAdditionalInfoRow(this)\"><i class=\"fa fa-edit\"></i>View</a>\n" +
                        "</td>" +
                        "</tr>";
                    $('#additionalinfo_card_tBody').append(str);
                }
            }

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving  Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });

})





function viewCardAdditionalInfoRow(node){

    var row=node.parentNode.parentNode;
    console.log(row);
    globalRow=row;
    $("#addiOccupation").text(row.cells[0].innerHTML);
    $("#addiOrgName").text(row.cells[1].innerHTML);
    $("#addiContact").text(row.cells[2].innerHTML);
    $("#addiHomeAddress").text(row.cells[3].innerHTML);
    $("#addiOfficeAddress").text(row.cells[4].innerHTML);
    $("#addiPermanentAddress").text(row.cells[5].innerHTML);

    $("#addiEmail").text(row.cells[7].innerHTML);
    $("#addiSpousembl").text(row.cells[8].innerHTML);
    $("#addiSpouseAcNo").text(row.cells[9].innerHTML);
    $("#addiSpouseOccu").text(row.cells[10].innerHTML);
    $("#addiSpousefaName").text(row.cells[11].innerHTML);
    $("#addiSpousemoName").text(row.cells[12].innerHTML);

    $("#addiRemarks").text(row.cells[13].innerHTML);
    $('#modal-additional-card-info-view').modal('toggle');

}


function clearCardAdditionalInfoForm(){
    $('#additional-info-card-form').trigger("reset");
}