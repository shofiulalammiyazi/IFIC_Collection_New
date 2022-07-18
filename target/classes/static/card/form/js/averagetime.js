
$(document).ready(function () {

    var monthinitiationvalue = $("#averagetimeinitiationtoapproved").val();
    if(monthinitiationvalue != undefined){
        var ob = JSON.parse(monthinitiationvalue);
    }

    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawVisualization);


    var now = new Date();
    var allday = new Date(now.getFullYear(), now.getMonth()+1, 0).getDate();
    var today = now.getDate();

    var arr = [];
    arr.push(['Month','Average'])
    // arr.push(['Jan',11])
    // arr.push(['Feb',12])
    // arr.push(['Mar',13])
    // arr.push(['Apl',10])
    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    if(monthinitiationvalue != undefined){
        for(var i = 1; i<=12; i++){
            arr.push([monthNames[i-1].toString(),ob[i-1]])
        }
    }

    function drawVisualization() {
        // Some raw data (not necessarily accurate)
        var data = google.visualization.arrayToDataTable(
            arr
        );

        var options = {
            title : 'Average time ',
            vAxis: {title: 'Number'},
            hAxis: {title: 'Month'},
            seriesType: 'bars',
            series: {5: {type: 'line'}}
        };

        if(document.getElementById('averagetimediv') != null){
            var chart = new google.visualization.ComboChart(document.getElementById('averagetimediv'));
            chart.draw(data, options);
        }
    }
});