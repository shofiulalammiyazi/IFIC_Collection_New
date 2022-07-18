
function addCreditAssement() {

    var decision=0;
    if($("#cerditAccId").val()!="") decision=1;
    var formValues = $('#creditAssementForm').serialize();
   // var jsoncreditAssementFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/credit/creditassessment/save',
        type: 'POST',
       /* contentType: 'application/json; charset=utf-8',
        dataType: 'json',*/
        data: formValues,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {

            //$('#modal-credit-assessment').modal('hide');
            clearCrdAsmnt();
            if (data.creditAssessment != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.creditAssessment.cerditAccId + "</td>" +
                    "<td style=\"display:none;\">" + data.creditAssessment.cardId + "</td>" +
                    "<td>" + data.creditAssessment.totalincomeC + "</td>" +
                    "<td>" + data.creditAssessment.totalExpenseC + "</td>" +
                    "<td>" + data.creditAssessment.totalLiabilityC + "</td>" +
                    "<td>" + data.creditAssessment.netIncomeC + "</td>" +
                    "<td>" + data.creditAssessment.maxAminPaymnet + "</td>" +
                    "<td style=\"display:none;\">" + data.creditAssessment.localRecommendedlimitC + "</td>" +
                    "<td style=\"display:none;\">" + data.creditAssessment.usdRecommendedlimitC + "</td>" +
                    "<td style=\"display:none;\">" + data.creditAssessment.totalRecommendedlimitC + "</td>" +
                    "<td>" + data.creditAssessment.minimumProposedLimit + "</td>" +
                    "<td>" + data.creditAssessment.dbrCal + "</td>" +
                    "<td><a  onclick=\"editCrdAsmntRow(this)\"><i class=\"fa fa-edit\"></i></a>\n" +
                    "<a onclick=\"deleteCrdAsmRow(this)\"> <i class=\"fa fa-trash\"></i></a></td>" +
                    "<td><a class=\"btn btn-xs\" onclick=\"CrdAsmntViewRow(this)\"><i class=\"fa fa-view\"></i>View</a>\n"+
                    "</tr>";
                $('#creditAssemen_tBody').append(str);
                $("#card_recommended_amount").text(data.creditAssessment.totalRecommendedlimitC.toFixed(2));
                $("#card_recommended_amount_div").show();
            }
            else if (data.creditAssessment != null && decision==1){
                globalRow.cells[0].innerHTML=data.creditAssessment.cerditAccId;
                globalRow.cells[1].innerHTML=data.creditAssessment.cardId;
                globalRow.cells[2].innerHTML=data.creditAssessment.totalincomeC;
                globalRow.cells[3].innerHTML=data.creditAssessment.totalExpenseC;
                globalRow.cells[4].innerHTML=data.creditAssessment.totalLiabilityC;
                globalRow.cells[5].innerHTML=data.creditAssessment.netIncomeC;
                globalRow.cells[6].innerHTML=data.creditAssessment.maxAminPaymnet;
                globalRow.cells[7].innerHTML=data.creditAssessment.localRecommendedlimitC;
                globalRow.cells[8].innerHTML=data.creditAssessment.totalRecommendedlimitC;
                globalRow.cells[9].innerHTML=data.creditAssessment.totalRecommendedlimitC;
                globalRow.cells[10].innerHTML=data.creditAssessment.minimumProposedLimit;
                globalRow.cells[10].innerHTML=data.creditAssessment.dbrCal;
            }
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Credit-Assessment info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
        }
    });
    $("#addcredit").hide();
    //$("#creditAsseFooter").hide();
}

$('#creditAsmntSaveBtn').click(function(event) {
    event.preventDefault();
    addCreditAssement();
});

$('#creditAsmntCloseBtn').click(function(event) {
    clearCrdAsmnt();
    event.preventDefault();
    $("#addcredit").hide();
    //$("#creditAsseFooter").hide();
    //$('#modal-credit-assessment').modal('hide');
});

