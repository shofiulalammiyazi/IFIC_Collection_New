/*<![CDATA[*/
var customerId = /*[[${cardCustomerInfo.id}]]*/ [];

/*]]>*/
console.log("customer Id : "+JSON.stringify(customerId))
function dailyNoteSave() {
    var formValues = $('#dailyNoteSaveForm').serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/collection/card/daily-notes/save',
        type: 'POST',
        data: formValues,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);

            $('#modal-dailynote').modal('hide');

            $("#dailyNoteSaveForm")[0].reset();
            //document.location.reload();
            var dailyNote = {};
            jQuery('input[name="url"]').val();
            return;
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving ptp Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }

    });
}
