function serachCustomerInfo() {

    var formValues = $('#customerSearchInfoForm').serializeJSON();
    var jsoncustomerSearchInfoFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/customer/search/getSearch',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: jsoncustomerSearchInfoFormData,
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                // $('#customerInfoCreditFacilityModal').modal('hide');
                var str="";
                $("#customerSearchInfo_tBody").html(str);
                for(i=0; i<data.personalInfoDtoList.length; i++){
                    if (1) {
                        var index=i+1;
                        str = "<tr>" +
                            "<td>" + index + "</td>" +
                            "<td>" + data.personalInfoDtoList[i].caseNumber + "</td>" +
                            "<td>" + data.personalInfoDtoList[i].cusName + "</td>" +
                            "<td>" + data.personalInfoDtoList[i].cusType + "</td>" +
                            "<td>" + data.personalInfoDtoList[i].contactNo + "</td>" +
                            "<td>" + data.personalInfoDtoList[i].nid + "</td>" +
                            "<td>" + data.personalInfoDtoList[i].tin + "</td>" +
                            "</tr>";
                        $('#customerSearchInfo_tBody').append(str);
                    }
                }

                //$('#employmentInfoMain').show();
                // $('#Previous-Experiance-Modal').modal('hide');
                // $("#expInfoId").val(data.personalInfoDtoList.expInfoId);
                // if (data.personalInfoDtoList != null) {
                //     var str = "<tr>" +
                //         "<td>" + data.personalInfoDtoList.expProfession + "</td>" +
                //         "<td>" + data.personalInfoDtoList.expCompName + "</td>" +
                //         "<td>" + data.personalInfoDtoList.expDepartment + "</td>" +
                //         "<td>" + data.personalInfoDtoList.expDesignation + "</td>" +
                //         "<td>" + data.personalInfoDtoList.expBusinessNature + "</td>" +
                //         "<td>" +
                //         " <button class='btn-info' value='${expInfoId}' onclick=\"prevExperianceEditBtn()\">Edit</button> "+
                //         "</td>" +
                //         "</tr>";
                //     $('#pExperianceInfo_tBody').append(str);
                // }
            },
            error: function (err) {
                //alert(err);
            }
        });
}

$('#customerSearchButton').click(function(event) {

    event.preventDefault();
    serachCustomerInfo();
});

