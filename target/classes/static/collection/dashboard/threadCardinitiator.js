$(document).ready(function () {


    $("#mo_data_calculate").click(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        if(confirm("Are you sure to initiate month opening data calculation?"))
        {
            console.log("Started...");
            $.ajax({
                type:"GET",
                url:"/kpi/cal/mo_data",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data:{
                },
                success:function (data) {
                    console.log(data);
                }
            })

        }else
            console.log("Cancel");

    });


    $("#btn_kpi_cal_card").click(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        if(confirm("Are you sure to initiate KPI calculation?"))
        {
            console.log("Started...");
            $.ajax({
                type:"GET",
                url:"/kpi/cal",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data:{
                },
                success:function (data) {

                    alert(data);
                    console.log(data);
                }
            })
        }else
            console.log("Cancel");

    });

});