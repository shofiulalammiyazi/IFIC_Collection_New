
var loanReschdulingNode;
$("#tab-previous-approval-related-to-loan-rescheduling").click(function () {
    $.ajax({
        url: "/samd/customer-profile/loan-rescheduling/view?accountNo="+$('input[name="account"]').val(),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if(data.loanRescheduling.length>0){
                $('#loan_rescheduling_tBody').replaceWith("<tbody id=\"loan_rescheduling_tBody\" >\n" +
                    "\t\t\t\t\t\t\t</tbody>");

                for(var i=0;i<data.loanRescheduling.length;i++){
                    if (data.loanRescheduling[i]!=null){
                        var str="<tr>"+
                            "<td style=\"display:none;\">" + data.loanRescheduling[i].id + "</td>" +
                            "<td>" + data.loanRescheduling[i].approvalReference + "</td>" +
                            "<td>" + data.loanRescheduling[i].approvalDate + "</td>" +
                            "<td>" + data.loanRescheduling[i].approvalDetails + "</td>" +
                            "<td><a class=\"btn btn-info\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a></td>" +
                            "</tr>";
                        $('#loan_rescheduling_tBody').append(str);
                    }
                }
            }

        },
        error: function (data) {
            //alert("Failure!");
        }
    });
})

function loanReschedulingSave() {
    var formValue = $('#loan-reschedul-saveForm').serializeJSON();
    var loanAccountNo =$('input[name="account"]').val();
    formValue.loanAccountNo=loanAccountNo;
    var loanReschedulingInfo = JSON.stringify(formValue);
    loanReschedulingInfo.loanAccountNo =$('input[name="account"]').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var decision=0;
    if($("#loan-reschedul-id").val()!="")
        decision=1;

    $.ajax({
        url: '/samd/customer-profile/loan-rescheduling/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: loanReschedulingInfo,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
             alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.loanRescheduling.id + "</td>" +
                "<td>" + data.loanRescheduling.approvalReference + "</td>" +
                "<td>" + data.loanRescheduling.approvalDate + "</td>" +
                "<td>" + data.loanRescheduling.approvalDetails + "</td>" +
                "<td>" +
                "<a class=\"btn btn-info\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                "</td>" +
                "</tr>";
             $('#modal-loan-rescheduling').modal('hide');
            loanReschedulingClearData();
            if (data.loanRescheduling != null && decision==0) {
                $('#loan_rescheduling_tBody').append(str);

            } else if (data.loanRescheduling != null && decision==1){
                globalRow.cells[0].innerHTML=data.loanRescheduling.id;
                globalRow.cells[1].innerHTML=data.loanRescheduling.approvalReference;
                globalRow.cells[2].innerHTML=data.loanRescheduling.approvalDate;
                globalRow.cells[3].innerHTML=data.loanRescheduling.approvalDetails;

            }
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving  Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}


function editRow(node){

    var row=node.parentNode.parentNode;
    globalRow=row;
    loanReschdulingNode=node;
    $("#loan-reschedul-id").val(row.cells[0].innerHTML);
    $("#approval_reference").val(row.cells[1].innerHTML);
    $("#approval_date").val(row.cells[2].innerHTML);
    $("#approval_details").val(row.cells[3].innerHTML);
    $('#modal-loan-rescheduling').modal('show');
}


function loanReschedulingClearData() {
    $("#loan-reschedul-id").val('');
    $("#approval_reference").val('');
    $("#approval_date").val('');
    $("#approval_details").val('');
}