/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @param {type} flashes
 * @returns {undefined}
 */
var showFlashes = function (flashes) {

  flashes.forEach(function (index) {

    var cssClass = undefined;

    if (index.clazz === 'error') {

      cssClass = 'alert-error';
    } else {

      cssClass = 'alert-success';
    }

    Materialize.toast(index.clazz + ', ' + index.message, 4000, cssClass)
  });
}

/**
 * 
 * @param {id} id
 * @param {string} title
 * @param {[string]} xCategories
 * @param {string} yText
 * @param {string} yScale
 * @param {string} year
 * @param {string} month
 * @returns {undefined}
 */
function graphColumn(id, title, xCategories, yText, yScale, year, month) {
  function graph1(data) {
    $(id).highcharts({
      chart: {
        type: 'column'
      },
      title: {
        text: title
      },
      xAxis: {
        categories: [
          xCategories
        ],
        crosshair: true
      },
      yAxis: {
        type: yScale,
        minorTickInterval: 0.1,
        title: {
          text: yText
        }
      },
      tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
      },
      plotOptions: {
        column: {
          pointPadding: 0.2,
          borderWidth: 0
        }
      },
      series: data
    });
  }
  $(document).ready(function () {
    $.ajax({
      type: 'GET',
      url: '/DuckBoard/graphs/1',
      async: true,
      dataType: "json",
      data: ({
        'year': year,
        'month': month
      }),
      success: function (data) {
        graph1(data);
      }
    });
  });
}


/**
 * 
 * @param {id} id
 * @param {string} title
 * @param {string} year
 * @param {string} month
 * @returns {undefined}
 */
function graphSemiCircle(id, title, year, month) {
  function graph2(data) {
    $(id).highcharts({
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: 0,
        plotShadow: false
      },
      title: {
        text: title,
        align: 'center',
        verticalAlign: 'middle',
        y: 40
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
      },
      plotOptions: {
        pie: {
          dataLabels: {
            enabled: true,
            distance: -50,
            style: {
              fontWeight: 'bold',
              color: 'white',
              textShadow: '0px 1px 2px black'
            }
          },
          startAngle: -90,
          endAngle: 90,
          center: ['50%', '75%']
        }
      },
      series: [{
          type: 'pie',
          name: '%use',
          innerSize: '50%',
          data: data
        }]
    });
  }
  $(document).ready(function () {
    $.ajax({
      type: 'GET',
      url: '/DuckBoard/graphs/2',
      async: true,
      dataType: "json",
      data: ({
        'year': year,
        'month': month
      }),
      success: function (data) {
        graph2(data);
      }
    });
  });
}

/**
 * 
 * @param {type} id
 * @param {string} title
 * @param {[string]} xCategories
 * @param {string} yText
 * @param {string} yScale
 * @param {string} year
 * @param {string} month
 * @returns {undefined}
 */
function graphLine(id, title, xCategories, yText, yScale, year, month) {
  function graph3(data) {
    $(id).highcharts({
      title: {
        text: title,
        x: -20 //center
      },
      xAxis: {
        categories: xCategories
      },
      yAxis: {
        type: yScale,
        minorTickInterval: 0.1,
        title: {
          text: yText
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
          }]
      },
      tooltip: {
        valueSuffix: '°C'
      },
      legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
      },
      series: data
    });
  }
  $(document).ready(function () {
    $.ajax({
      type: 'GET',
      url: '/DuckBoard/graphs/3',
      async: true,
      dataType: "json",
      data: ({
        'year': year,
        'month': month
      }),
      success: function (data) {
        graph3(data);
      }
    });
  });
}

/**
 * 
 * @param {id} id
 * @param {string} title
 * @param {[string]} xCategories
 * @param {string} yText
 * @param {string} yScale
 * @param {string} year
 * @param {string} month
 * @returns {undefined}
 */
function graphPolar(id, title, xCategories, yText, yScale, year, month) {
  function graph4(data) {
    $(id).highcharts({
      chart: {
        type: 'areaspline'
      },
      title: {
        text: title
      },
      legend: {
        layout: 'vertical',
        align: 'left',
        verticalAlign: 'top',
        x: 150,
        y: 100,
        floating: true,
        borderWidth: 1,
        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
      },
      xAxis: {
        categories:
                xCategories
        ,
        plotBands: [{// visualize the weekend
            from: 4.5,
            to: 6.5,
            color: 'rgba(68, 170, 213, .2)'
          }]
      },
      yAxis: {
        type: yScale,
        minorTickInterval: 0.1,
        title: {
          text: yText
        }
      },
      tooltip: {
        shared: true,
        valueSuffix: ' units'
      },
      credits: {
        enabled: false
      },
      plotOptions: {
        areaspline: {
          fillOpacity: 0.5
        }
      },
      series: data
    });
  }
  $(document).ready(function () {
    $.ajax({
      type: 'GET',
      url: '/DuckBoard/graphs/4',
      async: true,
      dataType: "json",
      data: ({
        'year': year,
        'month': month
      }),
      success: function (data) {
        graph4(data);
      }
    });
  });
}

/**
 * 
 * @param {id} id
 * @param {string} title
 * @param {string} year
 * @param {string} month
 * @returns {undefined}
 */
function graphPieLegend(id, title, year, month) {
  function graph5(data) {

    $(document).ready(function () {

      // Build the chart
      $(id).highcharts({
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          type: 'pie'
        },
        title: {
          text: title
        },
        tooltip: {
          pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: false
            },
            showInLegend: true
          }
        },
        series: [{
            name: '%use',
            colorByPoint: true,
            data: data
          }]
      });
    });
  }
  $(document).ready(function () {
    $.ajax({
      type: 'GET',
      url: '/DuckBoard/graphs/5',
      async: true,
      dataType: "json",
      data: ({
        'year': year,
        'month': month
      }),
      success: function (data) {
        graph5(data);
      }
    });
  });
}

