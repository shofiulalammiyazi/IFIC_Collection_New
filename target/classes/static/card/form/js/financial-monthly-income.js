var globalIncomeRow;

/*$('#totalMonthlyIncome').html(tot);*/
function monthlyIncomeInfo() {

    if (monthlyIncomeValidation()==false){
        return;
    }

    var decision=0;
    if($("#incomeId").val()!="") decision=1;

    var formValues = $('#monthlyIncomeInfo').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/card/customer-info/financial-info/save-monthly-financial-info',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonEmploymentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-monthly-income').modal('hide');
            clearIncomeData();
            if (data.monthlyIncomeInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.monthlyIncomeInfo.incomeId + "</td>" +
                    "<td style=\"display:none;\">" + data.monthlyIncomeInfo.customerId + "</td>" +
                    "<td style=\"display:none;\">" + data.monthlyIncomeInfo.cardId + "</td>" +

                    "<td>" + data.monthlyIncomeInfo.incomeTypeId + "</td>" +
                    "<td>" + data.monthlyIncomeInfo.declaredIncome + "</td>" +

                    "<td><a class=\"btn btn-xs\" onclick=\"editIncomeRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteIncomeRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#monthlyIncomeTable_tBody').append(str);
                /*    var x=data.monthlyIncomeInfo.declaredIncome;
                    console.log("new value:"+x);
                    tot=$("#totalMonthlyIncome").html();
                    console.log("Previous:"+tot);
                    tot=Number(tot)+Number(x);
                    console.log("After:"+tot);
                    $('#totalMonthlyIncome').html(tot);*/


            }
            else if (data.monthlyIncomeInfo != null && decision==1){
                globalIncomeRow.cells[3].innerHTML=data.monthlyIncomeInfo.incomeTypeId;
                globalIncomeRow.cells[4].innerHTML=data.monthlyIncomeInfo.declaredIncome;
            }

            var sum=Number($('#totalMonthlyIncome').html());
            var x=data.monthlyIncomeInfo.declaredIncome;
            sum+=Number(x);
            $('#totalMonthlyIncome').html(sum);



        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Monthly Income!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}



function editIncomeRow(node){
    var row=node.parentNode.parentNode;
    globalIncomeRow=row;

    $("#incomeId").val(row.cells[0].innerHTML);
    $("#customerId").val(row.cells[1].innerHTML);
    $("#cardId").val(row.cells[2].innerHTML);
    $("#incomeTypeId").val(row.cells[3].innerHTML);
    $("#declaredIncome").val(row.cells[4].innerHTML);

    $('#modal-monthly-income').modal('show');

    var sum=Number($('#totalMonthlyIncome').html());
    var x=$("#declaredIncome").val();
    sum-=Number(x);
    $('#totalMonthlyIncome').html(sum);

}

function deleteIncomeRow(node){
    var dt={
        incomeId: node.parentNode.parentNode.cells[0].innerHTML
    };
    $.ajax({
        url: "/card/customer-info/financial-info/remove-monthly-financial-info",
        type: "GET",
        data: dt,
        success: function (data) {

            $(node).closest("tr").remove();

            var dt={
                cardId: $("#cardId").val()
            };
            $.ajax({
                url: "/card/customer-info/financial-info/income/list",
                type: "GET",
                data: dt,
                success: function (data) {
                    var sum=0;
                    var x=0;
                    for (var i=0;i<data.monthlyIncomeList.length;i++){
                        x=data.monthlyIncomeList[i].declaredIncome;
                        sum+=Number(x);
                    }
                    $('#totalMonthlyIncome').html(sum);
                },
                error: function (data) {

                }
            });

        },
        error: function (data) {

        }
    })
}


$('#monthlyIncomeSaveButton').click(function(event) {
    event.preventDefault();
    monthlyIncomeInfo();
});

$('#monthlyIncomeCloseButton').click(function(event) {
    event.preventDefault();
    clearIncomeData();
});

function monthlyIncomeValidation() {
    if ($("#incomeTypeId").val()==0){
        alert("Income Source must be filled out!");
        $("#incomeTypeId").focus();
        return false;
    }

    if ($("#declaredIncome").val()==""){
        alert("Declared Income must be filled out!");
        $("#declaredIncome").focus();
        return false;
    }

    var x = $("#declaredIncome").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Declared Income  must be valid..");
        $("#declaredIncome").focus();
        return false;
    }
}




//edit financial info for Preview
$(document).ready(function(){

    $("#tab-financial").click(function(){
        ////alert("finance");
        //alert("peview financial click "+$("#personalCustomerId").val());
        var dt={
            name : "name",
            cid : $("#personalCustomerId").val()
        };

        $.ajax({
            url: '/card/financial/credit/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
                ////alert("Success");
                if (data.creditInfo != null) {
                    var str = "<tr>" +
                        "<td>" + data.creditInfo.facilityType + "</td>" +
                        "<td>" + data.creditInfo.bankName1 + "</td>" +
                        "<td>" + data.creditInfo.branchName + "</td>" +
                        "<td>" + data.creditInfo.cardNo + "</td>" +
                        "<td>" + data.creditInfo.loanAccNo + "</td>" +
                        "<td>" + data.creditInfo.sanctionLimit + "</td>" +
                        "<td>" + data.creditInfo.sanctionLimit + "</td>" +
                        "</tr>";
                    $('#creditFacility_tBody').append(str);
                }
            },
            error: function () {
                ////alert("Error");
            }
        });

        dt={
            name : "name",
            cid : $("#cardId").val()
        };

        $.ajax({
            url: '/card/financial/income/preview',
            type: "GET",
            data: dt,
            dataType: 'json',
            success: function (data) {
                ////alert("Success");
                if (data.incomeInfo != null) {
                    var str = "<tr>" +
                        "<td>" + data.incomeInfo.incomeTypeId + "</td>" +
                        "<td>" + data.incomeInfo.declaredIncome + "</td>" +
                        "</tr>";
                    $('#monthlyIncomeTable_tBody').append(str);
                }
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
                ////alert("Success");
                if (data.expenditureInfo != null) {
                    var str = "<tr>" +
                        "<td>" + data.expenditureInfo.expenditureType + "</td>" +
                        "<td>" + data.expenditureInfo.declaredExpenditure + "</td>" +
                        "</tr>";
                    $('#monthlyExpenditureTable_tBody').append(str);
                }
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
                ////alert("Success");
                if (data.incomeInfo != null) {
                    var str = "<tr>" +
                        "<td>" + data.incomeInfo.liabilityType + "</td>" +
                        "<td>" + data.incomeInfo.declaredLiability + "</td>" +
                        "</tr>";
                    $('#monthlyLiabilityTable_tBody').append(str);
                }
            },
            error: function () {
                ////alert("Error");
            }
        });
    });
});

function clearIncomeData(){
    $("#incomeId").val("");
    $("#incomeTypeId").val("")
    $("#declaredIncome").val("");
}

$("#tab-financial").click(function (event) {
    var dt={
        cardId: $("#cardId").val()
    };

    $.ajax({
        url: "/card/customer-info/financial-info/income/list",
        type: "GET",
        data: dt,
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
                        "<td><a class=\"btn btn-xs\" onclick=\"editIncomeRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteIncomeRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
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
        error: function (data) {

        }
    });

});



