function save_card_dairy_notes() {
    var manu_name = $('#card_menu').find(":selected").attr('data-name');
    if (manu_name === undefined) {
        alert('Choose  Manu Correctly !');
        return false;
    }
    $('#card_menu_name').val(manu_name);

  /*  var sm1_name = $('#card_submenu_one').find(":selected").attr('data-name');
    if (sm1_name === undefined) {
        alert('Choose Sub Manu 1 Correctly !');
        return false;
    }
    $('#card_submenu_one_name').val(sm1_name);

    var sm2_name = $('#card_submenu_two').find(":selected").attr('data-name');
    if (sm2_name === undefined) {
        alert('Choose Sub Manu 2 Correctly !');
        return false;
    }
    $('#card_submenu_two_name').val(sm2_name);

    var sm3_name = $('#card_submenu_three').find(":selected").attr('data-name');
    if (sm3_name === undefined) {
        alert('Choose Sub Manu 3 Correctly !');
        return false;
    }
    $('#card_submenu_three_name').val(sm3_name);

    if (dairyNoteValidation() == false) {
        return;
    }
*/    var formValues = $('#card_dairynotresForm').serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/collection/card/dairy-notes/save',
        type: 'POST',
        data: formValues,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        /*success: function (data) {
            alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.dairyNotes.id + "</td>" +
                "<td>" + data.dairyNotes.createdBy + "</td>" +
                "<td>" + data.dairyNotes.createdDate + "</td>" +
                "<td>" + data.dairyNotes.card_menu + "</td>" +
                "<td>" + data.dairyNotes.card_submenu_one + "</td>" +
                "<td>" + data.dairyNotes.card_submenu_two + "</td>" +
                "<td>" + data.dairyNotes.card_submenu_three + "</td>" +
                "<td>" + data.dairyNotes.card_date + "</td>" +
                "<td>" + data.dairyNotes.card_time + "</td>" +
                "<td>" + data.dairyNotes.card_short_note + "</td>" +
                "<td>" +
                "<a class=\"btn btn-xs btn-danger\" onclick=\"card_dairy_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a>" +
                "</td>" +
                "</tr>";
            $('#modal-dairy-notes').modal('hide');

            $("#card_dairynotresForm")[0].reset()
            $('#dairy_notes_tBody').append(str);
        },*/
        success: function (data) {
          /*  alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.dairyNotes.id + "</td>" +
                "<td>" + data.dairyNotes.createdBy + "</td>" +
                "<td>" + data.dairyNotes.createdDate + "</td>" +
                "<td>" + data.dairyNotes.card_menu + "</td>" +
                "<td>" + data.dairyNotes.card_submenu_one + "</td>" +
                "<td>" + data.dairyNotes.card_submenu_two + "</td>" +
                "<td>" + data.dairyNotes.card_submenu_three + "</td>" +
                "<td>" + data.dairyNotes.card_date + "</td>" +
                "<td>" + data.dairyNotes.card_time + "</td>" +
                "<td>" + data.dairyNotes.card_short_note + "</td>" +
                "<td>" +
                "<a class=\"btn btn-xs btn-danger\" onclick=\"card_dairy_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a>" +
                "</td>" +
                "</tr>";*/
            dairyNotesBody.updateDairyNotesList(data);
            $('#modal-dairy-notes').modal('hide');

            $("#card_dairynotresForm")[0].reset()
           /* $('#dairy_notes_tBody').append(str);*/
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Dairy Notes Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }

    });


}


function clearData() {
    $("#card_dairynotresForm")[0].reset()
}

$('#card_dairy_btn').click(function (event) {
    clearData();
});

// $("#tab-Card-Diary-Notes").click(function (event) {
//     getdairyNotefData();
// });

