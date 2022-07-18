

$(document).ready(function () {


    if($('#selDistrictLoc').length > 0) {

        var locId=$("#selDistrictLoc").val();

        if(locId.length > 0)
        {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                url: '/collection/zone/zonelistbyid?locId='+locId,
                type: 'GET',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {

                    var optionList='';

                    $.each(data, function(index, value){
                        optionList += '<option value ="'+ value.id +'">'+value.name+'</option>';
                    });

                    $('#selZone').html(optionList);
                },
                error: function (err) {
                    console.log(err.status);
                }
            });
        }
    }
});



$("#selDistrictLoc").change(function () {

    var locId=$("#selDistrictLoc").val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: '/collection/zone/zonelistbyid?locId='+locId,
        type: 'GET',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            var optionList='';

            $.each(data, function(index, value){
                optionList += '<option value ="'+ value.id +'">'+value.name+'</option>';
            });

            $('#selZone').html(optionList);
        },
        error: function (err) {
            console.log(err.status);;
        }
    });
});

