var globalLiabilityRow;

/*$('#totalMonthlyLiability').html(tot);*/

function monthlyLiabilityInfo() {

    if (monthlyLiabilityValidation()==false){
        return;
    }

    var decision=0;
    if($("#liabilityId").val()!="") decision=1;

    var formValues = $('#monthlyLiabilityInfoForm').serializeJSON();
    var jsonmonthlyLiabilityInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/customer-info/financial-info/save-monthly-financial-liability',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonmonthlyLiabilityInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#modal-monthly-liability').modal('hide');
            clearLiabilityData();
            if (data.monthlyLiabilityInfo != null && decision==0) {
                var str = "<tr>" +
                    "<td style=\"display:none;\">" + data.monthlyLiabilityInfo.liabilityId + "</td>" +
                    "<td style=\"display:none;\">" + data.monthlyLiabilityInfo.customerId + "</td>" +
                    "<td style=\"display:none;\">" + data.monthlyLiabilityInfo.cardId + "</td>" +

                    "<td>" + data.monthlyLiabilityInfo.liabilityType + "</td>" +
                    "<td>" + data.monthlyLiabilityInfo.declaredLiability + "</td>" +

                    "<td><a class=\"btn btn-xs\" onclick=\"editLiabilityRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                    "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteLiabilityRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
                    "</tr>";
                $('#monthlyLiabilityTable_tBody').append(str);
                /*    var x=data.monthlyLiabilityInfo.declaredLiability;
                    console.log("new value:"+x);
                    tot=$("#totalMonthlyLiability").html();
                    console.log("Previous:"+tot);
                    tot=Number(tot)+Number(x);
                    console.log("After:"+tot);
                    $('#totalMonthlyLiability').html(tot);*/


            }
            else if (data.monthlyLiabilityInfo != null && decision==1){
                globalLiabilityRow.cells[3].innerHTML=data.monthlyLiabilityInfo.liabilityType;
                globalLiabilityRow.cells[4].innerHTML=data.monthlyLiabilityInfo.declaredLiability;
            }

            var sum=Number($('#totalMonthlyLiability').html());
            var x=data.monthlyLiabilityInfo.declaredLiability;
            sum+=Number(x);
            $('#totalMonthlyLiability').html(sum);



        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Monthly Liability!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}



function editLiabilityRow(node){
    var row=node.parentNode.parentNode;
    globalLiabilityRow=row;

    $("#liabilityId").val(row.cells[0].innerHTML);
    $("#customerId").val(row.cells[1].innerHTML);
    $("#cardId").val(row.cells[2].innerHTML);
    $("#liabilityType").val(row.cells[3].innerHTML);
    $("#declaredLiability").val(row.cells[4].innerHTML);

    $('#modal-monthly-liability').modal('show');

    var sum=Number($('#totalMonthlyLiability').html());
    var x=$("#declaredLiability").val();
    sum-=Number(x);
    $('#totalMonthlyLiability').html(sum);

}

function deleteLiabilityRow(node){
    var dt={
        liabilityId: node.parentNode.parentNode.cells[0].innerHTML
    };
    $.ajax({
        url: "/card/customer-info/financial-info/remove-financial-liability-info",
        type: "GET",
        data: dt,
        success: function (data) {

            $(node).closest("tr").remove();
            var dt={
                cardId: $("#cardId").val()
            };
            $.ajax({
                url: "/card/customer-info/financial-info/liability/list",
                type: "GET",
                data: dt,
                success: function (data) {
                    console.log(data.monthlyLiabilityList);
                    var sum=0;
                    var x=0;
                    for (var i=0;i<data.monthlyLiabilityList.length;i++){
                        x=data.monthlyLiabilityList[i].declaredLiability;
                        sum+=Number(x);
                    }
                    $('#totalMonthlyLiability').html(sum);
                },
                error: function (data) {

                }
            });

        },
        error: function (data) {

        }
    })
}


$('#monthlyLiabilitySaveButton').click(function(event) {
    event.preventDefault();
    monthlyLiabilityInfo();
});

$('#monthlyLiabilityCloseButton').click(function(event) {
    event.preventDefault();
    clearLiabilityData();
});

function monthlyLiabilityValidation() {
    if ($("#liabilityType").val()==0){
        alert("Liability Source must be filled out!");
        $("#liabilityType").focus();
        return false;
    }

    if ($("#declaredLiability").val()==""){
        alert("Declared Liability must be filled out!");
        $("#declaredLiability").focus();
        return false;
    }

    var x = $("#declaredLiability").val();
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Declared Liability  must be valid..");
        $("#declaredLiability").focus();
        return false;
    }
}

function clearLiabilityData(){
    $("#liabilityId").val("");
    $("#liabilityType").val("")
    $("#declaredLiability").val("");
}

$("#tab-financial").click(function (event) {

    var dt={
        cardId: $("#cardId").val()
    };
    $.ajax({
        url: "/card/customer-info/financial-info/liability/list",
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
                        "<td><a class=\"btn btn-xs\" onclick=\"editLiabilityRow(this)\"><i class=\"fa fa-edit\"></i>Edit</a>\n" +
                        "<a class=\"btn btn-xs btn-xs-dlt\" onclick=\"deleteLiabilityRow(this)\"> <i class=\"fa fa-trash\"></i>Delete</a></td>" +
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

        },
        error: function (data) {

        }
    });

});



