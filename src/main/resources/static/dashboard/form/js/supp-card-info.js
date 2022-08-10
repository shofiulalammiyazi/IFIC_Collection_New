function suppCardInfo() {
    if (suppCardValidation()==false){
        return;
    }
    var formValues = $('#suppCardForm').serializeJSON();
    var cardPersonalInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/customer-info/supp-card-info/save',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: cardPersonalInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
        	alert('Supplimentary card added successfully!');
            $('#suppCardInfoFormDiv').hide();
            $('#suppCardViewDiv').show();
            $('#suppId').val(data.suppCardInfo.suppId);
            $('#accNo').text(data.suppCardInfo.suppbankAcc);
            $('#code').text(data.suppCardInfo.suppcustomerCode);
            $('#appliName').text(data.suppCardInfo.appName);
            $('#sapproveAmount').text(data.suppCardInfo.approveAmount);
            $('#sappCardNo').text(data.suppCardInfo.appCardNo);
            $('#sspendingLimit').text(data.suppCardInfo.spendingLimit);
            $('#sapplicantsFatherName').text(data.suppCardInfo.applicantsFatherName);
            $('#sapplicantsMotherName').text(data.suppCardInfo.applicantsMotherName);
            $('#srelationPrinApplicants').text(data.suppCardInfo.relationPrinApplicants);
            
            $('#fullName').text(data.suppCardInfo.suppfullName1);
            $('#nickName').text(data.suppCardInfo.suppnickName1);
            $('#suppdob').text(data.suppCardInfo.suppsuppCardDOB);
            $('#suppnid').text(data.suppCardInfo.suppnid1);
            $('#eduLevel').text(data.suppCardInfo.suppeducationLevel1);
            $('#suppgenderv').text(data.suppCardInfo.suppgender);
            $('#tin').text(data.suppCardInfo.supptinNo);
            $('#marit').text(data.suppCardInfo.suppmaritalStatus);
            $('#childNo').text(data.suppCardInfo.suppchildrenNo);
            $('#passNo').text(data.suppCardInfo.supppassportNo);
            $('#expDate').text(data.suppCardInfo.supppasExpDate);
            $('#carOwn').text(data.suppCardInfo.suppcarOwnership);
            $('#license').text(data.suppCardInfo.suppdrivingLicenseNo);
            $('#dlDate').text(data.suppCardInfo.suppCardExpDate);
            $('#residence').text(data.suppCardInfo.suppresidentialStatus);
            $('#address').text(data.suppCardInfo.suppAdrs1 + data.suppCardInfo.suppAdrs2);
            $('#postCode').text(data.suppCardInfo.suppPostCode);
            $('#country').text(data.suppCardInfo.suppCntry);
            $('#city').text(data.suppCardInfo.suppCity);
            $('#years').text(data.suppCardInfo.suppprlivingPeriod1);
            $('#same').text(data.suppCardInfo.suppprlivingPeriodMonth1);
            $('#peradrs1').text(data.suppCardInfo.supppmfn1);
            $('#peradrs2').text(data.suppCardInfo.supppmhpn1);
            $('#postCode1').text(data.suppCardInfo.supppmpc1);
            $('#country1').text(data.suppCardInfo.supppmroad1);
            $('#city1').text(data.suppCardInfo.supppmblock1);
            $('#years1').text(data.suppCardInfo.supppmlivingPeriod1);
            $('#months1').text(data.suppCardInfo.supppmlivingPeriodMonth1);
            
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Supp Card Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#suppCardCommitButton').click(function(event) {
    event.preventDefault();
    suppCardInfo();
});

$('#suppInfoEditButton').click(function(event) {
    event.preventDefault();
    $('#suppCardInfoFormDiv').show();
    $('#suppCardViewDiv').hide();
});



