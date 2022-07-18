
$(document).ready(function () {

    var monthinitiationvalue = $("#branchwisequeueandapproved").val();

    if(monthinitiationvalue != undefined){
        var ob = JSON.parse(monthinitiationvalue);
    }

   // console.log("branch Ob : "+ob)
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawVisualization);


    let now = new Date();
    let allday = new Date(now.getFullYear(), now.getMonth()+1, 0).getDate();
    let today = now.getDate();

    var arr = [];
    arr.push(['Month','Queue','Approved'])
    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];

    if(monthinitiationvalue != undefined){
        for(var i = 1; i<=12; i++){
            var innserArraySize = (ob[i-1]).length;
            var takeInnerValue = new Array();
            takeInnerValue.push(monthNames[i-1])
            for(var j = 0; j <innserArraySize; j++){
                takeInnerValue.push(ob[i-1][j])
            }
            arr.push(takeInnerValue)

        }
    }

    function drawVisualization() {
        // Some raw data (not necessarily accurate)
        var data = google.visualization.arrayToDataTable(
            arr
        );

        var options = {
            title : 'Branch wise Queue and Approved Product',
            vAxis: {title: 'Number'},
            hAxis: {title: 'Month'},
            seriesType: 'bars',
            series: {5: {type: 'line'}}
        };

        if(document.getElementById('branch_wise_queue_and_approved_div') != null){
            var chart = new google.visualization.ComboChart(document.getElementById('branch_wise_queue_and_approved_div'));
            chart.draw(data, options);
        }
    }
});