/*
var totalMonthlyIncome=$("#totalMonthlyIncome").text();
var totalMonthlyExpenditure=$("#totalMonthlyExpenditure").val();
sessionStorage.setItem("totalMonthlyIncome", totalMonthlyIncome);
sessionStorage.setItem("totalMonthlyExpenditure", totalMonthlyExpenditure);
var x=sessionStorage.getItem("totalMonthlyIncome")
alert(x);*/
// alert(sessionStorage.getItem("totalMonthlyExpenditure"));
/*document.getElementById("result").innerHTML = sessionStorage.getItem("lastname");*/


$('#totalMonthlyIncome').html(Number(0));
$('#totalMonthlyExpenditure').html(Number(0));

function addCardPersonalInfo() {
    if (personalInfoValidation()==false){
        return;
    }
    var formValues = $('#cardPersonalInfoForm').serializeJSON();
    var cardPersonalInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/card/customer-info/personal-info/save',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: cardPersonalInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);
            $('#personalInfoFormDiv').hide();
            $('#personalViewDiv').show();
            $('#personalInfoFullName').text(data.personalInfo.fullName);//
            $('#personalInfoNickName').text(data.personalInfo.nickName);//
            $('#dob1').text(data.personalInfo.dob);
            $('#nid1').text(data.personalInfo.nid);
            $('#tinNo1').text(data.personalInfo.tin);
            $('#gender1').text(data.personalInfo.gender);
            $('#educationLevel1').text(data.personalInfo.educationLevel);
            $('#maritalStatus1').text(data.personalInfo.martialStatus);
            $('#noOfChildren1').text(data.personalInfo.noOfChildren);
            $('#passportNo1').text(data.personalInfo.passportNo);
            $('#pasExpDate1').text(data.personalInfo.pasExpDate);
            $('#carOwnership1').text(data.personalInfo.carOwnership);
            $('#drivingLicenseNo1').text(data.personalInfo.drivingLicenseNo);
            $('#dlExpDate1').text(data.personalInfo.dlExpDate);
            $('#residentialStatus1').text(data.personalInfo.residentialStatus);
            $('#address1').text(data.personalInfo.prAddress1 + " " + data.personalInfo.prAddress2);
            $('#prPostCode1').text(data.personalInfo.prPostCode);
            $('#prCountry1').text(data.personalInfo.prCountry);
            $('#prCity1').text(data.personalInfo.prCity);
            $('#prLivingPeriodYear1').text(data.personalInfo.prLivingPeriodYear);
            $('#prLivingPeriodMonth1').text(data.personalInfo.prLivingPeriodMonth);
            $('#permAddrs1').text(data.personalInfo.prmAddress1+ " " + data.personalInfo.prmAddress2);
            $('#prmPostCode1').text(data.personalInfo.prmPostCode);
            $('#prmCountry1').text(data.personalInfo.prmCountry);
            $('#prmCity1').text(data.personalInfo.prmCity);
            $('#clubmembership1').text(data.personalInfo.clubmembership);
            $('#membershipNo1').text(data.personalInfo.membershipNo);
            $('#prmLivingPeriodYear1').text(data.personalInfo.prmLivingPeriodYear);
            $('#prmLivingPeriodMonth1').text(data.personalInfo.prmLivingPeriodMonth);
            $('.customer-id').val(data.personalInfo.customerId);
            
            var cdi=$("#cardId").val();
            var pId=$("#pId").val();
            var tc=$("#tc").val();
            
            
            var url="/card/cardDetails?cdi="+cdi;
            var str="<div class=\"box-header with-border\" id=\"genBox\">\n" +
                "\t\t\t\t<h3  class=\"box-title\">View General Information</h3>\n" +
                "\t\t\t<a href=\"/card/cardDetails?cdi="+cdi.toString()+"&pid="+pId.toString()+"&tc="+tc.toString()+"\" class=\"btn btn-primary btn-edit pull-right\"><i class=\"fa fa-eye\"></i>Preview </a>\n" +
                "\t\t\t</div>";
            $("#genBox").replaceWith(str);



        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Personal Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#personalInfoCommitBtn').click(function(event) {
    event.preventDefault();
    addCardPersonalInfo();
});

$('#personalInfoEditBtn').click(function(event) {
    event.preventDefault();
    $('#personalInfoFormDiv').show();
    $('#personalViewDiv').hide();
});

$('#maritalStatus').change(function () {
    if ($('#maritalStatus').val()=='Single'){
        $('#noOfChildren').val('0');
        $('#parentInfoFormDiv').show();
        $('#spouseInfoFormDiv').hide();
    }
    else {
        $('#noOfChildren').val('');
        $('#spouseInfoFormDiv').show();
        $('#parentInfoFormDiv').show();
    }
})

$('#spouseInfoFormDiv').hide();

// Present address to Permanent address
$(document).ready(function () {
    $('#check1').on('click',function () {
        if (this.checked){
            $('#prmAddress1').val($('#prAddress1').val());
            $('#prmAddress2').val($('#prAddress2').val());
            $('#prmPostCode').val($('#prPostCode').val());
            $('#prmCountry').val($('#prCountry').val());
            $('#prmCity').val($('#prCity').val());
            $('#prmLivingPeriodYear').val($('#prLivingPeriodYear').val());
            $('#prmLivingPeriodMonth').val($('#prLivingPeriodMonth').val());
        }
        else {
            $('#prmAddress1').val('');
            $('#prmAddress2').val('');
            $('#prmPostCode').val('');
            $('#prmCountry').val('');
            $('#prmCity').val('');
            $('#prmLivingPeriodYear').val('');
            $('#prmLivingPeriodMonth').val('');
        }
    })
})

