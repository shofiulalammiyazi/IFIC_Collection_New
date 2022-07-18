$(document).ready(function () {
    $('#additionalSaveForm input[name="_csrf"]').val(csrfToken);
    $('#additionalSaveForm input[name="customerId"]').val(customerId);
    $('#additionalSaveForm input[name="accountNo"]').val(accountNo);
})


$('add-button').on('click',function () {
    var formdatalist = $('#additionalSaveForm');
    formdatalist.find('input[name="id"]').val('');
    formdatalist.find('input[name="occupation"]').val('');
    formdatalist.find('input[name="orgName"]').val('');
    formdatalist.find('input[name="contactNo"]').val('');
    formdatalist.find('input[name="spouseMblNo"]').val('');
    formdatalist.find('input[name="email"]').val('');
    formdatalist.find('input[name="spouseOccupation"]').val('');
    formdatalist.find('input[name="spouseAcNo"]').val('');
    formdatalist.find('input[name="spouseFatherName"]').val('');
    formdatalist.find('input[name="spouseMotherName"]').val('');
    formdatalist.find('input[name="homeAddress"]').val('');
    formdatalist.find('input[name="officeAddress"]').val('');
    formdatalist.find('input[name="permanentAddress"]').val('');
    formdatalist.find('input[name="remarks"]').val('');
})

function additionalList() {
    $.getJSON('/collection/additionalInfo/list?accountNo='+accountNo, function (json) {
        var tr = [];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + (json[i].occupation == null ? '' : json[i].occupation) + '</td>');
            tr.push('<td>' + (json[i].orgName == null ? '' : json[i].orgName) + '</td>');
            tr.push('<td>' + (json[i].contactNo == null ? '' : json[i].contactNo) + '</td>');
            tr.push('<td>' + (json[i].homeAddress == null ? '' : json[i].homeAddress) + '</td>');
            tr.push('<td>' + (json[i].officeAddress == null ? '' : json[i].officeAddress) + '</td>');
            tr.push('<td>' + (json[i].permanentAddress == null ? '' : json[i].permanentAddress) + '</td>');
            tr.push('<td>' + (json[i].status == null ? '' : json[i].status) + '</td>');
            tr.push('<td>' +
                // (json[i].status == 'PENDING' ? '': '<button onclick="editAdditionalInfo(' + json[i].id + ')" class=\'addiEdit btn btn-primary btn-xs\'>Edit</button> ') +
                '<button onclick="viewAdditionalInfo(' + json[i].id + ')" class=\'btn btn-success btn-xs\'><i fa fa-eye></i>View</button>' +
                '</td>');

            // tr.push('<td><button onclick="editAdditionalInfo(' + json[i].id + ')" class=\'btn btn-success btn-xs\'><i fa fa-eye></i>View</button></td>');
            tr.push('</tr>');
        }

        viewLatestInfo(accountNo);

        $('#additionalinfo_tBody').append($(tr.join('')));
    })
}

additionalList(accountNo);

function additionalInfoSave(e) {
    e.preventDefault();
    var formValue = $("#additionalSaveForm").serializeJSON();
    // var loanAccountNo = $('input[name="accountNo"]').val();
    // formValue.accountNo = loanAccountNo;
    // var jsonAdditionalInfo = JSON.stringify(formValue);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var formdatalist = $('#additionalSaveForm');

    var occupation = formdatalist.find('input[name="occupation"]').val();
    var orgName = formdatalist.find('input[name="orgName"]').val();
    var contactNo = formdatalist.find('input[name="contactNo"]').val();
    var spouseMblNo = formdatalist.find('input[name="spouseMblNo"]').val();
    var email = formdatalist.find('input[name="email"]').val();
    var spouseOccupation =  formdatalist.find('input[name="spouseOccupation"]').val();
    var homeAddress =  formdatalist.find('input[name="homeAddress"]').val();
    var officeAddress =  formdatalist.find('input[name="officeAddress"]').val();
    var permanentAddress =  formdatalist.find('input[name="permanentAddress"]').val();
    var remarks =  formdatalist.find('input[name="remarks"]').val();

    if(occupation == "" && orgName == "" && contactNo == "" &&
       spouseMblNo == "" && email == "" && spouseOccupation == "" &&
        homeAddress == "" && officeAddress == "" && permanentAddress == "" &&
    permanentAddress == "" && remarks == ""
    ){
        alert("Must fill up at least one field");
        return;
    }

    $.ajax({
        type:"post",
        url:"/collection/additionalInfo/save",
        // data: jsonAdditionalInfo,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data:formValue,
        success:function (data) {
            swal("Success!", "Successfully Saved!", "success");
            $('id,occupation,orgName,contactNo,spouseMblNo,email,spouseOccupation,spouseAcNo,spouseFatherName,spouseMotherName,homeAddress,officeAddress,permanentAddress,remarks').val('');
            $('#modal-additional-info').modal('toggle')
            $('#additionalinfo_tBody').html("");
            additionalList(customerId);
        }
    }).done(function(){
        // Clear Form
        $('#additionalSaveForm')[0].reset();

    });

    return false;
}


