/*
function docUploadInfo() {

    var formValues = $('#cardBorrowerDocUploadForm').serializeJSON();
    var cardPersonalInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var values=[];
    var docs=[];
    var table=document.getElementById("borowerTable");

    for (var r=1;r<table.rows.length;r++){
        var values1=[];
        for(var c=0;c<table.rows[r].cells.length;c++){
            var element=table.rows[r].cells[c];
            console.log(element);
            if(c==0){

                values1.push(""+element.innerHTML+"");

            }
            else if (c==1) {
                values1.push(""+element.childNodes[0].value+"");
                var blob=document.getElementById("incomeTypeFile");
                var blobb=blob.files;
                values1.push(blobb);
                docs.push(blobb);
            }
        }

        values.push(values1);
    }

    console.log(values);

    //values.push(cardPersonalInfoFormData);

    var data=new FormData();
     data.append("list",values);
     data.append("formData",cardPersonalInfoFormData);
    //data.append("list", docs[0]);


    console.log("form data: " +JSON.stringify(data.get("list")));

    console.log(cardPersonalInfoFormData);
    $.ajax({
        url: '/card/document-check-list/save-borrower-doc',
        type: 'POST',
        contentType: false,
        //dataType: 'json',
        enctype: "multipart/form-data",
        processData: false,
        data: data,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //alert("Success!"+data.successMsg);
            console.log("returning from controller: "+JSON.stringify(data.doc));
        },
        error: function (err) {
            //alert("Failure!"+err);
        }
    });
}

$('#card-borrower-doc-submit-btn').click(function(event) {
    event.preventDefault();
    docUploadInfo();
});

*/