function getdairyNotefData() {
    $.ajax({
        url: "/collection/card/dairy-notes/list",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#dairy_notes_tBody').replaceWith("<tbody id=\"dairy_notes_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");

            for (var i = 0; i < data.dairyNotesList.length; i++) {
                if (data.dairyNotesList[i] != null) {
                    var str = "<tr>" +

                        "<td style=\"display:none;\">" + data.dairyNotesList[i].id + "</td>" +
                        "<td>" + data.dairyNotesList[i].createdBy + "</td>" +
                        "<td>" + data.dairyNotesList[i].createdDate + "</td>" +
                        "<td >" + data.dairyNotesList[i].card_menu + "</td>" +
                        "<td >" + data.dairyNotesList[i].card_submenu_one + "</td>" +
                        "<td >" + data.dairyNotesList[i].card_submenu_two + "</td>" +
                        "<td >" + data.dairyNotesList[i].card_submenu_three + "</td>" +
                        "<td>" + data.dairyNotesList[i].card_date + "</td>" +
                        "<td>" + data.dairyNotesList[i].card_time + "</td>" +
                        "<td >" + data.dairyNotesList[i].card_short_note + "</td>" +
                        "<td>" +
                        "<a class=\"btn btn-xs btn-danger\" onclick=\"card_dairy_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a></td>" +
                        "</tr>";
                    $('#dairy_notes_tBody').append(str);
                }
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}


function card_dairy_deleteRow(node) {
    var result = confirm("Want to delete?");
    if (result) {
        var id = node.parentNode.parentNode.cells[0].innerHTML;
        $.ajax({
            url: "/collection/card/dairy-notes/remove?id=" + id,
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


// menu and submenu list using ajax request
function menulist() {

    $.ajax({
        url: "/collection/dnote_menu/listall",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(data.menuList);
            $('#card_menu').empty();
            $("#card_menu").append('<option value="">Choose</option>');
            $.each(data.menuList, function (index, value) {
                $('#card_menu').append('<option value ="' + value.id + '" data-name="' + value.name.toString() + '">' + value.name + '</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function getsubmenu_one() {
    var menuid = $("#card_menu").val().toString();
    // console.log("menuId : "+menuid+" ..")
    $.ajax({
        url: "/collection/dn_submenu1/listall?menuid=" + menuid,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data.sub1List);
            $('#card_submenu_one').empty();
            $("#card_submenu_one").append('<option value="">Choose</option>');
            $.each(data.sub1List, function (index, value) {
                $('#card_submenu_one').append('<option value ="' + value.id + '" data-name="' + value.name.toString() + '">' + value.name + '</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}


function getsubmenu_two() {
    // alert("click main menu 1 ");
    var submenuid1 = $("#card_submenu_one").val().toString();
    var mid = $("#card_menu").val().toString();
    console.log("submenuid1 : " + submenuid1 + " ..")
    $.ajax({
        url: "/collection/dn_submenu2/listall?submenuid1=" + submenuid1 + "&mid=" + mid,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(data.sub2List);
            $('#card_submenu_two').empty();
            $("#card_submenu_two").append('<option value="">Choose</option>');
            $.each(data.sub2List, function (index, value) {
                $('#card_submenu_two').append('<option value ="' + value.id + '" data-name="' + value.name.toString() + '">' + value.name + '</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function getsubmenu_three() {
    // alert("click main menu 1 ");
    var sub2id = $("#card_submenu_two").val().toString();
    // console.log("submenuid1 : "+sub2id+" ..")
    $.ajax({
        url: "/collection/dn_submenu3/listall?sub2id=" + sub2id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#card_submenu_three').empty();
            $("#card_submenu_three").append('<option value="">Choose</option>');
            $.each(data.sub3List, function (index, value) {
                $('#card_submenu_three').append('<option value ="' + value.id + '" data-name="' + value.name.toString() + '">' + value.name + '</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function dairyNoteValidation() {

    if ($("#card_menu").val() == "") {
        alert("menu be filled out!");
        $("#card_menu").focus();
        return false;
    }
    if ($("#card_submenu_one").val() == "") {
        alert("sub menu one be filled out!");
        $("#card_submenu_one").focus();
        return false;
    }

    if ($("#card_submenu_two").val() == "") {
        alert("sub menu two  must be filled out!");
        $("#card_submenu_two").focus();
        return false;
    }


    if ($("#card_submenu_three").val() == "") {
        alert("sub menu three must be filled out!");
        $("#card_submenu_three").focus();
        return false;
    }
    if ($("#card_time").val() == "") {
        alert(" time  must be filled out!");
        $("#card_time").focus();
        return false;
    }
    if ($("#card_date").val() == "") {
        alert("date must be filled out!");
        $("#card_date").focus();
        return false;
    }
    if ($("#card_short_note").val() == "") {
        alert("short note must be filled out!");
        $("#card_short_note").focus();
        return false;
    }

}