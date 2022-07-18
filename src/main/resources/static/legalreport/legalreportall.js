// function branchwisecase() {
//
//     var token = $("meta[name='_csrf']").attr("content");
//     var header = $("meta[name='_csrf_header']").attr("content");
// alert("click")
//
//     $.ajax({
//         type:"get",
//         url:"/legal-report/branch-wise-court-case",
//         beforeSend: function (xhr) {
//             xhr.setRequestHeader(header, token);
//         },
//         data:{
//             branch_code:$("#branch_code_branch_wise_cortcase").val()
//         },
//         success:function (data) {
//
//         }
//     })
// }


function reportDownload() {
// console.log($("#branch_code_branch_wise_cortcase").val());
     var token = $("meta[name='_csrf']").attr("content");
     var header = $("meta[name='_csrf_header']").attr("content");
//     $.ajax({
//         cache: false,
//         type: 'GET',
//         url: '/legal-report/branch-wise',
//         contentType: false,
//          processData: false,
//         data: {
//             branch_code:$("#branch_code_branch_wise_cortcase").val()
//     },
//         // beforeSend: function (xhr) {
//         //     xhr.setRequestHeader(header, token);
//         // },
//
//         //xhrFields is what did the trick to read the blob to pdf
//         xhrFields: {
//             responseType: 'blob'
//         },
//
//         success: function (response, status, xhr) {
//
//             var filename = "";
//             var disposition = xhr.getResponseHeader('Content-Disposition');
//
//             if (disposition) {
//                 var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
//                 var matches = filenameRegex.exec(disposition);
//                 if (matches !== null && matches[1]) filename = matches[1].replace(/['"]/g, '');
//             }
//             var linkelem = document.createElement('a');
//             try {
//                 var blob = new Blob([response], { type: 'application/octet-stream' });
//
//                 if (typeof window.navigator.msSaveBlob !== 'undefined') {
//                     //   IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
//                     window.navigator.msSaveBlob(blob, filename);
//                 } else {
//                     var URL = window.URL || window.webkitURL;
//                     var downloadUrl = URL.createObjectURL(blob);
//
//                     if (filename) {
//                         // use HTML5 a[download] attribute to specify filename
//                         var a = document.createElement("a");
//
//                         // safari doesn't support this yet
//                         if (typeof a.download === 'undefined') {
//                             window.location = downloadUrl;
//                         } else {
//                             a.href = downloadUrl;
//                             a.download = filename;
//                             document.body.appendChild(a);
//                             a.target = "_blank";
//                             a.click();
//                         }
//                     } else {
//                         window.location = downloadUrl;
//                     }
//                 }
//
//             } catch (ex) {
//                 console.log(ex);
//             }
//         }
//     });
//     var branchcode=$("#branch_code_branch_wise_cortcase").val();
//     fetch('/legal-report/branch-wise', {
//
//         body: 'branch_code='+branchcode,
//         method: 'POST',
//         headers: {
//             'X-CSRF-TOKEN': window.csrfToken,
//             'Content-Type': 'application/json; charset=utf-8',
//
//         },
//     })
//         .then(response => response.blob())
// .then(response => {
//         const blob = new Blob([response], {type: 'application/pdf'});
//     const downloadUrl = URL.createObjectURL(blob);
//     const a = document.createElement("a");
//     a.href = downloadUrl;
//     a.download = "file.pdf";
//     document.body.appendChild(a);
//     a.target = "_blank";
//     a.click();
// })



    alert("data");
    $.ajax({

        dataType: 'native',
        type: 'GET',
        url: '/legal-report/branch-wise',
         // contentType: false,
         // processData: false,
        data: {
            branch_code:$("#branch_code_branch_wise_cortcase").val()
    },

        xhrFields: {
            responseType: 'blob'
        },
        success: function(blob){
            console.log(blob.size);
            var link=document.createElement('a');
            link.href=window.URL.createObjectURL(blob);
            link.download="Dossier_" + new Date() + ".pdf";
            // link.target = "_blank";
            link.click();
        }
    });
}
