var globalExpRow;

function monthlyExpenditureInfo() {
    
    if (monthlyExpenditureValidation()==false){
        return;
    } 

    var formValues = $('#expenditureIncomeInfo').serializeJSON();
    var jsonEmploymentInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var decision=0;
    if($("#expdId").val()!="") decision=1;
    $.ajax({
        url: '/card/customer-info/financial-info/save-expenditure-financial-info',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonEmploymentInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-monthly-expenditure').modal('hide');
            clearExpData();
            if (data.monthlyExpenseInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.monthlyExpenseInfo.expdId + "</td>" +
                    "<td style=\"display:none;\">" + data.monthlyExpenseInfo.customerId + "</td>" +
                    "<td style=\"display:none;\">" + data.monthlyExpenseInfo.cardId + "</td>" +

                    "<td>" + data.monthlyExpenseInfo.expenditureType + "</td>" +
                    "<td>" + data.monthlyExpenseInfo.declaredExpenditure + "</td>" +

                    "<td><a class=\"btn btn-xs\" onclick=\"editExpenditureRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteExpenditureRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#monthlyExpenditureTable_tBody').append(str);
            }
            else if(data.monthlyExpenseInfo != null && decision==1){
                globalExpRow.cells[3].innerHTML=data.monthlyExpenseInfo.expenditureType;
                globalExpRow.cells[4].innerHTML=data.monthlyExpenseInfo.declaredExpenditure;
            }

            var sum=Number($('#totalMonthlyExpenditure').html());
            var x=data.monthlyExpenseInfo.declaredExpenditure;
            sum+=Number(x);
            $('#totalMonthlyExpenditure').html(sum);
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Monthly Expenditure Info!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}

function editExpenditureRow(node) {
    var row=node.parentNode.parentNode;
    globalExpRow=row;

    $("#expdId").val(row.cells[0].innerHTML);
    $("#customerId").val(row.cells[1].innerHTML);
    $("#cardId").val(row.cells[2].innerHTML);
    $("#expenditureType").val(row.cells[3].innerHTML);
    $("#declaredExpenditure").val(row.cells[4].innerHTML);

    $('#modal-monthly-expenditure').modal('show');

    var sum=Number($('#totalMonthlyExpenditure').html());
    var x=$("#declaredExpenditure").val();
    sum-=Number(x);
    $('#totalMonthlyExpenditure').html(sum);
}

function deleteExpenditureRow(node) {
    var dt={
        expdId: node.parentNode.parentNode.cells[0].innerHTML
    };

    $.ajax({
        url: "/card/customer-info/financial-info/remove-expenditure-financial-info",
        type: "GET",
        data: dt,
        success: function (data) {
            $(node).closest("tr").remove();
            var dt={
                cardId: $("#cardId").val()
            };
            $.ajax({
                url: "/card/customer-info/financial-info/exp/list",
                type: "GET",
                data: dt,
                success: function (data) {
                    var sum=0;
                    var x=0;
                    for (var i=0;i<data.monthlyExpList.length;i++){
                        x=data.monthlyExpList[i].declaredExpenditure;
                        sum+=Number(x);
                    }
                    $('#totalMonthlyExpenditure').html(sum);
                },
                error: function (data) {

                }
            });
        },
        error: function (data) {

        }
    });
}

function modal_show(){
    $('modal-monthly-expenditure').show();
}

$('#monthlyExpenditureSaveButton').click(function(event) {
    event.preventDefault();
    monthlyExpenditureInfo();
});

$('#monthlyExpenditureCloseButton').click(function(event) {
    event.preventDefault();
    clearExpData();
});

function monthlyExpenditureValidation() {
    if ($("#expenditureType").val()==0){
        alert("Expenditure Type must be filled out!");
        $("#expenditureType").focus();
        return false;
    }

    if ($("#declaredExpenditure").val()<=0){
        alert("Declared Expenditure must be filled out!");
        $("#declaredExpenditure").focus();
        return false;
    }

    var x = $("#declaredExpenditure").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Declared Expenditure must be valid..");
        $("#declaredExpenditure").focus();
        return false;
    }
}

function clearExpData(){
    $("#expdId").val("");
    $("#expenditureType").val("");
    $("#declaredExpenditure").val("");
}

$("#tab-financial").click(function (event) {

    var dt={
        cardId: $("#cardId").val()
    };

    $.ajax({
        url: "/card/customer-info/financial-info/exp/list",
        type: "GET",
        data: dt,
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
                        "<td><a class=\"btn btn-xs\" onclick=\"editExpenditureRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteExpenditureRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
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
        error: function (data) {

        }
    });

});