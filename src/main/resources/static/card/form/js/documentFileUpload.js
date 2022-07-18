
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

            if (tabNo == 110) {
                documentsIndex = new Array();
                var j = 0;
                $("#loanDocuments :input").each(function () {
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
            }
            

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var loanID = $('#LoanId').val();
            var customerId = $('#customerId' + tabNo).val();
            var formData = new FormData();
            
/*              file=$("#ufile_100_0")[0].files[0];
             // var fileName = $("#document_100").val();
              var fileName = $("#document_" + tabNo + "_" + documentsIndex[0]).val();
             // filename= $("#ufile_100_0").val();
             // console.log("hello="+ fileName);
              console.log("here");
              formData.append("file", file, fileName);*/
            for (var i = 0; i < documentsIndex.length; i++) {
            	 file=$("#ufile_100_0")[i].files[i];
            	//var file = $("#ufile_" + tabNo + "_" + documentsIndex[i])[0].files[0];
                var fileName = $("#document_" + tabNo + "_" + documentsIndex[i]).val();
                formData.append("file", file, fileName);
            }
            

            formData.append('loanID', loanID);
            formData.append('isDisbursement', isDisbursement);
            tabNo = parseInt(tabNo);
            
            cardId=$("#cardId").val();
            formData.append('cardId',cardId);
          
            if (tabNo > 0) {
                formData.append('customerId', customerId); 
            }

//            for (var [key, value] of formData.entries()) {
//            }
            $.ajax({
                url: '/uploadfile/create',
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


        //]]>