$("#tab-suppCard").click(function(){

 var supp={
        name : "Unisoft",
        company : "Unisoft",
        id : $("#personalCustomerIdEdit").val()
    };

    $.ajax({
        type : "GET",
        url : "/card/customer-info/supp-card-info/view",
        data : supp,
        success: function (data) {
            $('#customerId').val(data.suppCardInfo.customerId);
            $('#suppId').val(data.suppCardInfo.suppId);
            
            $('#appName').val(data.suppCardInfo.appName);
            $('#approveAmount').val(data.suppCardInfo.approveAmount);
            $('#appCardNo').val(data.suppCardInfo.appCardNo);
            $('#spendingLimit').val(data.suppCardInfo.spendingLimit);
            $('#applicantsFatherName').val(data.suppCardInfo.applicantsFatherName);
            $('#applicantsMotherName').val(data.suppCardInfo.applicantsMotherName);
            $('#relationPrinApplicants').val(data.suppCardInfo.relationPrinApplicants);
            
            $('#suppbankAcc').val(data.suppCardInfo.suppbankAcc);
            $('#suppcustomerCode').val(data.suppCardInfo.suppcustomerCode);
            $('#suppfullName1').val(data.suppCardInfo.suppfullName1);
            $('#suppnickName1').val(data.suppCardInfo.suppnickName1);
            $('#suppCardDOB').val(data.suppCardInfo.suppsuppCardDOB);
            $('#suppnid1').val(data.suppCardInfo.suppnid1);
            $('#suppeducationLevel1').val(data.suppCardInfo.suppeducationLevel1);
            $('#suppgender').val(data.suppCardInfo.suppgender);
            $('#supptinNo').val(data.suppCardInfo.supptinNo);
            $('#suppmaritalStatus').val(data.suppCardInfo.suppmaritalStatus);
            $('#suppchildrenNo').val(data.suppCardInfo.suppchildrenNo);
            $('#supppassportNo').val(data.suppCardInfo.supppassportNo);
            $('#supppasExpDate').val(data.suppCardInfo.supppasExpDate);
            $('#suppcarOwnership').val(data.suppCardInfo.suppcarOwnership);
            $('#suppdrivingLicenseNo').val(data.suppCardInfo.suppdrivingLicenseNo);
            $('#suppCardExpDate').val(data.suppCardInfo.suppCardExpDate);
            $('#suppresidentialStatus').val(data.suppCardInfo.suppresidentialStatus);
            $('#suppAdrs1').val(data.suppCardInfo.suppAdrs1);
            $('#suppAdrs2').val(data.suppCardInfo.suppAdrs2);
            $('#suppPostCode').val(data.suppCardInfo.suppPostCode);
            $('#suppCntry').val(data.suppCardInfo.suppCntry);
            $('#suppCity').val(data.suppCardInfo.suppCity);
            $('#suppprlivingPeriod1').val(data.suppCardInfo.suppprlivingPeriod1);
            $('#suppprlivingPeriodMonth1').val(data.suppCardInfo.suppprlivingPeriodMonth1);
            $('#supppmfn1').val(data.suppCardInfo.supppmfn1);
            $('#supppmhpn1').val(data.suppCardInfo.supppmhpn1);
            $('#supppmpc1').val(data.suppCardInfo.supppmpc1);
            $('#supppmroad1').val(data.suppCardInfo.supppmroad1);
            $('#supppmblock1').val(data.suppCardInfo.supppmblock1);
            $('#supppmlivingPeriod1').val(data.suppCardInfo.supppmlivingPeriod1);
            $('#supppmlivingPeriodMonth1').val(data.suppCardInfo.supppmlivingPeriodMonth1);
            
           
        },
        error: function () {
            ////alert("Error");
        }
    });
});


$(document).ready(function () {
$('#checkSuppCard').on('click',function () {

    if(this.checked){
        $('#supppmfn1').val($('#suppAdrs1').val());
        $('#supppmhpn1').val($('#suppAdrs2').val());
        $('#supppmpc1').val($('#suppPostCode').val());
        $('#supppmroad1').val($('#suppCntry').val());
        $('#supppmblock1').val($('#suppCity').val());
        $('#supppmlivingPeriod1').val($('#suppprlivingPeriod1').val());
        $('#supppmlivingPeriodMonth1').val($('#suppprlivingPeriodMonth1').val());
    }
    else {
        $('#supppmfn1').val("");
        $('#supppmhpn1').val("");
        $('#supppmpc1').val("");
        $('#supppmroad1').val("");
        $('#supppmblock1').val("");
        $('#supppmlivingPeriod1').val("");
        $('#supppmlivingPeriodMonth1').val("");
    }

})
})



