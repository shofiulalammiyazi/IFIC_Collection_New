





function activitySearch() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    // var  supervisor=$("#activitysupervisor").val();
    // var  account=$("#activityAccount").val();

    // console.log(supervisor);
    // console.log(account);



    var startDate = $('.activityDate').data('daterangepicker').startDate._d;
    var endDate = $('.activityDate').data('daterangepicker').endDate._d;

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
        url: '/activity/report/activityreport',
        type: 'POST',
        // contentType: 'application/json',
        data: {
            startDate: formatted_startdate,
            endDate: formatted_enddate,
            // supervisor: supervisor,

        },

        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);

            $('#activitySrcBody').replaceWith("<tbody id=\"activitySrcBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.length;i++) {
                if (data[i] != null) {
                    var str = "<tr>" +
                        "<td>" + data[i].customerid + "</td>" +
                        "<td>" +data[i].acountname + "</td>" +
                        "<td>" + data[i].accountno + "</td>" +
                        "<td>" + data[i].ptp + "</td>" +
                        "<td>" + data[i].dairynote + "</td>" +
                        "<td>" + data[i].dailynote + "</td>" +
                        "<td>" + data[i].hotnote + "</td>" +
                        "<td>" + data[i].followup + "</td>" +
                        "<td>" + data[i].unit + "</td>" +
                        "</tr>";
                    $('#activitySrcBody').append(str);
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


function deleteData(link) {
    if(confirm('Are you sure want to delete this information ?')) {
        document.location.href = link;
    }
}