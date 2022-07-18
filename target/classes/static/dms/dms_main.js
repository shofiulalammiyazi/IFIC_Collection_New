var globalRow;

var hasFile = 0;
// function showManagerReportPreview(previewUrl, fileType) {
//     $("#showPreviewReport").attr("src", "/card/document/uploadfile/document?dId=" + previewUrl + "&type=" + fileType);
// }

function showManagerReportPreview2(previewUrl, fileType) {
    $("#iframeMangerCommnet").attr("src", "/card/document/uploadfile/document?dId=" + previewUrl + "&type=" + fileType);
    $("#idivpreview").show();
}

function iframeContentManager() {
    $("#iframeMangerCommnet").contents().find("img").css("max-width", "95%");
}

function showReportPreview(previewUrl, fileType) {
    $("#showPreviewReport").attr("src", "/collection/dms/document/preview?id=" + previewUrl + "&type=" + fileType);
}

function downloadFile(previewUrl, fileType) {
    let protocol = window.location.protocol;
    let host = window.location.host;
    let url = protocol + "//" + host + "/collection/dms/document/preview?id=" + previewUrl + "&type=" + fileType;
    window.open(url);
}

function showReportPreview2(previewUrl, fileType) {
    $("#iframe").attr("src", "/collection/dms/document/preview?id=" + previewUrl + "&type=" + fileType);
    $("#idiv").show();
}