function clearCrdAsmnt() {

    $("#cerditAccId").val("");
    $("#cardId").val("");
    $("#totalincomeC").val("");
    $("#totalExpenseC").val("");
    $("#totalLiabilityC").val("");
    $("#netIncomeC").val("");
    $("#maxAminPaymnet").val("");
    $("#localRecommendedlimitC").val("");
    $("#UsdRecommendedlimitC").val("");
    $("#TotalRecommendedlimitC").val("");
    $("#minimumProposedLimit").val("");
    $("#dbrCal").val("");



}

function editCrdAsmntRow(node) {
    var row = node.parentNode.parentNode;
    globalRow = row;
    $("#cerditAccId").val(row.cells[0].innerHTML);
    $("#cardId").val(row.cells[1].innerHTML);
    $("#totalincomeC").val(row.cells[2].innerHTML);
    $("#totalExpenseC").val(row.cells[3].innerHTML);
    $("#totalLiabilityC").val(row.cells[4].innerHTML);
    $("#netIncomeC").val(row.cells[4].innerHTML);
    $("#maxAminPaymnet").val(row.cells[6].innerHTML);
    $("#localRecommendedlimitC").val(row.cells[7].innerHTML);
    $("#UsdRecommendedlimitC").val(row.cells[8].innerHTML);
    $("#TotalRecommendedlimitC").val(row.cells[9].innerHTML);
    $("#minimumProposedLimit").val(row.cells[10].innerHTML);
    $("#dbrCal").val(row.cells[11].innerHTML);
    $('#addcredit').modal('show');
}



$('.cpQuery').change(function(event, number) {
    this.val = $(this).val();
    if (this.val === '0') {
        $(this).siblings('div.cpComments').hide();
    } else {

        $(this).siblings('div.cpComments').show();
        CKEDITOR.replace($(this).data('commentquery'));
    }

});

