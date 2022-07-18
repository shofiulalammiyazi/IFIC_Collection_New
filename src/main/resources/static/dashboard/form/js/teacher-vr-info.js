function addTeacherInfo() {

    if (teacherValidation()==false){
        return;
    }

    var formValues = $('#teacherVrForm').serializeJSON();
    var cardPersonalInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/branch-verification/teacher-verification/save',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: cardPersonalInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#teacherVerification-div').hide();
            $('#teacherVerificationView').show();

            $('#tvId').val(data.teacherVrInfo.tvId);
            $('#siteVis1').text(data.teacherVrInfo.siteVis);
            $('#visitBy1').text(data.teacherVrInfo.visitBy);
            $('#visDate1').text(data.teacherVrInfo.visDate);
            $('#locOfTution1').text(data.teacherVrInfo.locOfTution);
            $('#major1').text(data.teacherVrInfo.major);
            $('#totStudent1').text(data.teacherVrInfo.totStudent);
            $('#durationOfTution1').text(data.teacherVrInfo.durationOfTution);
            $('#level1').text(data.teacherVrInfo.level);
            $('#tutionFee1').text(data.teacherVrInfo.tutionFee);
            $('#exper1').text(data.teacherVrInfo.exper);
            $('#batchNo1').text(data.teacherVrInfo.batchNo);
            $('#totIncome1').text(data.teacherVrInfo.totIncome);
            $('#stdntPerBatch1').text(data.teacherVrInfo.stdntPerBatch);
            $('#setUp1').text(data.teacherVrInfo.exper);
            $('#comment1').text(data.teacherVrInfo.comment);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Teacher Vr!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#teacherVrCommitButton').click(function(event) {
    event.preventDefault();
    addTeacherInfo();
});

$('#teacherVrViewEditBtn').click(function(event) {
    event.preventDefault();
    $('#teacherVerification-div').show();
    $('#teacherVerificationView').hide();
});



$("#tab-pivot").click(function(){
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    $.ajax({
        url: '/card/branchvr/teachervr',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            $('#customerId').val(data.teacherVrInfo.customerId);
            $('#tvId').val(data.teacherVrInfo.tvId);
            $('#siteVis').val(data.teacherVrInfo.siteVis);
            $('#visitBy').val(data.teacherVrInfo.visitBy);
            $('#visDate').val(data.teacherVrInfo.visDate);
            $('#locOfTution').val(data.teacherVrInfo.locOfTution);
            $('#major').val(data.teacherVrInfo.major);
            $('#totStudent').val(data.teacherVrInfo.totStudent);
            $('#durationOfTution').val(data.teacherVrInfo.durationOfTution);
            $('#level').val(data.teacherVrInfo.level);
            $('#tutionFee').val(data.teacherVrInfo.tutionFee);
            $('#exper').val(data.teacherVrInfo.exper);
            $('#batchNo').val(data.teacherVrInfo.batchNo);
            $('#totIncome').val(data.teacherVrInfo.totIncome);
            $('#stdntPerBatch').val(data.teacherVrInfo.stdntPerBatch);
            $('#setUp').val(data.teacherVrInfo.setUp);
            $('#comment').val(data.teacherVrInfo.comment);

        },
        error: function () {
            ////alert("Error");
        }
    });
});

function teacherValidation() {
    if ($("#siteVis").val()==""){
        alert("Site Visit must be filled out!");
        $("#siteVis").focus();
        return false;
    }

    if ($("#visitBy").val()==""){
        alert("Visitor Name must be filled out!");
        $("#visitBy").focus();
        return false;
    }

    if ($("#locOfTution").val()==""){
        alert("Location must be filled out!");
        $("#locOfTution").focus();
        return false;
    }

    if ($("#durationOfTution").val()==""){
        alert("Duration must be filled out!");
        $("#durationOfTution").focus();
        return false;
    }

    if ($("#tutionFee").val()==""){
        alert("Tution Fee must be filled out!");
        $("#tutionFee").focus();
        return false;
    }

    if ($("#exper").val()==""){
        alert("Experience must be filled out!");
        $("#exper").focus();
        return false;
    }

    if ($("#batchNo").val()==""){
        alert("Number of batch must be filled out!");
        $("#batchNo").focus();
        return false;
    }

    if ($("#stdntPerBatch").val()==""){
        alert("Student Number must be filled out!");
        $("#stdntPerBatch").focus();
        return false;
    }

    if ($("#comment").val()==""){
        alert("Overall Comments must be filled out!");
        $("#comment").focus();
        return false;
    }

    x=$("#totStudent").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totStudent").focus();
        return;
    }

    x=$("#durationOfTution").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#durationOfTution").focus();
        return;
    }

    x=$("#tutionFee").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#tutionFee").focus();
        return;
    }

    x=$("#exper").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#exper").focus();
        return;
    }

    x=$("#batchNo").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#batchNo").focus();
        return;
    }

    x=$("#totIncome").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totIncome").focus();
        return;
    }

    x=$("#stdntPerBatch").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#stdntPerBatch").focus();
        return;
    }
}


