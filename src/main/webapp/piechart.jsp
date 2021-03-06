<!DOCTYPE html>
<html>
<head>
  <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
  <title>User Input Form</title>
  <script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
  <script src="http://code.highcharts.com/highcharts.js"></script>
  <script src="http://code.highcharts.com/modules/exporting.js"></script>
  <script>
    $(document).ready(function () {    
           
            RenderPieChart('container', [
                      ['Company', 45.0],
                      ['Family', 35.0],
                      ['Other',  20.0],                         
                  ]);     
     
     $('#btnPieChart').live('click', function(){ 
         var data = [
                      ['Firefox', 42.0],
                      ['IE', 26.8],
                      {
                          name: 'Chrome',
                          y: 14.8,
                          sliced: true,
                          selected: true
                      },
                      ['Safari', 6.5],
                      ['Opera', 8.2],
                      ['Others', 0.7]
                  ];
             
            RenderPieChart('container', data);
     });
     
            function RenderPieChart(elementId, dataList) {
                new Highcharts.Chart({
                    chart: {
                        renderTo: elementId,
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    }, title: {
                        text: 'Browser market shares at a specific website, 2010'
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.point.name + '</b>: ' + this.percentage + ' %';
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                color: '#000000',
                                connectorColor: '#000000',
                                formatter: function () {
                                    return '<b>' + this.point.name + '</b>: ' + this.percentage + ' %';
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: 'Browser share',
                        data: dataList
                    }]
                });
            };
        });
  </script>
</head>
<body>
<h2>User Input Form</h2>
<div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
<input type="button" id="btnPieChart" value="Pie Chart" />
<div>  ${test}
 </div>
</body>
