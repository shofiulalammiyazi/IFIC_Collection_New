function addPropertyVrInfo() {

    if (propertyValidation()==false){
        return;
    }

    var formValues = $('#propertyVrForm').serializeJSON();
    var propertyVrFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/branch-verification/property-verification/save-property-vr',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: propertyVrFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#propertyVrFormDiv').hide();
            $('#propertyVrViewDiv').show();

            $('#pvId').val(data.propertyVrInfo.pvId);
            $('#vpropertyCustName').text(data.propertyVrInfo.propertyCustName);
            $('#vpropertySiteVisit').text(data.propertyVrInfo.propertySiteVisit);
            $('#vpropertyVisitCondBy').text(data.propertyVrInfo.propertyVisitCondBy);
            $('#vpropertyVisitDate').text(data.propertyVrInfo.propertyVisitDate);
            $('#vloanPurpose').text(data.propertyVrInfo.loanPurpose);

            $('#vpropertyType').text(data.propertyVrInfo.propertyType);
            $('#vpropertyNature').text(data.propertyVrInfo.propertyNature);
            $('#vdeveloperName').text(data.propertyVrInfo.developerName);
            $('#vagreementValue').text(data.propertyVrInfo.agreementValue);
            $('#vtentativeDate').text(data.propertyVrInfo.tentativeDate);

            $('#vtentativeDate').text(data.propertyVrInfo.tentativeDate);
            $('#vtentativeDate').text(data.propertyVrInfo.tentativeDate);
            $('#vtentativeDate').text(data.propertyVrInfo.tentativeDate);
            $('#vtentativeDate').text(data.propertyVrInfo.tentativeDate);
            $('#vtentativeDate').text(data.propertyVrInfo.tentativeDate);

            $('#vprojectName').text(data.propertyVrInfo.projectName);
            $('#vroadWidth').text(data.propertyVrInfo.roadWidth);
            $('#vNoOfFloors').text(data.propertyVrInfo.noOfFloors);
            $('#vpropertyLocOfProp').text(data.propertyVrInfo.propertyLocOfProp);
            $('#vlandArea').text(data.propertyVrInfo.landArea);

            $('#vareaPerFloor').text(data.propertyVrInfo.areaPerFloor);
            $('#vpropertyOwner').text(data.propertyVrInfo.propertyOwner);
            $('#vlandValue').text(data.propertyVrInfo.landValue);
            $('#vareaOfBuilding').text(data.propertyVrInfo.areaOfBuilding);
            $('#vrelOfOwner').text(data.propertyVrInfo.relOfOwner);

            $('#vtotalLandValue').text(data.propertyVrInfo.totalLandValue);
            $('#vbuildingValue').text(data.propertyVrInfo.buildingValue);
            $('#vapartmentSize').text(data.propertyVrInfo.apartmentSize);
            $('#vpresentValue').text(data.propertyVrInfo.presentValue);
            $('#vforceSale').text(data.propertyVrInfo.forceSale);

            $('#vworkProgress').text(data.propertyVrInfo.workProgress);
            $('#vpropertyOverallComments').text(data.propertyVrInfo.propertyOverallComments);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Property Verification!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#propertyVRCommitButton').click(function(event) {
    event.preventDefault();
    addPropertyVrInfo();
});

$('#propertyVrEditBtn').click(function(event) {
    event.preventDefault();
    $('#propertyVrFormDiv').show();
    $('#propertyVrViewDiv').hide();
});



$("#tab-propertyVR").click(function(){
    var dt={
        name : "name",
        cid : $("#personalCustomerIdEdit").val()
    };
    $.ajax({
        url: '/card/branchvr/propertyvr',
        type: "GET",
        data: dt,
        dataType: 'json',
        success: function (data) {
            $('#customerId').val(data.propertyVrInfo.customerId);

            $('#pvId').val(data.propertyVrInfo.pvId);
            $('#propertyCustName').val(data.propertyVrInfo.propertyCustName);
            $('#propertySiteVisit').val(data.propertyVrInfo.propertySiteVisit);
            $('#propertyVisitCondBy').val(data.propertyVrInfo.propertyVisitCondBy);
            $('#propertyVisitDate').val(data.propertyVrInfo.propertyVisitDate);
            $('#loanPurpose').val(data.propertyVrInfo.loanPurpose);

            $('#vvpropertyType').val(data.propertyVrInfo.propertyType);
            $('#propertyNature').val(data.propertyVrInfo.propertyNature);
            $('#developerName').val(data.propertyVrInfo.developerName);
            $('#agreementValue').val(data.propertyVrInfo.agreementValue);
            $('#tentativeDate').val(data.propertyVrInfo.tentativeDate);

            $('#tentativeDate').val(data.propertyVrInfo.tentativeDate);
            $('#tentativeDate').val(data.propertyVrInfo.tentativeDate);
            $('#tentativeDate').val(data.propertyVrInfo.tentativeDate);
            $('#tentativeDate').val(data.propertyVrInfo.tentativeDate);
            $('#tentativeDate').val(data.propertyVrInfo.tentativeDate);

            $('#projectName').val(data.propertyVrInfo.projectName);
            $('#roadWidth').val(data.propertyVrInfo.roadWidth);
            $('#noOfFloors').val(data.propertyVrInfo.noOfFloors);
            $('#propertyLocOfProp').val(data.propertyVrInfo.propertyLocOfProp);
            $('#landArea').val(data.propertyVrInfo.landArea);

            $('#areaPerFloor').val(data.propertyVrInfo.areaPerFloor);
            $('#propertyOwner').val(data.propertyVrInfo.propertyOwner);
            $('#landValue').val(data.propertyVrInfo.landValue);
            $('#areaOfBuilding').val(data.propertyVrInfo.areaOfBuilding);
            $('#relOfOwner').val(data.propertyVrInfo.relOfOwner);

            $('#totalLandValue').val(data.propertyVrInfo.totalLandValue);
            $('#buildingValue').val(data.propertyVrInfo.buildingValue);
            $('#apartmentSize').val(data.propertyVrInfo.apartmentSize);
            $('#presentValue').val(data.propertyVrInfo.presentValue);
            $('#forceSale').val(data.propertyVrInfo.forceSale);

            $('#workProgress').val(data.propertyVrInfo.workProgress);
            $('#propertyOverallComments').val(data.propertyVrInfo.propertyOverallComments);
        },
        error: function () {
            ////alert("Error");
        }
    });
});