function suppCardValidation() {
    if ($("#appName").val()==""){
        alert("Applicant Name Must be filled out!");
        $("#appName").focus();
        return false;
    }

    if ($("#approveAmount").val()==""){
        alert("Approve Amount Must be filled out!");
        $("#approveAmount").focus();
        return false;
    }

    if ($("#appCardNo").val()==""){
        alert("Applicant Card No Must be filled out!");
        $("#appCardNo").focus();
        return false;
    }

    if ($("#spendingLimit").val()==""){
        alert("Spending Limit Must be filled out!");
        $("#spendingLimit").focus();
        return false;
    }

    if ($("#applicantsFatherName").val()==""){
        alert("Applicants Father Name Must be filled out!");
        $("#applicantsFatherName").focus();
        return false;
    }

    if ($("#applicantsMotherName").val()==""){
        alert("Applicants Mother Name Must be filled out!");
        $("#applicantsMotherName").focus();
        return false;
    }

    if ($("#relationPrinApplicants").val()==""){
        alert("Relation With Principal Applicants Must be filled out!");
        $("#relationPrinApplicants").focus();
        return false;
    }

    if ($("#suppfullName1").val()==""){
        alert("Full Name Must be filled out!");
        $("#suppfullName1").focus();
        return false;
    }

    if ($("#suppnid1").val()==""){
        alert("National Id Must be filled out!");
        $("#suppnid1").focus();
        return false;
    }

   if ($("#suppgender").val()==""){
        alert("Gender Must be filled out!");
        $("#suppgender").focus();
        return false;
    }

    if ($("#suppCardDOB").val()==""){
        alert("Date Of Birth Must be filled out!");
        $("#suppCardDOB").focus();
        return false;
    }

    if ($("#supptinNo").val()==""){
        alert("TIN Must be filled out!");
        $("#supptinNo").focus();
        return false;
    }

    if ($("#suppmaritalStatus").val()==""){
        alert("Marital Status Must be filled out!");
        $("#suppmaritalStatus").focus();
        return false;
    }

    if ($("#suppAdrs1").val()==""){
        alert("Present Address Must be filled out!");
        $("#suppAdrs1").focus();
        return false;
    }

    if ($("#supppmfn1").val()==""){
        alert("Permanent Address Must be filled out!");
        $("#supppmfn1").focus();
        return false;
    }

    /*if ($("#suppprlivingPeriodMonth1").val()==""){
        alert("Present Living Month Must be filled out!");
        $("#suppprlivingPeriodMonth1").focus();
        return false;
    }

    if ($("#supppmpc1").val()==""){
        alert("Post Code Must be filled out!");
        $("#supppmpc1").focus();
        return false;
    }

    if ($("#supppmroad1").val()==""){
        alert("Country Must be filled out!");
        $("#supppmroad1").focus();
        return false;
    }

    if ($("#supppmblock1").val()==""){
        alert("City Must be filled out!");
        $("#supppmblock1").focus();
        return false;
    }

    if ($("#supppmlivingPeriod1").val()==""){
        alert("Year Must be filled out!");
        $("#supppmlivingPeriod1").focus();
        return false;
    }

    if ($("#supppmlivingPeriodMonth1").val()==""){
        alert("Month Must be filled out!");
        $("#supppmlivingPeriodMonth1").focus();
        return false;
    }

    // number validation

    var x = $("#suppchildrenNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Children No must be valid..");
        $("#suppchildrenNo").focus();
        return false;
    }

    var x = $("#suppnid1").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("National ID must be valid..");
        $("#suppnid1").focus();
        return false;
    }

    var x = $("#supptinNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("TIN must be valid..");
        $("#supptinNo").focus();
        return false;
    }

    var x = $("#supppassportNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Passport No must be valid..");
        $("#supppassportNo").focus();
        return false;
    }


    var x = $("#suppPostCode").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Present Post Code must be valid..");
        $("#suppPostCode").focus();
        return false;
    }

    var x = $("#supppmpc1").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Parmanent Post Code must be valid..");
        $("#supppmpc1").focus();
        return false;
    }*/
}