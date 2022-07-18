

function addcreditCardInsDetails() {

    //var formValues = $('#debitCardForm').serializeJSON();
     var formValues = submitCurrentForm($(this));
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/card/customer-info/cr-card-details/save-cr-card-details',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: jsonEmploymentInfoFormData,
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                /*$('#creditCardDetailsModal').modal('hide');*/
            },
            error: function (err) {
                //alert(err);
            }
        });
}

$('#debitInstructionCommitBtn').click(function(event) {
    event.preventDefault();
    addcreditCardInsDetails();
});