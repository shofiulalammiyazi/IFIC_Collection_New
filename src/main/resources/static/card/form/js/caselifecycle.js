$(document).ready(function () {



    $("#caselifecyclebtn").click(function () {
        var cardCaseNo = $("#cardCaseNo").val();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type:"post",
            url:"/card/report/caselifecyclesearch",
            data:{"caseNo":cardCaseNo},
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success:function (data) {
                var len = data.length;
                var str = "";

                var index=1;
                for(var i = 0; i<len; i++){

                    var empid = '-';
                    var username = '-';
                    var task = '-';
                    var receivedate = '-';
                    var completedate = '-';
                    var duration = '-';
                    var categoryname = "-";
                    if( data[i].assigne != null ) { empid = data[i].empid; }
                    if( data[i].uname != null ) { username = data[i].uname; }
                    if( data[i].startDate != null ) { receivedate = data[i].startDate; }
                    if( data[i].endDate != null ) { completedate = data[i].endDate; }

                    if( data[i].duration != null ) { duration = (data[i].duration); } //

                    if( data[i].duration != null ) { duration = (data[i].duration); }

                    if( data[i].category != null ) { categoryname = data[i].category; }




                    str += "<tr>" +
                        "<td>" + empid + "</td>" +
                        "<td>" + username + "</td>" +
                        "<td>" + categoryname.replace(/_/g, ' ') + "</td>" +
                        "<td>" + receivedate+ "</td>" +
                        "<td>" + completedate + "</td>" +
                        "<td>" + duration + "</td>" +
                        "</tr>";
                }


                $('#caselifecyclesearchresult').html(str);
            },
            error:function (textStatus) {
            }
        });
    });

    // function msToTime(duration) {
    //     var milliseconds = parseInt((duration%1000)/100)
    //         , seconds = parseInt((duration/1000)%60)
    //         , minutes = parseInt((duration/(1000*60))%60)
    //         , hours = parseInt((duration/(1000*60*60))%24);
    //
    //     hours = (hours < 10) ? "0" + hours : hours;
    //     minutes = (minutes < 10) ? "0" + minutes : minutes;
    //     seconds = (seconds < 10) ? "0" + seconds : seconds;
    //
    //     return hours + "h " + minutes + "m " + seconds + "s " + milliseconds +"ms ";
    // }
});