// function editAdditionalInfo(id){
//     $('#additionalInfo input[name="_csrf"]').val(csrfToken);
//     var url = '/collection/additionalInfo/edit?id='+id;
//     $.getJSON(url, function(data){
//         var formdatalist = $('#additionalSaveForm');
//         formdatalist.find('input[name="id"]').val(data.id);
//         formdatalist.find('input[name="occupation"]').val(data.occupation);
//         formdatalist.find('input[name="orgName"]').val(data.orgName);
//         formdatalist.find('input[name="contactNo"]').val(data.contactNo);
//         formdatalist.find('input[name="spouseMblNo"]').val(data.spouseMblNo);
//         formdatalist.find('input[name="email"]').val(data.email);
//         formdatalist.find('input[name="homeAddress"]').val(data.homeAddress);
//         formdatalist.find('input[name="officeAddress"]').val(data.officeAddress);
//         formdatalist.find('input[name="permanentAddress"]').val(data.permanentAddress);
//         formdatalist.find('input[name="remarks"]').val(data.remarks);
//         $('#modal-additional-info').modal('toggle');
//     });
// }

function viewAdditionalInfo(id) {
    $('#additionalInfo input[name="_csrf"]').val(csrfToken);
    var url = '/collection/additionalInfo/edit?id='+id;
    $.getJSON(url, function(data){
        formData = data;
        $('#addiOccupation').text(data.occupation);
        $('#addiOrgName').text(data.orgName);
        $('#addiContact').text(data.contactNo);
        $('#addiEmail').text(data.email);
        $('#addiSpousembl').text(data.spouseMblNo);
        $('#addiSpouseOccu').text(data.spouseOccupation);
        $('#addiSpouseAcNo').text(data.spouseAcNo);
        $('#addiSpousefaName').text(data.spouseFatherName);
        $('#addiSpousemoName').text(data.spouseMotherName);
        $('#addiHomeAddress').text(data.homeAddress);
        $('#addiOfficeAddress').text(data.officeAddress);
        $('#addiPermanentAddress').text(data.permanentAddress);
        $('#addiRemarks').text(data.remarks);
        $('#modal-additional-info-view').modal('toggle');
    });

}


function viewLatestInfo(accountNo) {
    $('#additionalInfo input[name="_csrf"]').val(csrfToken);
    var url = '/collection/additionalInfo/latest?accountNo='+accountNo;
    $.getJSON(url, function(data){
        formData = data;
        $('#addiLaOccupation').text(data.occupation);
        $('#addiLaOrgName').text(data.orgName);
        $('#addiLaContact').text(data.contactNo);
        $('#addiLaEmail').text(data.email);
        $('#addiLaSpousembl').text(data.spouseMblNo);
        $('#addiLaSpouseOccu').text(data.spouseOccupation);
        $('#addiLaSpouseAcNo').text(data.spouseAcNo);
        $('#addiLaSpousefaName').text(data.spouseFatherName);
        $('#addiLaSpousemoName').text(data.spouseMotherName);
        $('#addiLaHomeAddress').text(data.homeAddress);
        $('#addiLaOfficeAddress').text(data.officeAddress);
        $('#addiLaPermanentAddress').text(data.permanentAddress);
        $('#addiLaRemarks').text(data.remarks);
        // $('#modal-additional-info-view').modal('toggle');
        update_additionalInfo.setLatestAdditionalInfo(data);
        profile_information.getLatestAdditionalInfo(data);
    });

}