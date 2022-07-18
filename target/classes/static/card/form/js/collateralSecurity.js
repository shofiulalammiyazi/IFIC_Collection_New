function addCollateralSecurityInfo() {
    if (contactValidate()==false){
        return;
    }
    var formValues = $('#collateralSecForm').serializeJSON();
    var csFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/info/collaterial-security/save-collateral-security',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: csFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#collateralSecFormDiv').hide();
            $('#collSecViewDiv').show();
            $('#colSecId').val(data.collateralSecurity.colSecId);
            $('#VbankName').text(data.collateralSecurity.bankName);
            $('#VbranchName').text(data.collateralSecurity.branchName);
            $('#VaccTypeId').text(data.collateralSecurity.accType);
            $('#VaccName').text(data.collateralSecurity.accName);
            $('#VaccNo').text(data.collateralSecurity.accNo);
            $('#VsecAmount').text(data.collateralSecurity.secAmount);
            $('#VmaturityDate').text(data.collateralSecurity.maturityDate);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Contact Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#collCommitBtn').click(function(event) {
    event.preventDefault();
    addCollateralSecurityInfo();
});

$("#csEditButton").click(function(){
    $("#collateralSecFormDiv").show();
    $('#collSecViewDiv').hide();
    var dt={
    		customerId : $("#personalCustomerIdEdit").val()
    };
    $.ajax({
        url: '/card/info/collaterial-security/view',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            $('#customerId').val(data.collateralSecurity.customerId);
            $('#colSecId').val(data.collateralSecurity.colSecId);
            $('#bankName').val(data.collateralSecurity.bankName);
            $('#branchName').val(data.collateralSecurity.branchName);
            $('#accType').val(data.collateralSecurity.accType);
            $('#accName').val(data.collateralSecurity.accName);
            $('#accNo').val(data.collateralSecurity.accNo);
            $('#secAmount').val(data.collateralSecurity.secAmount);
            $('#maturityDate').val(data.collateralSecurity.maturityDate);
        },
        error: function (err) {
            ////alert(err);
        }
    });
});

$("#tab_collateral_security").click(function () {

    var dt={
        customerId : $(".customer-id").val()
    };
    console.log(dt);
        $.ajax({
            url: '/card/info/collaterial-security/view',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
                $('#customerId').val(data.collateralSecurity.customerId);
                $('#colSecId').val(data.collateralSecurity.colSecId);
                $('#colBankName').val(data.collateralSecurity.bankName);
                $('#colBranchName').val(data.collateralSecurity.branchName);
                $('#colAccType').val(data.collateralSecurity.accType);
                $('#colAccName').val(data.collateralSecurity.accName);
                $('#colAccNo').val(data.collateralSecurity.accNo);
                $('#colSecAmount').val(data.collateralSecurity.secAmount);
                $('#colMaturityDate').val(data.collateralSecurity.maturityDate);
            },
            error: function (err) {
                ////alert(err);
            }
        });
})