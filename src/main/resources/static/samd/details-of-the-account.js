


$("#tab-details-of-the-account").click(function () {
    $.ajax({
        url: "/samd/customer-profile/details-account/view?accountNo="+$('input[name="account"]').val(),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if(data.detailsOfAccount!=null) {
                $("#details_account_update_btn").show();
                $("#details_account_save_btn").hide();

            }else{
                $("#details_account_save_btn").show();
                $("#details_account_update_btn").hide();

            }


            $("#details_account_id").val(data.detailsOfAccount.id);
            $("#name_of_the_company").val(data.detailsOfAccount.nameOfTheCompany);
            $("#address_of_the_company").val(data.detailsOfAccount.addressOfTheCompany);
            $("#construction_legal_status").val(data.detailsOfAccount.constructionLegalLtatus);
            $("#year_of_establishment").val(data.detailsOfAccount.yearOfEstablishment);
            $("#particular_of_cd_account").val(data.detailsOfAccount.particularOfCdAccount);
            $("#account_number").val(data.detailsOfAccount.accountNumber);
            $("#date_of_account_opening").val(data.detailsOfAccount.dateOfAccountOpening);
            $("#borrower_relationship").val(data.detailsOfAccount.borrowerRelationship);
            $("#owner_information").val(data.detailsOfAccount.ownerInformation);
            $("#nature_of_business").val(data.detailsOfAccount.natureOfBusiness);
            $("#nature_of_credit_facility").val(data.detailsOfAccount.natureOfCreditFacility);
            $("#facility_limit").val(data.detailsOfAccount.facilityLimit);
            $("#approval_auth_sanction_no_date").val(data.detailsOfAccount.approvalAuthSanctionNoDate);
            $("#incumbency_loan_disbursed").val(data.detailsOfAccount.incumbencyLoanDisbursed);
            $("#branch_manager_name_no").val(data.detailsOfAccount.branchManagerNameNo);
            $("#date_of_disbursement").val(data.detailsOfAccount.dateOfDisbursement);
            $("#date_of_expiry").val(data.detailsOfAccount.dateOfExpiry);
            $("#bank_other_effort_recovery").val(data.detailsOfAccount.bankOtherEffortRecovery);


        },
        error: function (data) {
            //alert("Failure!");
        }
    });
})

function saveDetailsOfAccount() {
    var formValue = $('#details_of_account_form').serializeJSON();
    var loanAccountNo =$('input[name="account"]').val();
    formValue.loanAccountNo=loanAccountNo;
    var detailsOfAccountInfo = JSON.stringify(formValue);
    detailsOfAccountInfo.loanAccountNo =$('input[name="account"]').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/samd/customer-profile/details-account/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: detailsOfAccountInfo,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#details_account_id").val(data.detailsOfAccount.id);
            $("#details_account_update_btn").show();
            $("#details_account_save_btn").hide();
            alert(data.successMsg);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Details of account Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}


$("#details_account_update_btn").click(function(){
    saveDetailsOfAccount();
});




