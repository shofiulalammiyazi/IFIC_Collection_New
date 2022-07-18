function card_referenceInfoSave() {
    if (refValidation() == false) {
        return;
    }
    var formValues = $('#card_referenceInfoSaveForm').serialize();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/collection/reference/save',
        type: 'POST',
        // contentType: 'application/json; charset=utf-8',
        // dataType: 'json',
        data: formValues,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.referenceInfo.id + "</td>" +
               /* "<td>" + data.referenceInfo.ref_account_no + "</td>" +*/
                "<td>" + data.referenceInfo.ref_name + "</td>" +
                "<td>" + data.referenceInfo.ref_home_address + "</td>" +
                "<td>" + data.referenceInfo.ref_phone_no + "</td>" +
                "<td>" + data.referenceInfo.ref_relationship + "</td>" +
                "<td>" +
                "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"card_deleteRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a>" +
                "</td>" +
                "</tr>";
            $('#card-modal-reference-info').modal('hide');

            $("#card_referenceInfoSaveForm")[0].reset()
            $('#card_referenceinfo_tBody').append(str);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Reference Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }

    });


}

function card_deleteRow(node) {
    var result = confirm("Want to delete?");
    if (result) {
        var refId = node.parentNode.parentNode.cells[0].innerHTML;
        $.ajax({
            url: "/collection/reference/remove?id=" + refId,
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

// function editRow(node){
//
//     var row=node.parentNode.parentNode;
//     globalRow=row;
//     $("#refId").val(row.cells[0].innerHTML);
//     $("#refName").val(row.cells[1].innerHTML);
//     $("#relationship").val(row.cells[2].innerHTML);
//     $("#refMobile").val(row.cells[3].innerHTML);
//     $("#refTelNo").val(row.cells[4].innerHTML);
//     $("#refProfession").val(row.cells[5].innerHTML);
//     $("#ext").val(row.cells[6].innerHTML);
//     $("#refCompAddress").val(row.cells[7].innerHTML);
//     $("#refStatus").val(row.cells[8].innerHTML);
//     $("#prmntAddrs").val(row.cells[9].innerHTML);
//     $("#resdntlAddrs").val(row.cells[10].innerHTML);
//     $("#designation").val(row.cells[11].innerHTML);
//     $("#emailId").val(row.cells[12].innerHTML);
//
//     $('#modal-reference-info').modal('show');
// }

function clearData() {
    $("#ref_account_no").val("");
    $("#ref_name").val("");
    $("#ref_address").val("");
    $("#ref_relationship").val("");

}

$('#cardRefCancelBtn').click(function (event) {

    clearData();
});

// $("#tab-card-referenceInfo").click(function (event) {
//     //event.preventDefault();
//      geteRefData();
// });

function geteRefData() {
    $.ajax({
        url: "/collection/reference/list",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#card_referenceinfo_tBody').replaceWith("<tbody id=\"card_referenceinfo_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");

            for (var i = 0; i < data.referenceInfoList.length; i++) {
                if (data.referenceInfoList[i] != null) {
                    var str = "<tr>" +

                        "<td style=\"display:none;\">" + data.referenceInfoList[i].id + "</td>" +
                        "<td>" + data.referenceInfoList[i].ref_account_no + "</td>" +
                        "<td>" + data.referenceInfoList[i].ref_name + "</td>" +
                        "<td >" + data.referenceInfoList[i].ref_address + "</td>" +
                        "<td >" + data.referenceInfoList[i].ref_phone_no + "</td>" +
                        "<td>" + data.referenceInfoList[i].ref_relationship + "</td>" +
                        "<td>" +
                        "<a class=\"btn btn-xs btn-danger\" onclick=\"card_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a></td>" +
                        "</tr>";
                    $('#card_referenceinfo_tBody').append(str);
                }
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function refValidation() {

    if ($("#ref_account_no").val() == "") {
        alert("Account must be filled out!");
        $("#ref_account_no").focus();
        return false;
    }
    if ($("#ref_name").val() == "") {
        alert("Name must be filled out!");
        $("#ref_name").focus();
        return false;
    }

    if ($("#ref_address").val() == "") {
        alert("Address must be filled out!");
        $("#ref_address").focus();
        return false;
    }


    if ($("#ref_relationship").val() == "") {
        alert("Relationship must be filled out!");
        $("#ref_relationship").focus();
        return false;
    }

}
