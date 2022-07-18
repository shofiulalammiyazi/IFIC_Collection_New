function cardTypeList() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/card/customer-info/employment-info/save-employment-info',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#customerInfoCreditFacilityModal').modal('hide');
            },
            error: function (err) {
                //alert(err);
            }
        });
}

$('#prevEmploymentSaveButton').click(function(event) {
    event.preventDefault();
    addEmploymentInfo();
});