





function advptpSearchInfo() {
    // alert("click");

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var  dealer=$("#dealer").val();
    var  teamleader=$("#teamleader").val();
    var  supervisor=$("#supervisor").val();
    var  manager=$("#manager").val();
    var  account=$("#account").val();
    console.log(dealer);
    console.log(teamleader);
    console.log(supervisor);
    console.log(manager);
    console.log(account);


    var startDate = $('#reservation').data('daterangepicker').startDate._d;
    var endDate = $('#reservation').data('daterangepicker').endDate._d;

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
    // dd/mm/yyyy
    // console.log(startDate);
    // console.log(endDate);
    $.ajax({
        url: '/collection/report/search',
        // url: '/collection/report/search?startDate='+formatted_startdate+'&endDate='+formatted_enddate,
        type: 'POST',
        // contentType: 'application/json',
        data: {
            startDate: formatted_startdate,
            endDate: formatted_enddate,
            dealer: dealer,
            teamleader: teamleader,
            supervisor: supervisor,
            manager: manager,
            account: account

        },

        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);

            $('#ptpSrcBody').replaceWith("<tbody id=\"ptpSrcBody\" >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for(var i=0;i<data.length;i++) {
                if (data[i] != null) {
                    var str = "<tr>" +
                        "<td>" + data[i].accountno + "</td>" +
                        "<td>" + data[i].accountname + "</td>" +
                        "<td>" + formatDate(new Date(data[i].createddate) )+ "</td>" +
                        "<td>" +formatDate(new Date(data[i].loanptpdate )) + "</td>" +
                        "<td>" + data[i].loanptpamount + "</td>" +
                        "<td>" + data[i].loanptpstatus + "</td>" +
                        "<td>" + data[i].unit + "</td>" +
                        "</tr>";
                    $('#ptpSrcBody').append(str);
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


