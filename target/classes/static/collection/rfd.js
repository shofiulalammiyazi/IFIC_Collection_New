





function rfdSearch() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var  rfd=$("#rfd").val();
    var  subrfd=$("#subrfd").val();

    console.log(rfd);
    console.log(subrfd);



    var startDate = $('.rfdDate').data('daterangepicker').startDate._d;
    var endDate = $('.rfdDate').data('daterangepicker').endDate._d;

    var current_datetime = new Date(startDate);
    var current_Enddatetime = new Date(endDate);

    var current_dateTimeMonth = current_datetime.getMonth() + 1;
    var current_EndTimeMonth = current_Enddatetime.getMonth() + 1;

    if(current_dateTimeMonth < 10)  current_dateTimeMonth = "0"+current_dateTimeMonth;
    if(current_EndTimeMonth < 10)  current_EndTimeMonth = "0"+current_EndTimeMonth;


    var formatted_startdate =current_datetime.getDate() + "/" + current_dateTimeMonth + "/" + current_datetime.getFullYear();
    var formatted_enddate =current_Enddatetime.getDate() + "/" + current_EndTimeMonth + "/" + current_Enddatetime.getFullYear();
    console.log(formatted_startdate);
    console.log(formatted_enddate);

    $.ajax({
        url: '/rfd/report/rfdsearch',
        type: 'POST',
        // contentType: 'application/json',
        data: {
            startDate: formatted_startdate,
            endDate: formatted_enddate,
            rfd: rfd,
            subrfd: subrfd,
        },

        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);

            $('#rfdSrcBody').replaceWith("<tbody id=\"rfdSrcBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.length;i++) {
                if (data[i] != null) {
                    var str = "<tr>" +
                        "<td>" + formatDate(new Date(data[i].date) )+ "</td>" +
                        "<td>" + data[i].accountno + "</td>" +
                        "<td>" + data[i].accountname + "</td>" +
                        "<td>" + data[i].menu + "</td>" +
                        "<td>" + data[i].submenuone + "</td>" +
                        "<td>" + data[i].submenutwo + "</td>" +
                        "<td>" + data[i].submenuthree + "</td>" +
                        "<td>" + data[i].shortnote + "</td>" +
                        "<td>" + data[i].unit + "</td>" +
                        "</tr>";
                    $('#rfdSrcBody').append(str);
                }
            }
        },
        error: function (err) {
            //alert(err);
        }
    });
}


function formatDate(date) {
    var monthNames = [
        "Jan", "Feb", "Mar",
        "Apr", "May", "Jun", "Jul",
        "Aug", "Sep", "Oct",
        "Nov", "Dec"
    ];

    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return day + '-' + monthNames[monthIndex] + '-' + year;
}

// function formatDate(data) {
//     var olddate = new Date(data);
//     return olddate.getDate() + "-" + (olddate.getMonth() + 1) + "-" + olddate.getFullYear()
//
// }


