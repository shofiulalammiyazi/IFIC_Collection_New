function addDoctorVrInfo() {
    
    if (doctorValidation()==0){
        return;
    } 

    var formValues = $('#DoctorVrForm').serializeJSON();
    var DoctorVrFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/card/branch-verification/doctor-verification/save-doctor-vr',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: DoctorVrFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#DoctorVrFormDiv').hide();
            $('#DoctorVrViewDiv').show();

            $('#dvId').val(data.doctorVrInfo.dvId);
            $('#vdocVRSiteVisit').text(data.doctorVrInfo.docVRSiteVisit);
            $('#vdocVRVisitCondBy').text(data.doctorVrInfo.docVRVisitCondBy);
            $('#vdocVisitDate').text(data.doctorVrInfo.docVisitDate);
            $('#vlocOfPractice').text(data.doctorVrInfo.locOfPractice);
            $('#vnewPatient').text(data.doctorVrInfo.newPatient);

            $('#vpracticeFrequency').text(data.doctorVrInfo.practiceFrequency);
            $('#vdurationInCurLoc').text(data.doctorVrInfo.durationInCurLoc);
            $('#vconsultancyFeeNew').text(data.doctorVrInfo.consultancyFeeNew);
            $('#vtotalConsultIncome').text(data.doctorVrInfo.totalConsultIncome);
            $('#vtotalExperience').text(data.doctorVrInfo.totalExperience);

            $('#vOldPatientNo').text(data.doctorVrInfo.oldPatientNo);
            $('#votherIncome').text(data.doctorVrInfo.otherIncome);
            $('#vconcentArea').text(data.doctorVrInfo.concentArea);
            $('#vconsultancyFeeOld').text(data.doctorVrInfo.consultancyFeeOld);
            $('#vtotalPvtIncome').text(data.doctorVrInfo.totalPvtIncome);

            $('#vdocOfficeSetup').text(data.doctorVrInfo.docOfficeSetup);
            $('#vdocOverallComment').text(data.doctorVrInfo.docOverallComment);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Doctor Verification!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#DoctorVrCommitBtn').click(function(event) {
    event.preventDefault();
    addDoctorVrInfo();
});

$('#DoctorVrEditBtn').click(function(event) {
    event.preventDefault();
    $('#DoctorVrFormDiv').show();
    $('#DoctorVrViewDiv').hide();
});



$("#tab-pivod").click(function(){
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    $.ajax({
        url: '/card/branchvr/doctorvr',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {

            $('#dvId').val(data.doctorVrInfo.dvId);
            $('#customerId').val(data.doctorVrInfo.customerId);
            $('#docVRSiteVisit').val(data.doctorVrInfo.docVRSiteVisit);
            $('#docVRVisitCondBy').val(data.doctorVrInfo.docVRVisitCondBy);
            $('#docVisitDate').val(data.doctorVrInfo.docVisitDate);
            $('#locOfPractice').val(data.doctorVrInfo.locOfPractice);
            $('#newPatient').val(data.doctorVrInfo.newPatient);

            $('#practiceFrequency').val(data.doctorVrInfo.practiceFrequency);
            $('#durationInCurLoc').val(data.doctorVrInfo.durationInCurLoc);
            $('#consultancyFeeNew').val(data.doctorVrInfo.consultancyFeeNew);
            $('#totalConsultIncome').val(data.doctorVrInfo.totalConsultIncome);
            $('#totalExperience').val(data.doctorVrInfo.totalExperience);

            $('#oldPatientNo').val(data.doctorVrInfo.oldPatientNo);
            $('#otherIncome').val(data.doctorVrInfo.otherIncome);
            $('#concentArea').val(data.doctorVrInfo.concentArea);
            $('#consultancyFeeOld').val(data.doctorVrInfo.consultancyFeeOld);
            $('#totalPvtIncome').val(data.doctorVrInfo.totalPvtIncome);

            $('#docOfficeSetup').val(data.doctorVrInfo.docOfficeSetup);
            $('#docOverallComment').val(data.doctorVrInfo.docOverallComment);
        },
        error: function () {
            ////alert("Error");
        }
    });
});

function doctorValidation() {
    if ($("#docVRSiteVisit").val()==""){
        alert("Site Visit must be filled out!");
        $("#docVRSiteVisit").focus();
        return false;
    }

    if ($("#docVRVisitCondBy").val()==""){
        alert("Visitor Name must be filled out!");
        $("#docVRVisitCondBy").focus();
        return false;
    }

    if ($("#locOfPractice").val()==""){
        alert("Location must be filled out!");
        $("#locOfPractice").focus();
        return false;
    }

    if ($("#newPatient").val()==""){
        alert("Number of Patient must be filled out!");
        $("#newPatient").focus();
        return false;
    }

    if ($("#practiceFrequency").val()==""){
        alert("Practice Frequency must be filled out!");
        $("#practiceFrequency").focus();
        return false;
    }

    if ($("#durationInCurLoc").val()==""){
        alert("Duration must be filled out!");
        $("#durationInCurLoc").focus();
        return false;
    }

    if ($("#consultancyFeeNew").val()==""){
        alert("Consultancy Fee must be filled out!");
        $("#consultancyFeeNew").focus();
        return false;
    }

    if ($("#totalExperience").val()==""){
        alert("Experience must be filled out!");
        $("#totalExperience").focus();
        return false;
    }

    if ($("#OldPatientNo").val()==""){
        alert("Number of ol patient must be filled out!");
        $("#OldPatientNo").focus();
        return false;
    }

    if ($("#consultancyFeeOld").val()==""){
        alert("Consultancy Fee must be filled out!");
        $("#consultancyFeeOld").focus();
        return false;
    }

    if ($("#docOverallComment").val()==""){
        alert("Comments must be filled out!");
        $("#docOverallComment").focus();
        return false;
    }

    x=$("#newPatient").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#newPatient").focus();
        return;
    }

    x=$("#durationInCurLoc").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#durationInCurLoc").focus();
        return;
    }

    x=$("#consultancyFeeNew").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#consultancyFeeNew").focus();
        return;
    }

    x=$("#totalConsultIncome").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalConsultIncome").focus();
        return;
    }

    x=$("#totalExperience").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalExperience").focus();
        return;
    }

    x=$("#oldPatientNo").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#oldPatientNo").focus();
        return;
    }

    x=$("#otherIncome").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#otherIncome").focus();
        return;
    }

    x=$("#consultancyFeeOld").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#consultancyFeeOld").focus();
        return;
    }

    x=$("#totalPvtIncome").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalPvtIncome").focus();
        return;
    }
}


