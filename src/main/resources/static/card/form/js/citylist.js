
function showCityList(id) {
    $("#prCity").html("");
    $("#prmCity").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#prCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#prCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }


            $("#prmCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#prmCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }
        },
        error: function (err) {
        }
    });
}

function showCityListPrm(id) {
    $("#prmCity").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#prmCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#prmCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }


        },
        error: function (err) {
        }
    });
}

function showSuppCardCityList(id) {
    $("#suppCity").html("");
    $("#supppmblock1").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data.cityList);
            $("#suppCity").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#suppCity").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }

            $("#supppmblock1").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#supppmblock1").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }

        },
        error: function (err) {
        }
    });
}

function showSuppCardCityListPrm(id) {
    $("#supppmblock1").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#supppmblock1").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#supppmblock1").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }
        },
        error: function (err) {
        }
    });
}

function showIntroducerInfoCityList(id) {
    $("#incity11").html("");
    $("#incity22").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#incity11").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#incity11").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }

            $("#incity22").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#incity22").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }

        },
        error: function (err) {
        }
    });
}

function showIntroducerInfoCityListPrm(id) {
    $("#incity22").html("");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/city/citylist?countryName="+id,
        type: 'GET',
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $("#incity22").append("<option selected=\"selected\">Select</option>");
            for (i=0;i<data.cityList.length;i++){
                $("#incity22").append("<option  value="+ data.cityList[i].name +" >"+data.cityList[i].name+"</option>");
            }
        },
        error: function (err) {
        }
    });
}