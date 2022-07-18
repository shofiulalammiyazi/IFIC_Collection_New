async function save_card_follow_up() {

    var booleanMesg = false;
    var formValues = $('#card_followUpForm').serialize();
    var jsonFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    var followUpReason = [];
    $('.ads_Checkbox').each(function () {
        if ($(this).prop('checked') == true) {
            if (followUpReason.length > 0) {
                followUpReason += ", ";
            }
            followUpReason += $(this).val();
        }
    });

  await  $.ajax({
        url: '/collection/card/follow-up/save',
        type: 'POST',
        // contentType: 'application/json; charset=utf-8',
        // dataType: 'json',
        data: formValues+"&followUpReason="+followUpReason,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);},
        success: function (data) {
            booleanMesg=true;
           // alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.followUp.id + "</td>" +
                "<td>" + data.followUp.username +' '+ data.followUp.createdBy +"</td>" +
                "<td>" + data.followUp.followUpDate + '/' + data.followUp.followUpTimes + "</td>" +
                // "<td>" + data.followUp.followUpTimes + "</td>" +
                "<td>" + data.followUp.followUpReason + "</td>" +
                // "<td>" + data.followUp.followUpRemarks + "</td>" +
                // "<td>" +
                // "<a class=\"btn btn-xs btn-danger\" onclick=\"card_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a>" +
                // "</td>" +
                "</tr>";
            //$('#modal-follow-up').modal('hide');

            $("#card_followUpForm")[0].reset()
            $('#card_followup_tBody').prepend(str);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Follow Up Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
  //          console.log(err);
        }
    });

  return booleanMesg;
}

function dateFormat_DD_MM_YYYY(date){
    var mS = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'];
    if(date != null && date != ""){
        date = new Date(date);
        var year = date.getFullYear();
        var month = date.getMonth();
        var day = date.getDate();
        return day +'-'+mS[month]+'-'+year;
    }
}

function clearData() {
    $("#ref_account_no").val("");
    $("#ref_name").val("");
    $("#ref_address").val("");
    $("#ref_relationship").val("");

}

$('#cardRefCancelBtn').click(function (event) {

    clearData();
});

// $("#tab-Card-Follow-Up").click(function (event) {
//     //event.preventDefault();
//      geteRefData();
// });

function geteRefData() {
    $.ajax({
        url: "/collection/card/follow-up/list",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#card_followup_tBody').replaceWith("<tbody id=\"card_followup_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");

            for (var i = 0; i < data.followUpList.length; i++) {
                if (data.followUpList[i] != null) {
                    var str = "<tr>" +

                        "<td style=\"display:none;\">" + data.followUpList[i].id + "</td>" +
                        "<td>" + data.followUpList[i].createdBy + "</td>" +
                        "<td>" + data.followUpList[i].dates + "</td>" +
                        "<td >" + data.followUpList[i].times + "</td>" +
                        "<td >" + data.followUpList[i].reason + "</td>" +
                        "<td>" +
                        "<a class=\"btn btn-xs btn-danger\" onclick=\"card_followup_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a></td>" +
                        "</tr>";
                    $('#card_followup_tBody').append(str);
                }
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}


function card_followup_deleteRow(node) {
    var result = confirm("Want to delete?");
    if (result) {
        var refId = node.parentNode.parentNode.cells[0].innerHTML;
        $.ajax({
            url: "/collection/card/follow-up/remove?id=" + refId,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                alert(data.successMsg);
            },
            error: function (data) {
                //alert("Error!");
            }
        });

        $(node).closest("tr").remove();
    }

}


var date = new Date();
$('.datepicker').datepicker({
    startDate: date,
    format: 'dd-mm-yyyy',
    autoclose: true,
    // container:'#modal-filefollowup',
    forceParse: false
})