function propertyValidation() {
    if ($("#propertyVisitCondBy").val() == "") {
        alert("Visitor Name must be filled out!");
        $("#propertyVisitCondBy").focus();
        return false;
    }

    if ($("#propertyVisitDate").val() == "") {
        alert("Visit Date must be filled out!");
        $("#propertyVisitDate").focus();
        return false;
    }

    if ($("#loanPurpose").val() =="select") {
        alert("Loan Puspose must be filled out!");
        $("#loanPurpose").focus();
        return false;
    }

    if ($("#vvpropertyType").val() =="select") {
        alert("Property Type must be filled out!");
        $("#vvpropertyType").focus();
        return false;
    }

    if ($("#propertyNature").val() =="select") {
        alert("Property Nature must be filled out!");
        $("#propertyNature").focus();
        return false;
    }

    if ($("#agreementValue").val() == "") {
        alert("Agreement Value must be filled out!");
        $("#agreementValue").focus();
        return false;
    }

    if ($("#tentativeDate").val() == "") {
        alert("Tentative Date must be filled out!");
        $("#tentativeDate").focus();
        return false;
    }

    if ($("#roadWidth").val() == "") {
        alert("Road width must be filled out!");
        $("#roadWidth").focus();
        return false;
    }

    if ($("#noOfFloors").val() == "") {
        alert("Floor Number must be filled out!");
        $("#NoOfFloors").focus();
        return false;
    }

    if ($("#propertyLocOfProp").val() == "") {
        alert("Property Location must be filled out!");
        $("#propertyLocOfProp").focus();
        return false;
    }

    if ($("#landArea").val() == "") {
        alert("Land Area must be filled out!");
        $("#landArea").focus();
        return false;
    }

    if ($("#areaPerFloor").val() == "") {
        alert("Area per floor must be filled out!");
        $("#areaPerFloor").focus();
        return false;
    }

    if ($("#landValue").val() == "") {
        alert("Land Valur must be filled out!");
        $("#landValue").focus();
        return false;
    }

    if ($("#areaOfBuilding").val() == "") {
        alert("Existing Area must be filled out!");
        $("#areaOfBuilding").focus();
        return false;
    }

    if ($("#totalLandValue").val() == "") {
        alert("Total land value must be filled out!");
        $("#totalLandValue").focus();
        return false;
    }

    if ($("#buildingValue").val() == "") {
        alert("Building value must be filled out!");
        $("#buildingValue").focus();
        return false;
    }

    if ($("#presentValue").val() == "") {
        alert("Present value must be filled out!");
        $("#presentValue").focus();
        return false;
    }

    if ($("#forceSale").val() == "") {
        alert("Force sale value must be filled out!");
        $("#forceSale").focus();
        return false;
    }

    if ($("#propertyOverallComments").val() == "") {
        alert("Comments must be filled out!");
        $("#propertyOverallComments").focus();
        return false;
    }

    x=$("#agreementValue").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#agreementValue").focus();
        return;
    }

    x=$("#roadWidth").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#roadWidth").focus();
        return;
    }

    x=$("#noOfFloors").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#NoOfFloors").focus();
        return;
    }

    x=$("#landArea").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#landArea").focus();
        return;
    }

    x=$("#areaPerFloor").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#areaPerFloor").focus();
        return;
    }

    x=$("#landValue").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#landValue").focus();
        return;
    }

    x=$("#areaOfBuilding").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#areaOfBuilding").focus();
        return;
    }

    x=$("#totalLandValue").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#totalLandValue").focus();
        return;
    }

    x=$("#buildingValue").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#buildingValue").focus();
        return;
    }

    x=$("#apartmentSize").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#apartmentSize").focus();
        return;
    }

    x=$("#presentValue").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#presentValue").focus();
        return;
    }

    x=$("#forceSale").val();
    if (isNaN(x) || x<0){
        alert("Input valid number!");
        $("#forceSale").focus();
        return;
    }

}
