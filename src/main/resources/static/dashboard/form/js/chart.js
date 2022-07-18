$(document).ready(function () {

    let chartvalue = $("#chart").val();

    var arr = [];
    arr.push(['', ''])
    if(chartvalue != undefined){
        chartvalue
            .trim()
            .slice(1, -1)
            .split(',')
            .forEach(function(v) {
                let val = v.trim().split('=');
                arr.push([val[0],parseInt(val[1])])
               // console.log(val[0]+" - "+val[1])
            })

    }

    // Load google charts
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = google.visualization.arrayToDataTable(
            arr
        );
        // Optional; add a title and set the width and height of the chart
        var options = {'title':'Product Category in : '+(new Date().getFullYear()), 'width':500, 'height':400};

        // Display the chart inside the <div> element with id="piechart"
        if(document.getElementById('chartContainer') != null){
            var chart = new google.visualization.PieChart(document.getElementById('chartContainer'));
            chart.draw(data, options);
        }
    }
});