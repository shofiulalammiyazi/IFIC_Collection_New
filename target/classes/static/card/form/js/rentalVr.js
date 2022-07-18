function addRentalVrInfo() {
    
    if (rentalVRValidation()==false){
        return;
    } 

    var formValues = $('#rentalVrForm').serializeJSON();
    var rentalVrFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/branch-verification/rental-verification/save-rental-income-vr',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: rentalVrFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //alert(data.message);
            $('#rentalVrInfoFormDiv').hide();
            $('#rentalIncomeVerificationView').show();

            $('#rentId').val(data.rentalVrInfo.rentId);
            $('#rentalSiteVisit1').text(data.rentalVrInfo.rentalSiteVisit);
            $('#rentalVisitCondBy1').text(data.rentalVrInfo.rentalVisitCondBy);
            $('#rentalVisitDate1').text(data.rentalVrInfo.rentalVisitDate);
            $('#rentalOfficeSetup1').text(data.rentalVrInfo.rentalOfficeSetup);
            $('#rentalOverallComments1').text(data.rentalVrInfo.rentalOverallComments);

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Rental Income Verification!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#rentalIncomeVRCommitButton').click(function(event) {
    event.preventDefault();
    addRentalVrInfo();
});


$('#rentalIncomeVerificationViewEditBtn').click(function(event) {
    event.preventDefault();
    $('#rentalVrInfoFormDiv').show();
    $('#rentalIncomeVerificationView').hide();
});

function rentalVRValidation() {
    if ($("#rentalSiteVisit").val()==""){
        alert("Site Visit must be filled out!");
        $("#rentalSiteVisit").focus();
        return false;
    }

    if ($("#rentalVisitCondBy").val()==""){
        alert("Visitor Name must be filled out!");
        $("#rentalVisitCondBy").focus();
        return false;
    }

    if ($("#rentalVisitDate").val()==""){
        alert("Visit Date must be filled out!");
        $("#rentalVisitDate").focus();
        return false;
    }

    if ($("#rentalOverallComments").val()==""){
        alert("Overall Comments must be filled out!");
        $("#rentalOverallComments").focus();
        return false;
    }
}


$("#tab-rentalincomeVR").click(function(){
    ////alert("preview contact info click");
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };

    $.ajax({
        url: '/card/branchvr/rentalvr',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            ////alert("Success");
            $('#customerId').val(data.rentalVrInfo.customerId);
            $('#rentId').val(data.rentalVrInfo.rentId);
            $('#rentalSiteVisit').val(data.rentalVrInfo.rentalSiteVisit);
            $('#rentalVisitCondBy').val(data.rentalVrInfo.rentalVisitCondBy);
            $('#rentalVisitDate').val(data.rentalVrInfo.rentalVisitDate);
            $('#rentalOfficeSetup').val(data.rentalVrInfo.rentalOfficeSetup);
            $('#rentalOverallComments').val(data.rentalVrInfo.rentalOverallComments)
        },
        error: function () {
            ////alert("Error");
        }
    });

});





