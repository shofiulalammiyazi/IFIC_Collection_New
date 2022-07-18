// $("#tab-visitledger").click(function (event) {
//     getVistiLedgerData(accountNo);
// });
//
// $('#visitLedgerModal').on('show.bs.modal',function () {
//     $('input[name="accountCardNumber"]').val(accountNo)
// })
//
// function getVistiLedgerData(accountNo) {
//
//     $.ajax({
//         url: "/collection/visitledger/list1?accountNo="+accountNo,
//         type: "GET",
//         contentType: "application/json; charset=utf-8",
//         dataType: "json",
//         success: function (data) {
//
//             $('#loan_visitledger_tBody').html("");
//
//             console.log('+++++++++++++++++++++++++++'+ data.length)
//             for (i = 0; i < data.length; i++) {
//                 console.log('================================='+ data[i])
//                 if (data[i] != null) {
//                     var str = "<tr>" +
//                         // "<td style=\"display:none;\">" + data[i].id + "</td>" +
//                         "<td>" + data[i].username + "-" + data[i].firstName + "</td>" +
//                         "<td>" + formatDate_DD_MMM_YYYY(data[i].dateOfVisit) + "</td>" +
//                         "<td >" + (data[i].visitTime != null ? data[i].visitTime:'') + "</td>" +
//                         "<td >" + (data[i].location == null ? '' : data[i].location) + "</td>" +
//                         "<td >" + (data[i].status == null ? '' : data[i].status) + "</td>" +
//                         //"<td style='display: none;width: 0px;'>" + data[i].remark + "</td>"
//                          "<td class='text-center'>" +
//                         "<a class=\"btn btn-xs btn-info\" onclick=\"vl_editRow("+data[i].id+")\"> <i class=\"fa fa-edit\"></i></a>" +
//                         "<a class=\"btn btn-xs btn-info\" style=\"margin-left: 5px\" onclick=\"viewVisitLedgerRemark("+data[i].id+")\"> <i class=\"fa fa-eye\"></i></a>" +
//                         "</td>";
//                     console.log('visit ledger list in loop');
//
//                     $('#loan_visitledger_tBody').append(str);
//                 }
//             }
//         },
//         error: function (data) {
//             console.log(data);
//         }
//     });
//
// }
//
//
// function addVLLoan() {
//     var data = $('#vl_loan').serialize();
//
//     var jsonFormData = JSON.stringify(data);
//     console.log('<----------- time ------------->');
//     console.log(jsonFormData.visitTime);
//     var token = $("meta[name='_csrf']").attr("content");
//     var header = $("meta[name='_csrf_header']").attr("content");
//
//
//     $.ajax({
//         url: '/collection/visitledger/create/visit-ledger',
//         type: 'POST',
//         data: jsonFormData,
//         beforeSend: function (xhr) {
//             xhr.setRequestHeader(header, token);
//         },
//         success: function (data) {
//             $('#visitLedgerModal').modal('hide');
//             $("#vl_loan")[0].reset();
//             console.log('ajex submit: '+data.accountCardNumber);
//             getVistiLedgerData(data.accountCardNumber);
//         },
//         error: function (err) {
//             console.log(err);
//         }
//
//     });
//     return false;
// }
//
//
//
// function vl_editRow(id) {
//
//     $.ajax({
//         url: '/collection/visitledger/find?id='+id,
//         success: function (response) {
//             var formdatalist = $('#vl_loan');
//             formdatalist.find('input[name="id"]').val(response.id);
//             formdatalist.find('input[name="accountCardNumber"]').val(response.accountCardNumber);
//             formdatalist.find('input[name="productUnit"]').val(response.productUnit);
//             formdatalist.find('input[name="dateOfVisit"]').val(formatDateInput(response.dateOfVisit));
//             formdatalist.find('input[name="visitTime"]').val(response.visitTime);
//             formdatalist.find('input[name="location"]').val(response.location);
//             formdatalist.find('textarea[name="remark"]').val(response.remark);
//             $('#visitLedgerModal').modal('show');
//         },
//         error: function (response) {
//             console.log(response);
//         }
//     })
// }
//
//
// function viewVisitLedgerRemark(id) {
//     $.ajax({
//         url: '/collection/visitledger/find/visit-ledger?id='+id,
//         success: function (response) {
//             $('#supervisor-visitledger-remark').val(response.remark);
//             $('#modal-supervisor-visit-ledger-view').modal('show');
//         },
//         error: function (response) {
//             console.log(response);
//         }
//     })
//
// }
//
// // Date Conversion Code Snippet
// function convertDate(date) {
//
//     var vd = new Date(date);
//     var vd_day = vd.getDay();
//     if (vd_day <= 9) {
//         vd_day = "0" + vd.getDay();
//     }
//     var vd_month = vd.getMonth();
//     if (vd_month <= 9) {
//         vd_month = "0" + vd.getMonth();
//     }
//     return vd_day + "-" + vd_month + "-" + vd.getFullYear();
//
//
// }
//
//
// $('#visit_ledger_loan_add').click(function () {
//     $("#vl_loan")[0].reset();
// });
//
//  function formatDateInput(date) {
//     let date1 = date ? new Date(date) : new Date()
//
//     return date ? date1.getFullYear()
//         +'-'+((date1.getMonth()+1) < 10 ? '0'+(date1.getMonth()+1) : (date1.getMonth()+1))
//         +'-'+(date1.getDate() < 10 ? '0'+date1.getDate() : date1.getDate())
//         : '';
// }
//
//  function formatDate(date) {
//     return formatDate_DD_MMM_YYYY(date)
// }