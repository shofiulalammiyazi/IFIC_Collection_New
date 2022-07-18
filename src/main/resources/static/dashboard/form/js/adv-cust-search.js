// BDT NUMBER FORMATE Implementation 



//BDT Number Formate Implementation end 

function advCustSearchInfo() {
    //$('#advCustSrcBody').clean();
    var formValues = $('#advSearchForm').serializeJSON();
    var cardPersonalInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var str="";
    $.ajax({
        url: '/search/adv-search',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: cardPersonalInfoFormData,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            //$("advCustSrcBody").val("");
            $('#advCustSrcBody').html(str);
            for (var i=0;i<data.personalInfoList.length;i++){
                if (1){
                    var index=i+1;
                     str+="<tr>"+
                         "<td>" + index + "</td>"+
                        "<td>" + data.personalInfoList[i].customerName + "</td>"+
                        "<td>" + data.personalInfoList[i].fatherName + "</td>"+
                        "<td>" + data.personalInfoList[i].motherName + "</td>"+
                        "<td>" + data.personalInfoList[i].contactNo + "</td>"+
                        "<td>" + data.personalInfoList[i].nid + "</td>"+
                        "<td>" + data.personalInfoList[i].tin + "</td>"+
                        "<td>" + data.personalInfoList[i].passportNo + "</td>"+
                        "<td>" + data.personalInfoList[i].drivingLicense + "</td>"+
                        "<td>" + data.personalInfoList[i].permanentAddress + "</td>"+

                        "</tr>";
                }
            }

            $('#advCustSrcBody').html(str);
        },
        error: function (err) {
            //alert(err);
        }
    });
}

$('#advCustSearchButton').click(function(event) {
    event.preventDefault();
    advCustSearchInfo();
});