$(document).ready(function() {

    $("#tab-creditAssessment").click(function() {
        var id=$("#cardId").val();
        $.ajax({
            url: "/credit/creditassessment/list?id="+id,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                $('#creditAssemen_tBody').replaceWith("<tbody id=\"creditAssemen_tBody\" >\n" +
                    "\t\t\t\t\t\t\t</tbody>");
                
                for(var i=0;i<data.creditAssessmentList.length;i++){
                    if (data.creditAssessmentList[i]!=null){
                        var str="<tr>"+
                            "<td style=\"display:none;\">" + data.creditAssessmentList[i].cerditAccId + "</td>" +
                            "<td style=\"display:none;\">" + data.creditAssessmentList[i].cardId + "</td>" +
                            "<td>" + data.creditAssessmentList[i].totalincomeC + "</td>" +
                            "<td>" + data.creditAssessmentList[i].totalExpenseC + "</td>" +
                            "<td>" + data.creditAssessmentList[i].totalLiabilityC + "</td>" +
                            "<td>" + data.creditAssessmentList[i].netIncomeC + "</td>" +
                            "<td>" + data.creditAssessmentList[i].maxAminPaymnet + "</td>" +
                            "<td style=\"display:none;\">" + data.creditAssessmentList[i].localRecommendedlimitC + "</td>" +
                            "<td style=\"display:none;\">" + data.creditAssessmentList[i].usdRecommendedlimitC + "</td>" +
                            "<td style=\"display:none;\">" + data.creditAssessmentList[i].totalRecommendedlimitC + "</td>" +
                            "<td>" + data.creditAssessmentList[i].minimumProposedLimit + "</td>" +
                            "<td>" + data.creditAssessmentList[i].dbrCal + "</td>" +
                            "<td><a  onclick=\"editCrdAsmntRow(this)\"><i class=\"fa fa-edit\"></i></a>\n" +
                            "<a onclick=\"deleteCrdAsmRow(this)\"> <i class=\"fa fa-trash\"></i></a></td>" +
                            "<td><a class=\"btn btn-xs\" onclick=\"CrdAsmntViewRow(this)\"><i class=\"fa fa-view\"></i>View</a>\n"+
                            "</tr>";
                        $('#creditAssemen_tBody').append(str);

                    }
                }
                $("#card_recommended_amount").text($('#creditAssemen_tBody tr:last-child ').find('td:eq(9)').html());
                $("#card_recommended_amount_div").show();

            },
            error: function (data) {
                //alert("Failure!");
            }
        });


        $('#totalMonthlyIncome').html("0");
        $('#totalMonthlyExpenditure').html("0");
        $('#totalMonthlyLiability').html("0")

        var dt={
            name : "name",
            cardId : $("#cardId").val()
        };

        $.ajax({
            url: '/card/financial/credit/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
                $("#creditFacility_tBody").replaceWith("<tbody id=\"creditFacility_tBody\" >\n" +
                    "\t\t\t\t\t\t</tbody>");

                for (var i=0;i<data.creditIncomeList.length;i++){
                    if (data.creditIncomeList[i]!=null){
                        var str = "<tr>" +
                            "<td style=\"display:none;\">" + data.creditIncomeList[i].cfId + "</td>" +
                            "<td style=\"display:none;\">" + data.creditIncomeList[i].customerId + "</td>" +
                            "<td style=\"display:none;\">" + data.creditIncomeList[i].cardId + "</td>" +

                            "<td>" + data.creditIncomeList[i].facilityType + "</td>" +
                            "<td>" + data.creditIncomeList[i].branchName + "</td>" +
                            "<td>" + data.creditIncomeList[i].cardNo + "</td>" +
                            "<td>" + data.creditIncomeList[i].loanAccNo + "</td>" +
                            "<td>" + data.creditIncomeList[i].sanctionLimit + "</td>" +
                            "<td>" + data.creditIncomeList[i].obligation + "</td>" +
                            "<td>" + data.creditIncomeList[i].presentOutstanding1 + "</td>" +

                            /*"<td>" + data.creditIncomeList[i].bankName1 + "</td>" +*/

                            "<td style=\"display:none;\">" + data.creditIncomeList[i].bankName1 + "</td>" +
                            "<td style=\"display:none;\">" + data.creditIncomeList[i].facilityDate + "</td>" +
                            "<td style=\"display:none;\">" + data.creditIncomeList[i].expiryDate + "</td>" +
                            "<td style=\"display:none;\">" + data.creditIncomeList[i].securityColl + "</td>" +
                            "<td><a class=\"btn btn-xs\" onclick=\"viewCreditRow(this)\"><i class=\"fa fa-view\"></i>View</a>\n" +
                            "</tr>";
                        $('#creditFacility_tBody').append(str);
                    }
                }
            },
            error: function () {
                ////alert("Error");
            }
        });

        $.ajax({
            url: '/card/financial/income/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
                $('#monthlyIncomeTable_tBody').replaceWith("<tbody id=\"monthlyIncomeTable_tBody\"></tbody>");
                for (var i=0;i<data.monthlyIncomeList.length;i++){
                    if (data.monthlyIncomeList[i]!=null){
                        var str = "<tr>" +
                            "<td style=\"display:none;\">" + data.monthlyIncomeList[i].incomeId + "</td>" +
                            "<td style=\"display:none;\">" + data.monthlyIncomeList[i].customerId + "</td>" +
                            "<td style=\"display:none;\">" + data.monthlyIncomeList[i].cardId + "</td>" +

                            "<td>" + data.monthlyIncomeList[i].incomeTypeId + "</td>" +
                            "<td>" + data.monthlyIncomeList[i].declaredIncome + "</td>" +
                            "<td><a class=\"btn btn-xs\" onclick=\"viewIncomeRow(this)\"><i class=\"fa fa-view\"></i>View</a>\n" +

                            "</tr>";
                        $('#monthlyIncomeTable_tBody').append(str);
                    }

                }
                var sum=Number($('#totalMonthlyIncome').html());
                var x=0;
                for (var i=0;i<data.monthlyIncomeList.length;i++){
                    x=data.monthlyIncomeList[i].declaredIncome;
                    sum+=Number(x);
                }
                $('#totalMonthlyIncome').html(sum);
            },
            error: function () {
                ////alert("Error");
            }
        });

        $.ajax({
            url: '/card/financial/expenditure/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
                $('#monthlyExpenditureTable_tBody').replaceWith("<tbody id=\"monthlyExpenditureTable_tBody\"></tbody>");

                for (var i=0;i<data.monthlyExpList.length;i++){
                    if (data.monthlyExpList[i]!=null){
                        var str = "<tr>" +
                            "<td style=\"display:none;\">" + data.monthlyExpList[i].expdId + "</td>" +
                            "<td style=\"display:none;\">" + data.monthlyExpList[i].customerId + "</td>" +
                            "<td style=\"display:none;\">" + data.monthlyExpList[i].cardId + "</td>" +

                            "<td>" + data.monthlyExpList[i].expenditureType + "</td>" +
                            "<td>" + data.monthlyExpList[i].declaredExpenditure + "</td>" +
                            "<td><a class=\"btn btn-xs\" onclick=\"viewExpenditureRow(this)\"><i class=\"fa fa-view\"></i>View</a>\n" +

                            "</tr>";
                        $('#monthlyExpenditureTable_tBody').append(str);
                    }
                }
                var sum=Number($('#totalMonthlyExpenditure').html());
                var x=0;
                for (var i=0;i<data.monthlyExpList.length;i++){
                    x=data.monthlyExpList[i].declaredExpenditure;
                    sum+=Number(x);
                }
                $('#totalMonthlyExpenditure').html(sum);
            },
            error: function () {
                ////alert("Error");
            }
        });


       $.ajax({
            url: "/card/financial/liability/preview",
            type: "GET",
            data: dt,
            success: function (data) {
                $('#monthlyLiabilityTable_tBody').replaceWith("<tbody id=\"monthlyLiabilityTable_tBody\"></tbody>");
                for (var i=0;i<data.monthlyLiabilityList.length;i++){
                    if (data.monthlyLiabilityList[i]!=null){
                        var str = "<tr>" +
                            "<td style=\"display:none;\">" + data.monthlyLiabilityList[i].liabilityId + "</td>" +
                            "<td style=\"display:none;\">" + data.monthlyLiabilityList[i].customerId + "</td>" +
                            "<td style=\"display:none;\">" + data.monthlyLiabilityList[i].cardId + "</td>" +

                            "<td>" + data.monthlyLiabilityList[i].liabilityType + "</td>" +
                            "<td>" + data.monthlyLiabilityList[i].declaredLiability + "</td>" +
                            "<td><a class=\"btn btn-xs\" onclick=\"viewLiabilityRow(this)\"><i class=\"fa fa-view\"></i>View</a>\n" +
                            "</tr>";
                        $('#monthlyLiabilityTable_tBody').append(str);
                    }
                }

                var sum=Number($('#totalMonthlyLiability').html());
                var x=0;
                for (var i=0;i<data.monthlyLiabilityList.length;i++){
                    x=data.monthlyLiabilityList[i].declaredLiability;
                    sum+=Number(x);
                }
                $('#totalMonthlyLiability').html(sum);
                //$("#totalLiabilityC").val(sum);
            },
            error: function (data) {

            }
        });

    });

    var totalExpenseC,totalincomeC,totalLiabilityC=0,netIncomeC=0,minimumProposedLimit=0,maxAminPaymnet=0;
    $("#modal-credit-assessment-add-button").click(function () {
        $("#addcredit").show();
        // $("#creditAsseFooter").show();
        // clearCrdAsmnt();
        var dt={
            name : "name",
            cardId : $("#cardId").val()
        };
        $.ajax({
            url: '/card/financial/income/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
               // var sum=Number($('#totalMonthlyIncome').html());
                var sum =0;
                var x=0;
                for (var i=0;i<data.monthlyIncomeList.length;i++){
                    x=data.monthlyIncomeList[i].declaredIncome;
                    sum+=Number(x);
                }
                $("#totalincomeC").val(sum);


                totalincomeC=  sum;
            },
            error: function () {
                ////alert("Error");
            }
        });

        $.ajax({
            url: '/card/financial/expenditure/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
              //  var sum=Number($('#totalMonthlyExpenditure').html());
                var sum =0;
                var x=0;
                for (var i=0;i<data.monthlyExpList.length;i++){
                    x=data.monthlyExpList[i].declaredExpenditure;
                    sum+=Number(x);
                }
                $("#totalExpenseC").val(sum);

                totalExpenseC=sum;


            },
            error: function () {
                ////alert("Error");
            }
        });

        $.ajax({
            url: '/card/financial/liability/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {

                //var sum=Number($('#totalMonthlyLiability').html());
                var sum=Number($('#totalMonthlyLiability').val());
                var x=Number(0);
                for (var i=0;i<data.monthlyLiabilityList.length;i++){
                    x=data.monthlyLiabilityList[i].declaredLiability;
                    sum+=Number(x);
                }
                $("#totalLiabilityC").val(sum);
                totalLiabilityC=sum;
                netIncomeC=Number(totalincomeC)-Number(totalExpenseC)-Number(totalLiabilityC);
                netIncomeC=parseFloat(netIncomeC);
                $("#netIncomeC").val(netIncomeC);
                maxAminPaymnet=parseFloat(netIncomeC*.5)
                $("#maxAminPaymnet").val(maxAminPaymnet);
            },
            error: function () {
                ////alert("Error");
            }
        });

        $("#totalLiabilityC").keyup(function () {

            var totalLiabilityC= $("#totalLiabilityC").val();
            var totalincomeC  = $("#totalincomeC").val();
            var totalExpenseC = $("#totalExpenseC").val();
            netIncomeC=Number(totalincomeC)-Number(totalExpenseC)-Number(totalLiabilityC);
            netIncomeC=parseFloat(netIncomeC);
            $("#netIncomeC").val(netIncomeC);
            maxAminPaymnet=parseFloat(netIncomeC*.5);
            $("#maxAminPaymnet").val(maxAminPaymnet);
        })



       /* $("#localRecommendedlimitC").keyup(function () {
           var a=$("#localRecommendedlimitC").val();
            var b=$("#UsdRecommendedlimitC").val();
            var tot=Number(a)+Number(b)*Number(80.3);//Total  Recommanded limit
            tot=parseFloat(tot);
            minimumProposedLimit=parseFloat(tot*.05);// minimum Aim Proposed limit
            $("#TotalRecommendedlimitC").val(tot);
            $("#minimumProposedLimit").val(minimumProposedLimit);
           // var dbr=parseFloat((totalLiabilityC+minimumProposedLimit*100)/netIncomeC);// DBR calculation
           // $("#dbrCal").val(dbr)
            var totalLiabilityC = $("#totalLiabilityC").val();
            var minimumProposedLimit = $("#minimumProposedLimit").val();
            var totalincomeC = $("#totalincomeC").val();
            var netIncomeC = $("#netIncomeC").val();
           // var cal = parseFloat((minimumProposedLimit *100)/totalincomeC);
            var cal = parseFloat((minimumProposedLimit *100)/netIncomeC);
            var dbr = parseFloat(totalLiabilityC) + cal;
            $("#dbrCal").val(dbr);
        })*/

        $("#localRecommendedlimitC").keyup(function () {

            var a=$("#localRecommendedlimitC").val();
            var b=$("#UsdRecommendedlimitC").val();
            var tot=Number(a)+(Number(b)*Number(80.3));//Total  Recommanded limit
            tot=parseFloat(tot);
            minimumProposedLimit=parseFloat(tot*.05);// minimum Aim Proposed limit
            $("#TotalRecommendedlimitC").val(tot.toFixed(2));
            $("#minimumProposedLimit").val(minimumProposedLimit);
            var TotalRecommendedlimitC= $("#TotalRecommendedlimitC").val(tot.toFixed(2));
            var netIncomeC = $("#netIncomeC").val();
            $("#minimumProposedLimit").val(minimumProposedLimit);
            var dbr=parseFloat((totalLiabilityC+minimumProposedLimit*100)/netIncomeC);// DBR calculation
            $("#dbrCal").val(dbr.toFixed(2));
        });


        $("#UsdRecommendedlimitC").keyup(function () {/// last calculation DBR
            var a=$("#localRecommendedlimitC").val();
            var b=$("#UsdRecommendedlimitC").val();
            var tot=Number(a)+Number(b)*Number(80.3);
            tot=parseFloat(tot);
            minimumProposedLimit=parseFloat(tot*.05);
            $("#TotalRecommendedlimitC").val(tot.toFixed(2));
            $("#minimumProposedLimit").val(minimumProposedLimit);
            var netIncomeC = $("#netIncomeC").val();
            var dbr=parseFloat(totalLiabilityC+(minimumProposedLimit*100)/netIncomeC);
            $("#dbrCal").val(dbr.toFixed(2));
          /*  var totalLiabilityC = $("#totalLiabilityC").val();
            var minimumProposedLimit = $("#minimumProposedLimit").val();
            var totalincomeC = $("#totalincomeC").val();
            var cal = parseFloat((minimumProposedLimit *100)/totalincomeC);

            var cal = parseFloat((minimumProposedLimit *100)/netIncomeC);
            var dbr = parseFloat(totalLiabilityC) + cal;
            $("#dbrCal").val(dbr);*/

        })

    })
});

