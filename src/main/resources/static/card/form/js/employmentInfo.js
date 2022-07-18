function addEmploymentInfo() {

    if (employmentInfoValidation()==false){
        return;
    }

    var formValues = $('#employmentInfoForm').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/card/customer-info/employment-info/save',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: jsonEmploymentInfoFormData,
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#employmentInfoMain').hide();
                $('#employmentInfoViewDiv').show();
                $('#empInfoId').val(data.employmentInfo.empInfoId);
                $('#professionV').text(data.employmentInfo.professionName);
                $('#companyNameV').text(data.employmentInfo.companyName);
                $('#departmentV').text(data.employmentInfo.department);
                $('#designationV').text(data.employmentInfo.designation);
                $('#businessNatureV').text(data.employmentInfo.businessNature);
                $('#empfromDateV').text(data.employmentInfo.empfromDate);
                $('#empIndustryNameV').text(data.employmentInfo.empIndustryName);
                $('#empLegalStatusV').text(data.employmentInfo.empLegalStatus);
                $('#empCompanyAddrsV').text(data.employmentInfo.empCompanyAddrs);
                $('#empCompanyNoV').text(data.employmentInfo.empCompanyNo);
                $('#CserviceLengthV').text(data.employmentInfo.cserviceLength);
            },
            error: function (err) {
                $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                    "\t\t\t\t\t</div>");
                var message = "<ul style='list-style-type:disc'><li>Error in saving Employment Info!</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);
            }
        });
}

$('#employmentInfoCommitButton').click(function(event) {

    event.preventDefault();
    addEmploymentInfo();
});

$('#employmentInfoViewEditBtn').click(function(event) {
    event.preventDefault();
    $('#employmentInfoMain').show();
    $('#employmentInfoViewDiv').hide();
});

function employmentInfoValidation() {
    if($("#professionName").val()==0){
        alert("Profession must be filled out!");
        $("#professionName").focus();
        return false;
    }

    if($("#companyName").val()==""){
        alert("Company Name must be filled out!");
        $("#companyName").focus();
        return false;
    }

    if($("#designation").val()==""){
        alert("Designation must be filled out!");
        $("#designation").focus();
        return false;
    }
}


$("#professionName").change(function () {
    var professionValue=$('#professionName option:selected').val();
      /*  $("#professionName").find('option:selected');*/
    if(professionValue=='BussinessMan'){
        $("#empLegalStatusLebel").addClass("required");
    }
    else{
        $("#empLegalStatusLebel").removeClass("required");
    }
})



$("#tab-employment").click(function(){
    var info={
        id : $("#customerIdEdit").val()
    }

    $.ajax({
        type : "GET",
        url : "/card/customer-info/employment-info/view",
        data : info,

        success : function (data) {

            $('#empInfoId').val(data.employmentInfo.empInfoId);
            $('#professionName').val(data.employmentInfo.professionName);
            $('#companyName').val(data.employmentInfo.companyName);
            $('#department').val(data.employmentInfo.department);
            $('#designation').val(data.employmentInfo.designation);
            $('#businessNature').val(data.employmentInfo.businessNature);
            $('#empfromDateV').val(data.employmentInfo.empfromDate);
            $('#empIndustryName').val(data.employmentInfo.empIndustryName);
            $('#empLegalStatus').val(data.employmentInfo.empLegalStatus);
            $('#empCompanyAddrs').val(data.employmentInfo.empCompanyAddrs);
            $('#empCompanyNo').val(data.employmentInfo.empCompanyNo);
            $('#empfromDate').val(data.employmentInfo.empfromDate);
            $('#CserLength').val(data.employmentInfo.cserviceLength);
        },

        error: function (err) {
            ////alert("failure!");
        }
    })


    $.ajax({
        type : "GET",
        url : "/card/customer-info/experiance-info/view",
        data : info,

        success : function (data) {
            if (data.experianceInfo != null) {
                var str = "<tr>" +
                    "<td>" + data.experianceInfo.expProfession + "</td>" +
                    "<td>" + data.experianceInfo.expCompName + "</td>" +
                    "<td>" + data.experianceInfo.expDepartment + "</td>" +
                    "<td>" + data.experianceInfo.expDesignation + "</td>" +
                    "<td>" + data.experianceInfo.expBusinessNature + "</td>" +
                    "<td>" +
                    " <button class='btn-info' value='${expInfoId}' onclick=\"prevExperianceEditBtn()\">Edit</button> "+
                    "</td>" +
                    "</tr>";
                $('#pExperianceInfo_tBody').append(str);
            }


        },

        error: function (err) {
            //alert("failure!");
        }
    })

});
$("#empfromDate").change(function () {
	dateLengthofCurrentEmployee();
});
function dateLengthofCurrentEmployee() {
	var EmpfromDate=$('#empfromDate').val();
	var currentDate =new Date();
	//$('#CserLength').attr('disabled', 'disabled');
	var date1 = new Date(EmpfromDate);
	var date2 = new Date(currentDate);
	var timeDiff = Math.abs(date2.getTime() - date1.getTime());
	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24 *30)); 
	var dateLength = diffDays-1;
	$("#CserLength").val(dateLength);
	

}