function personalInfoValidation() {


    if ($("#fullName1").val()==""){
        alert("Name Must be filled out!");
        $("#fullName1").focus();
        return false;
    }

    if ($("#fullName1").val().length>18){
        alert("Maximum 18 Character is allowed");
        $("#fullName1").focus();
        return false;
    }

    if ($("#nid").val()==""){
        alert("National Id Must be filled out!");
        $("#nid").focus();
        return false;
    }

    if ($("#nid").val().length <8 || $("#nid").val().length >20){
        alert("National-Id Must be Between 8-20 Letter ");
        $("#nid").focus();
        return false;
    }

    if ($("#nickName1").val().length >19){
        alert("Embossed-Name at-most 19 char");
        $("#nickName1").focus();
        return false;
    }

    if ($("#gender").val()==""){
        alert("Gender Must be filled out!");
        $("#gender").focus();
        return false;
    }

    if ($("#tinNo").val()==""){
        alert("TIN Must be filled out!");
        $("#tinNo").focus();
        return false;
    }


    if ($("#tinNo").val().length<9  || $("#tinNo").val().length>15){
        alert("TIN No Max 15 and Min 9");
        $("#tinNo").focus();
        return false;
    }

    if ($("#maritalStatus").val()==""){
        alert("Marital Status Must be filled out!");
        $("#maritalStatus").focus();
        return false;
    }
    
    if ($("#date_of_birth").val()==""){
        alert("Date of Birth must be selected");
        $("#date_of_birth").focus();
        return false;
    }
 
    if ($("#date_of_birth").val()!==null){
	    var date=$("#date_of_birth").val();
	    var pieces = date.split("/");
	    var month=pieces[0];
	    var day=pieces[1];
	    var year=pieces[2];
	    var age = 18;
	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);
	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);
	
	    if(currdate < mydate){
	        alert('You must be at least 18 years of age.');
	         $("#date_of_birth").focus();
	            return false;
	    } 
    }

        var today = new Date();
        $('#date_of_birth').datepicker({
            format: 'mm-dd-yyyy',
            autoclose:true,
            endDate: "today",
            maxDate: today
        }).on('changeDate', function (ev) {
            $(this).datepicker('hide');
        });


        $('#date_of_birth').keyup(function () {
            if (this.value.match(/[^0-9]/g)) {
                this.value = this.value.replace(/[^0-9^-]/g, '');
            }
        });


    // number validation
    var x = $("#nid").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("National ID must be valid..");
        $("#nid").focus();
        return false;
    }

    var x = $("#tinNo").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("TIN must be valid..");
        $("#tinNo").focus();
        return false;
    }
    /*  if ($("#noOfChildren").val()==""){
    alert("Number Of Children Must be filled out!");
    $("#noOfChildren").focus();
    return false;
}


if ($("#passportNo").val()==""){
    alert("Passport No Must be filled out!");
    $("#passportNo").focus();
    return false;
}

if ($("#carOwnership").val()==""){
    alert("Car Ownership Status Must be filled out!");
    $("#carOwnership").focus();
    return false;
}


if ($("#drivingLicenseNo").val()==""){
    alert("Driving License Must be filled out!");
    $("#drivingLicenseNo").focus();
    return false;
}

if ($("#residentialStatus").val()==""){
    alert("Residential Status Must be filled out!");
    $("#residentialStatus").focus();
    return false;
}

if ($("#prPostCode").val()==""){
    alert("Present Post Code Must be filled out!");
    $("#prPostCode").focus();
    return false;
}

if ($("#prCountry").val()==""){
    alert("Present Country Must be filled out!");
    $("#prCountry").focus();
    return false;
}

if ($("#prCity").val()==""){
    //alert("Present City Must be filled out!");
    $("#prCity").focus();
    return false;
}

if ($("#prLivingPeriodYear").val()==""){
    alert("Present Living Year Must be filled out!");
    $("#prLivingPeriodYear").focus();
    return false;
}

if ($("#prLivingPeriodMonth").val()==""){
    alert("Present Living Month Must be filled out!");
    $("#prLivingPeriodMonth").focus();
    return false;
}

if ($("#prmPostCode").val()==""){
    alert("Post Code Must be filled out!");
    $("#prmPostCode").focus();
    return false;
}

if ($("#prmCountry").val()==""){
    alert("Country Must be filled out!");
    $("#prmCountry").focus();
    return false;
}

if ($("#prmCity").val()==""){
    //alert("City Must be filled out!");
    $("#prmCity").focus();
    return false;
}

if ($("#prmLivingPeriodYear").val()==""){
    alert("Year Must be filled out!");
    $("#prmLivingPeriodYear").focus();
    return false;
}

if ($("#prmLivingPeriodMonth").val()==""){
    alert("Month Must be filled out!");
    $("#prmLivingPeriodMonth").focus();
    return false;
}*/


}