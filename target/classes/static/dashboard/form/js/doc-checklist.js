/*
function dockList(){
    var velka={
        cid : $("cdi").val()
    };

    console.log($("cdi").val());

    $.ajax({
        type: "GET",
        url: "/card/document-check-list/list",
        data: velka,
        success : function (data) {
            //alert("Successful!!!"+data.docNameList.length);
            console.log(data.docNameList[0]);
            console.log(data.docNameList[1]);


            /!*for (var i=0;i<data.docNameList.length;i++){
                var str="<tr>"+
                "<td>" + data.docNameList[i] + "</td>"+
                    "<td>"+"<span><i class=\"fa fa-upload\" style=\"margin-right:5px\"></i>Choose File</span>"+
                    "<input id=\"incomeTypeFile\" name=\"incomeTypeFile\" type=\"file\" class=\"custom-form-control upload ufile\" placeholder=\"File\"></input>"+
                    "</td>"+
                "</tr>";
                $('#borrowerDocTbody').append(str);
            }*!/

           for (var i=0;i<data.docNameList.length;i++){
               var tableRow=document.getElementById("borowerTable").insertRow();
               var cell=tableRow.insertCell();
               cell.innerHTML=data.docNameList[i];

               cell=tableRow.insertCell();
               cell.innerHTML="<input id=\"incomeTypeFile\" name=\"incomeTypeFile\" type=\"file\" class=\"custom-form-control upload ufile\" placeholder=\"File\"></input>";

               cell=tableRow.insertCell();
               cell.innerHTML="<input type=\"button\" value=\"Delete\" onclick=\"removeRow(this);\" />"
           }
        },
        error : function (data) {
            //alert("Failure!!!"+data);
        }
    })
}

function removeRow(src){
    var rowNum=src.parentElement.parentElement.rowIndex;
    document.getElementById("borowerTable").deleteRow(rowNum);
}


$('#tab_doc_checklist').click(function (event) {
   event.preventDefault();
    console.log("Hello World");
    dockList();
});*/