function CrdAsmntViewRow(node){
   $("#addcredit").show();
    var row=node.parentNode.parentNode;
    globalRow=row;
    $("#cerditAccId").val(row.cells[0].innerHTML);
    $("#cardId").val(row.cells[1].innerHTML);
    $("#totalincomeC").val(row.cells[2].innerHTML);
    $("#totalExpenseC").val(row.cells[3].innerHTML);
    $("#totalLiabilityC").val(row.cells[4].innerHTML);
    $("#netIncomeC").val(row.cells[4].innerHTML);
    $("#maxAminPaymnet").val(row.cells[6].innerHTML);
    $("#localRecommendedlimitC").val(row.cells[7].innerHTML);
    $("#UsdRecommendedlimitC").val(row.cells[8].innerHTML);
    $("#TotalRecommendedlimitC").val(row.cells[9].innerHTML);
    $("#minimumProposedLimit").val(row.cells[10].innerHTML);
    $("#dbrCal").val(row.cells[11].innerHTML);

    $('#addcredit').find(':input').attr('disabled', 'disabled');
    $('#creditAsmntCloseBtn').show();
    $('#creditAsmntSaveBtn').hide();
    $('#addcredit').modal({show:true, backdrop:false, keyboard:false});

    $('#cloasebtnforA').removeAttr("disabled");
    $('#creditAsmntCloseBtn').removeAttr("disabled");
    $('#modal-credit-assessment').appendTo("body");

   /* $('#modal-credit-assessment').find(':input').attr('disabled', 'disabled');
    $('#creditAsmntCloseBtn').show();
    $('#creditAsmntSaveBtn').hide();
    $('#modal-credit-assessment').modal({show:true, backdrop:false, keyboard:false});

    $('#cloasebtnforA').removeAttr("disabled");
    $('#creditAsmntCloseBtn').removeAttr("disabled");
    $('#modal-credit-assessment').appendTo("body");*/
}
function deleteCrdAsmRow(node){
    var dt={
        id: node.parentNode.parentNode.cells[0].innerHTML
    };

    $.ajax({
        url: "/credit/creditassessment/remove-credit-assessment",
        type: "GET",
        data: dt,
        success: function (data) {
            $(node).closest("tr").remove();
            location.reload();
        },
        error: function (data) {

        }
    });
}