function mangerfile_preview(tabNo, i) {
    $("#td_" + tabNo + "_" + i).empty();
    var fileType = $("#mfile_" + tabNo + "_" + i)[0].files[0].type;
    var a = fileType.match(/image\//);


    if (fileType.match(/image\//) == "image/") {
        var thumbnail = '<div id="dv_' + tabNo + '_' + i + '" class="thumbnail"> <img style="width:100%" src="' +
            URL.createObjectURL($("#mfile_" + tabNo + "_" + i)[0].files[0]) + '" /> <a style="font-size:40px;position: absolute;top: 50%; left: 50%; transform: translate(-50%, -50%)" href="#" onclick="removeFilemanager(' + tabNo + ',' + i + ');">' +
            '<i class="fa fa-trash"></i></a> </div>';
        $("#td_" + tabNo + "_" + i).append(thumbnail);

    } else if (fileType.match(/\/\pdf/) == "\/pdf") {
        var thumbnail = '<div id="dv_' + tabNo + '_' + i + '" class="thumbnail"> <iframe style="width:100%" src="' +
            URL.createObjectURL($("#mfile_" + tabNo + "_" + i)[0].files[0]) + '" /> <a style="font-size:40px;position: absolute;top: 50%; left: 50%; transform: translate(-50%, -50%)" href="#" onclick="removeFilemanager(' + tabNo + ',' + i + ');">' +
            '<i class="fa fa-trash"></i></a> </div>';
        $("#td_" + tabNo + "_" + i).append(thumbnail);
    }
    hasFile = hasFile + 1;

}


function isValidFileUpload(file) {
    if (typeof file === 'undefined' && file == null)
        return "Please select a file";
    else if (file.size >= 5242880)
        return "File too Big, please select a file less than 5mb";
    else
        return "valid";

}

function removeFilemanager(tabNo, i) {
    $("#mfile_" + tabNo + "_" + i).val('');
    $("#dv_" + tabNo + "_" + i).remove();
    hasFile = hasFile - 1;

}

function commnetFormClear() {
    $("#managerRecommendation")[0].reset();
    removeFilemanager(200, 0);
    $("#manager_rec_id").val('');
    $("#idivpreview").hide();
    $("#manager-file-table").hide();
}

function uploadBranchlebelcoment() {
    $("#spinner").modal(
        {
            toggle: "toggle",
            backdrop: "static",
            keyboard: false
        });
    $("#specialAlert").removeClass("errorDiv");
    $("#specialAlert").text("");

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var id = $("#manager_rec_id").val();
    var manager_comment = $('#manager_comment').val();
    var cardId = $('#cardId').val();
    var dmsFileId = $('#uploadFileID').val();
    var customerId = $(".customer-id").val();
    var formData = new FormData();
    var file = $("#mfile_200_0")[0].files[0];
    formData.append("id", id);
    formData.append("branchComment", manager_comment);
    formData.append("file", file);
    formData.append('cardId', cardId);
    formData.append('customerId', customerId);
    if ($("#manager_rec_id").val() != '') {
        formData.append('dmsFileId', dmsFileId);
    }
    if ($("#manager_rec_id").val() == '') {
        $.ajax({
            url: '/card/manager-recommendation/upload',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: formData,
            processData: false,
            contentType: false,

            type: 'POST',
            success: function (data) {
                $("#modal-manager-recommendation").modal("hide");
                $("#spinner").modal('hide');
                $("#specialAlert").text("Comment & File Successfully Saved");
                $("#exampleModalLong").modal("toggle");
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.id + "</td>" +
                    "<td style=\"display:none;\">" + data.dmsFileId + "</td>" +
                    "<td>" + data.branchComment + "</td>" +
                    "<td>" +
                    "<a class=\"btn btn-xs\" onclick=\"editRowManagerDocoment(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteRowManagerDocoment(this)\"> <i class=\"fa fa-trash\"></i>Delete</a>" +
                    "</td>" +
                    "</tr>";
                $("#managerRecommendation")[0].reset();
                $("#managerrecommend_tBody").append(str);
                $("#manager-file-table").hide();
                $("#idivpreview").hide();
                removeFilemanager(200, 0);
            },
            error: function (err) {
                $("#spinner").modal('hide');
                var message = "<ul style='list-style-type:disc'><li>Comment & File  Upload Error</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);

            }
        });
    } else {
        $.ajax({
            url: '/card/manager-recommendation/update',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: formData,
            processData: false,
            contentType: false,

            type: 'POST',
            success: function (data) {
                $("#modal-manager-recommendation").modal("hide");
                $("#spinner").modal('hide');
                $("#specialAlert").text("Comment & File update Successfully Uploaded");
                $("#exampleModalLong").modal("toggle");
                globalRow.cells[0].innerHTML = data.id;
                globalRow.cells[1].innerHTML = data.dmsFileId;
                globalRow.cells[2].innerHTML = data.branchComment;
                $("#managerRecommendation")[0].reset();
                $("#manager-file-table").hide();
                $("#idivpreview").hide();
                removeFilemanager(200, 0);

            },
            error: function (err) {
                $("#spinner").modal('hide');
                var message = "<ul style='list-style-type:disc'><li>Comment & File  Upload Error</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);

            }
        });
    }


}


function viewmangaercomment() {
    // var id = $("#personalCustomerIdEdit").val();
    var id = $('#cardId').val();

    $.ajax({
        url: "/card/manager-recommendation/list?cardId=" + id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $('#managerrecommend_tBody').replaceWith("<tbody id=\"managerrecommend_tBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");

            for (var i = 0; i < data.comment.length; i++) {
                if (data.comment[i] != null) {
                    var str = "<tr>" +
                        "<td style=\"display:none;\">" + data.comment[i].id + "</td>" +
                        "<td style=\"display:none;\">" + data.comment[i].dmsFileId + "</td>" +
                        "<td>" + data.comment[i].branchComment + "</td>" +
                        "<td><a class=\"btn btn-xs\" onclick=\"editRowManagerDocoment(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteRowManagerDocoment(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                        "</tr>";
                    $('#managerrecommend_tBody').append(str);
                }
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });

}

function editRowManagerDocoment(node) {
    var row = node.parentNode.parentNode;
    globalRow = row;
    var dmsId = row.cells[1].innerHTML;
    if (dmsId !== null && dmsId !== '') {
        $.ajax({
            url: "/card/manager-recommendation/view?dmsId=" + dmsId,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                $("#manager_rec_id").val(row.cells[0].innerHTML);
                $("#manager_comment").val(row.cells[2].innerHTML);
                $("#uploadFileID").val(data.comment.dmsFileId);
                $("#docurl").val(data.comment.documentUrl);
                $("#docname").text(data.comment.documentName);
                $("#doctype").val(data.comment.documentType);
                $("#manager-file-table").show();

            },
            error: function (data) {
                //alert("Failure!");
            }
        });
    }

    $("#manager_rec_id").val(row.cells[0].innerHTML);
    $("#manager_comment").val(row.cells[2].innerHTML);
    $('#modal-manager-recommendation').modal('show');

}

function deleteRowManagerDocoment(node) {
    var id = node.parentNode.parentNode.cells[0].innerHTML;
    $.ajax({
        url: "/card/manager-recommendation/remove?id=" + id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            //alert("Successfully Removed!");
        },
        error: function (data) {
            //alert("Error!");
        }
    });

    $(node).closest("tr").remove();
}