


function vehicleRepossessionSave() {
    var formValue = $("#vehicleRepossessionForm").serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type:"post",
        url:"/customer-loan-profile/vehicle-repossession/save",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data:formValue,
        success:function (data) {
            swal("Success!", "Successfully Saved!", "success");
            $('vehicleType,vehicleModel,vehicleRegistrationNo,vehicleChassisNo,vehicleEngineNo,yearManufacturing,vehicleCC,presentStatus,agencyHandOverDate,RepossessionDate,clStatusRepossessionDate,vehicleReleaseDate,samHandoverDate,AuctionDate').val('');
            $('#modal-vehicle-repossession').modal('toggle')
            $('#vehicleRepossessionBody').html("");
            guarantorInformatoinList(customerId);

        }
    }).done(function(){
        // Clear Form
        $('#vehicleRepossessionForm')[0].reset();

    });

    return false;

}