function addCrdCardHomeInfo() {

    var formValues = $('#crCardHomeForm').serializeJSON();
    var crCardHomeFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/customer-info/cr-card-home/save',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: crCardHomeFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#crCardHome').hide();
            $('#crCardHomeDivView').show();
            $('#crCardHomeId').val(data.crCardHomeInfo.crCardHomeId);
            $('#vsmsAlert').text(data.crCardHomeInfo.smsAlert);
            $('#vmailingAddress').text(data.crCardHomeInfo.mailingAddress);
            $('#vndetailsAddress').text(data.crCardHomeInfo.mailingAddressDetails);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Cr Card Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

$('#crCardHomeCommitBtn').click(function(event) {
    event.preventDefault();
    addCrdCardHomeInfo();
});

$('#crCardHomeViewEditBtn').click(function(event) {
    event.preventDefault();
    $('#crCardHome').show();
    $('#crCardHomeDivView').hide();
});

