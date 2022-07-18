var hasFile = 0;
function preview(tabNo, i) {
    var fileType = $("#ufile_" + tabNo + "_" + i)[0].files[0].type;
    var a = fileType.match(/image\//);


    if (fileType.match(/image\//) == "image/") {
        var thumbnail = '<div id="dv_' + tabNo + '_' + i + '" class="thumbnail"> <img style="width:100%" src="' +
            URL.createObjectURL($("#ufile_" + tabNo + "_" + i)[0].files[0]) + '" /> <a href="#" onclick="removeFile(' + tabNo + ',' + i + ');">' +
            '<i class="fa fa-trash"></i></a> </div>';
        $("#td_" + tabNo + "_" + i).append(thumbnail);

    } else if (fileType.match(/\/\pdf/) == "\/pdf") {
        var thumbnail = '<div id="dv_' + tabNo + '_' + i + '" class="thumbnail"> <iframe style="width:100%" src="' +
            URL.createObjectURL($("#ufile_" + tabNo + "_" + i)[0].files[0]) + '" /> <a href="#" onclick="removeFile(' + tabNo + ',' + i + ');">' +
            '<i class="fa fa-trash"></i></a> </div>';
        $("#td_" + tabNo + "_" + i).append(thumbnail);
    }
    hasFile =hasFile+1;

}

function removeFile(tabNo, i) {
    $("#ufile_" + tabNo + "_" + i).val('');
    $("#dv_" + tabNo + "_" + i).remove();
    hasFile=hasFile-1;

}

function uploadfile(tabNo) {
    $("#spinner").modal(
        {
            toggle: "toggle",
            backdrop: "static",
            keyboard: false
        });
    $("#specialAlert").removeClass("errorDiv");
    $("#specialAlert").text("");
    var documentsIndex;
    var isDisbursement = 0;

    if (tabNo == 100) {
        documentsIndex = new Array();
        var j = 0;
        $("#cardDocuments :input").each(function () {
            documentsIndex[j] = $(this).val();
            j++;
        });
    } else if (tabNo == '0') {

        documentsIndex = new Array();
        var j = 0;
        $("#disbursementDocuments :input").each(function () {
            documentsIndex[j] = $(this).val();
            j++;
        });
    }
    else{
        console.log("ELSEE condition");
    }

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var cardId=$("#cardId").val();
    var docChkListName=$("#docName")
    var formData = new FormData();
    formData.append('isDisbursement', isDisbursement);
    formData.append('cardId',cardId);

    var selectRequired=true;

           for (var i = 0; i < documentsIndex.length; i++) {

               var file = $("#ufile_" + tabNo + "_" + documentsIndex[i])[0].files[0];
               var fileName = $("#document_" + tabNo + "_" + documentsIndex[i]).val();
               var isrequirdId=$("#requiredId"+documentsIndex[i]).val();
               console.log("isrequirdId:"+isrequirdId+" file:"+$("#ufile_" + tabNo + "_" + documentsIndex[i]).val());
               if(isrequirdId=="Yes" && file==null ){
                   selectRequired=false;
               }
            if($("#ufile_" + tabNo + "_" + documentsIndex[i]).val() != '')
              formData.append("file", file, fileName);
           }


    if(selectRequired==true){
        $.ajax({
            url: '/card/document/uploadfile/create',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(
                    header, token);
            },
            data: formData,
            processData: false,
            contentType: false,
            type: 'POST',
            success: function (data) {
                $("#spinner").modal('hide');
                if(hasFile != 0){
                    $("#specialAlert").text("File Successfully Uploaded");
                }
                else if(hasFile == 0){
                    $("#specialAlert").text("Please Added Atlest One File");
                }
                $("#exampleModalLong").modal("toggle");
            },
            error: function (err) {
                $("#spinner").modal('hide');
                var message = "<ul style='list-style-type:disc'><li>Upload Error</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);

            }
        });
    }
    else{
    }

}

function showReportPreview(previewUrl, fileType) {
    $("#showPreviewReport").attr("src", "/card/document/uploadfile/document?dId=" + previewUrl + "&type=" + fileType + "#toolbar=0&navpanes=0");
}

function showReportPreview2(previewUrl, fileType) {
      $("#iframe").attr("src", "/card/document/uploadfile/document?dId=" + previewUrl + "&type=" + fileType + "#toolbar=0&navpanes=0");
      $("#idiv").show();
}

function iframeContent() {
    $("iframe").contents().find("img").css("max-width","95%");
}






