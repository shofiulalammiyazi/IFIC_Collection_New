
var latestApprovalRelatedToLoanReschedulingNode;
$("#tab-latest-approval-to-loan-rescheduling-settlement-waiver").click(function () {
    $.ajax({
        url: "/samd/latest-approval-related-to-loan-rescheduling/view?accountNo="+$('input[name="account"]').val(),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if(data.detailsOfHeadOfficeApproval.length>0){
                $('#latest_approval_related_to_loan_rescheduling_tBody').replaceWith("<tbody id=\"latest_approval_related_to_loan_rescheduling_tBody\" >\n" +
                    "\t\t\t\t\t\t\t</tbody>");

                for(var i=0;i<data.latestApprovalRelatedToLoanRescheduling.length;i++){
                    if (data.latestApprovalRelatedToLoanRescheduling[i]!=null){
                        var str="<tr>"+
                            "<td style=\"display:none;\">" + data.latestApprovalRelatedToLoanRescheduling[i].id + "</td>" +
                            "<td>" + data.latestApprovalRelatedToLoanRescheduling[i].approvalReference + "</td>" +
                            "<td>" + data.latestApprovalRelatedToLoanRescheduling[i].latestApprovalDate + "</td>" +
                            "<td>" + data.latestApprovalRelatedToLoanRescheduling[i].details + "</td>" +

                            "<td><a class=\"btn btn-info\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a></td>" +
                            "</tr>";
                        $('#latest_approval_related_to_loan_rescheduling_tBody').append(str);
                    }
                }
            }

        },
        error: function (data) {
            //alert("Failure!");
        }
    });
})

function latestApprovalRelatedToLoanReschedulingSave() {
    var formValue = $('#latestApprovalRelatedToLoanSaveForm').serializeJSON();

    var loanAccountNo =$('input[name="account"]').val();
    formValue.loanAccountNo=loanAccountNo;
    var jsonLatestApprovalRelatedToLoanRescheduling = JSON.stringify(formValue);
    jsonLatestApprovalRelatedToLoanRescheduling.loanAccountNo =$('input[name="account"]').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var decision=0;
    if($("#latest-approval-related-to-loan-rescheduling-id").val()!="")
        decision=1;

    $.ajax({
        url: '/samd/latest-approval-related-to-loan-rescheduling/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonLatestApprovalRelatedToLoanRescheduling,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.latestApprovalRelatedToLoanRescheduling.id + "</td>" +
                "<td>" + data.latestApprovalRelatedToLoanRescheduling.approvalReference + "</td>" +
                "<td>" + data.latestApprovalRelatedToLoanRescheduling.latestApprovalDate + "</td>" +
                "<td>" + data.latestApprovalRelatedToLoanRescheduling.details + "</td>" +
                "<td>" +
                "<a class=\"btn btn-info\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                "</td>" +
                "</tr>";
            $('#modal-latest-approval-related-to-loan').modal('hide');
            enableFormAndClearFormData();
            if (data.latestApprovalRelatedToLoanRescheduling != null && decision==0) {
                $('#latest_approval_related_to_loan_rescheduling_tBody').append(str);

            } else if (data.latestApprovalRelatedToLoanRescheduling != null && decision==1){
                globalRow.cells[0].innerHTML=data.latestApprovalRelatedToLoanRescheduling.id;
                globalRow.cells[1].innerHTML=data.latestApprovalRelatedToLoanRescheduling.approvalReference;
                globalRow.cells[2].innerHTML=data.latestApprovalRelatedToLoanRescheduling.latestApprovalDate;
                globalRow.cells[3].innerHTML=data.latestApprovalRelatedToLoanRescheduling.details;


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
    latestApprovalRelatedToLoanReschedulingNode=node;
    $("#latest-approval-related-to-loan-rescheduling-id").val(row.cells[0].innerHTML);
    $("#approvalReference").val(row.cells[1].innerHTML);
    $("#latestApprovalDate").val(row.cells[2].innerHTML);
    $("#details").val(row.cells[3].innerHTML);

    $('#modal-latest-approval-related-to-loan').modal('show');
}


function enableFormAndClearFormData() {
    $("#latestApprovalRelatedToLoanSaveForm").find(':input').attr("disabled", false);
    $("#latest-approval-related-to-loan-rescheduling-save-btn").show();

    $("#latest-approval-related-to-loan-rescheduling-id").val('');
    $("#approvalReference").val('');
    $("#latestApprovalDate").val('');
    $("#details").val('');
}