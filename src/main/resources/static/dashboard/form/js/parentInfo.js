function addCardParentInfo() {
    if (parentInfoValidation()==false){
        return;
    }
    var formValues = $('#cardParentInfoForm').serializeJSON();
    var parentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/customer-info/parent-info/save',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: parentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#parentInfoFormDiv').hide();
            $('#parentInfoViewDiv').show();
            $('#customerParentId').val(data.parentInfo.customerParentId);
            $('#VparentFatherName').text(data.parentInfo.fatherName);
            $('#VparentMotherName').text(data.parentInfo.motherName);
            $('#VmotherProfession').text(data.parentInfo.motherProfessionId);
            $('#VfatherProffession').text(data.parentInfo.fatherProfessionId);
            $('#VfatherNid').text(data.parentInfo.fatherNid);
            $('#VmotherNid').text(data.parentInfo.motherNid);
            $('#VfatherCompanyName').text(data.parentInfo.fatherCompanyName);
            $('#VmotherCompanyName').text(data.parentInfo.motherCompanyName);
            $('#VfatherContactNumber').text(data.parentInfo.fatherContactNumber);
            $('#VmotherContactNumbe').text(data.parentInfo.motherContactNumber);

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Parent Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#parentInfoCommitBtn').click(function(event) {
    event.preventDefault();
    addCardParentInfo();
});


$('#parentInfoEditButton').click(function(event) {
    event.preventDefault();
    $('#parentInfoFormDiv').show();
    $('#parentInfoViewDiv').hide();
});

function parentInfoValidation() {
    if ($("#motherNamexx").val()==""){
        alert("Mother Name must be filled out!");
        $("#motherNamexx").focus();
        return false;
    }

    if ($("#fatherNamexx").val()==""){
        alert("Father Name must be filled out!");
        $("#fatherNamexx").focus();
        return false;
    }

    var x = $("#motherNidxx").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Mother's National Id must be valid..");
        $("#motherNidxx").focus();
        return false;
    }

    var x = $("#motherContactNumberxx").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Mother's Mobile No. must be valid..");
        $("#motherContactNumberxx").focus();
        return false;
    }

    var x = $("#fatherNidxx").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Father's National Id must be valid..");
        $("#fatherNidxx").focus();
        return false;
    }

    var x = $("#fatherContactNumberxx").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Father's Mobile No. must be valid..");
        $("#fatherContactNumberxx").focus();
        return false;
    }


}

