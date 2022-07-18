
$(document).ready(function () {

    var monthinitiationvalue = $("#monthinitiationhistory").val();
    var chartvalue = $("#chart").val();
    if(monthinitiationvalue != undefined){
        var ob = JSON.parse(monthinitiationvalue);
    }

    //console.log("Ob : "+ob)
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawVisualization);

    var arr = [];
    var arr2 = new Array();
    arr2.push("Month");

    if(chartvalue != undefined){
        chartvalue
            .trim()
            .slice(1, -1)
            .split(',')
            .forEach(function(v) {
                var val = v.trim().split('=');
                arr2.push(val[0]);
            })
    }


    arr.push(arr2);

    var now = new Date();
    var allday = new Date(now.getFullYear(), now.getMonth()+1, 0).getDate();
    var today = now.getDate();

    if(monthinitiationvalue != undefined){
        for(var i = 1; i<=today; i++){
            var innserArraySize = (ob[i-1]).length;
            var takeInnerValue = new Array();
            takeInnerValue.push(i.toString())
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
            title : 'Month Initiation History',
            vAxis: {title: 'Number'},
            hAxis: {title: 'Day'},
            seriesType: 'bars',
            series: {5: {type: 'line'}}
        };

        if(document.getElementById('month_initiation_history_div') != null){
            var chart = new google.visualization.ComboChart(document.getElementById('month_initiation_history_div'));
            chart.draw(data, options);
        }
    }
});