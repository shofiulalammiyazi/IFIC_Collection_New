
var detailsOfHeadOfficeApprovalNode;
$("#tab-details-of-head-office-approval").click(function () {
    $.ajax({
        url: "/samd/details-of-head-office-approval/view?accountNo="+$('input[name="account"]').val(),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if(data.detailsOfHeadOfficeApproval.length>0){
                $('#details_of_head_office_approval_tBody').replaceWith("<tbody id=\"details_of_head_office_approval_tBody\" >\n" +
                    "\t\t\t\t\t\t\t</tbody>");

                for(var i=0;i<data.detailsOfHeadOfficeApproval.length;i++){
                    if (data.detailsOfHeadOfficeApproval[i]!=null){
                        var str="<tr>"+
                            "<td style=\"display:none;\">" + data.detailsOfHeadOfficeApproval[i].id + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].sanctionDetails + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].sl + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].sanctionNo + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].detailsDate + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].approvalAuthority + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].facilityType + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].limitTaka + "</td>" +
                            "<td>" + data.detailsOfHeadOfficeApproval[i].validity + "</td>" +
                            "<td><a class=\"btn btn-info\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a></td>" +
                            "</tr>";
                        $('#details_of_head_office_approval_tBody').append(str);
                    }
                }
            }

        },
        error: function (data) {
            //alert("Failure!");
        }
    });
})

function detailsOfHeadOfficeApprovalSave() {
    var formValue = $('#detailsOfHeadOfficeApprovalSaveForm').serializeJSON();

    var loanAccountNo =$('input[name="account"]').val();
    formValue.loanAccountNo=loanAccountNo;
    var jsonDetailsOfHeadOfficeApproval = JSON.stringify(formValue);
    jsonDetailsOfHeadOfficeApproval.loanAccountNo =$('input[name="account"]').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var decision=0;
    if($("#details-of-head-office-approval-id").val()!="")
        decision=1;

    $.ajax({
        url: '/samd/details-of-head-office-approval/save',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonDetailsOfHeadOfficeApproval,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.successMsg);
            var str = "<tr>" +
                "<td style=\"display:none;\">" + data.detailsOfHeadOfficeApproval.id + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.sanctionDetails + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.sl + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.sanctionNo + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.detailsDate + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.approvalAuthority + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.facilityType + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.limitTaka + "</td>" +
                "<td>" + data.detailsOfHeadOfficeApproval.validity + "</td>" +
               /* "<td>" + data.detailsOfHeadOfficeApproval.dealerPin + "</td>" +*/
                "<td>" +
                "<a class=\"btn btn-info\" onclick=\"editRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                "</td>" +
                "</tr>";
            $('#modal-details-of-head-office-approval').modal('hide');
            enableFormAndClearFormData();
            if (data.detailsOfHeadOfficeApproval != null && decision==0) {
                $('#details_of_head_office_approval_tBody').append(str);

            } else if (data.detailsOfHeadOfficeApproval != null && decision==1){
                globalRow.cells[0].innerHTML=data.detailsOfHeadOfficeApproval.id;
                globalRow.cells[1].innerHTML=data.detailsOfHeadOfficeApproval.sanctionDetails;
                globalRow.cells[2].innerHTML=data.detailsOfHeadOfficeApproval.sl;
                globalRow.cells[3].innerHTML=data.detailsOfHeadOfficeApproval.sanctionNo;
                globalRow.cells[4].innerHTML=data.detailsOfHeadOfficeApproval.detailsDate;
                globalRow.cells[5].innerHTML=data.detailsOfHeadOfficeApproval.approvalAuthority;
                globalRow.cells[6].innerHTML=data.detailsOfHeadOfficeApproval.facilityType;
                globalRow.cells[7].innerHTML=data.detailsOfHeadOfficeApproval.limitTaka;
                globalRow.cells[8].innerHTML=data.detailsOfHeadOfficeApproval.validity;
               /* globalRow.cells[9].innerHTML=data.detailsOfHeadOfficeApproval.dealerPin;*/


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
    detailsOfHeadOfficeApprovalNode=node;
    $("#details-of-head-office-approval-id").val(row.cells[0].innerHTML);
    $("#sanctionDetails").val(row.cells[1].innerHTML);
    $("#sl").val(row.cells[2].innerHTML);
    $("#sanctionNo").val(row.cells[3].innerHTML);
    $("#detailsDate").val(row.cells[4].innerHTML);
    $("#approvalAuthority").val(row.cells[5].innerHTML);
    $("#facilityType").val(row.cells[6].innerHTML);
    $("#limitTaka").val(row.cells[7].innerHTML);
    $(".validity").val(row.cells[8].innerHTML);

    $('#modal-details-of-head-office-approval').modal('show');
}


function enableFormAndClearFormData() {
    $("#detailsOfHeadOfficeApprovalSaveForm").find(':input').attr("disabled", false);
    $("#details-of-head-office-approval-save-btn").show();

    $("#details-of-head-office-approval-id").val('');
    $("#sanctionDetails").val('');
    $("#sl").val('');
    $("#santionNo").val('');
    $("#detailsDate").val('');
    $("#approvalAuthority").val('');
    $("#facilityType").val('');
    $("#limitTaka").val('');
    $("#validity").val('');
}