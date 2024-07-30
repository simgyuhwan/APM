const memoryLineCtx = document.getElementById('memoryLineChart').getContext(
    '2d');
const cpuLineCtx = document.getElementById('cpuLineChart').getContext('2d');
const realTimeMemoryLineCtx = document.getElementById(
    'realTimeMemoryChart').getContext('2d');
const realTimeCpuLineCtx = document.getElementById(
    'realTimeCpuChart').getContext('2d');
const pointRadiusPixel = 1;
const borderWidthValue = 2;
const tensionValue = 0.2;

const memoryLineChart = new Chart(memoryLineCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      {
        label: 'Total Memory',
        data: [],
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: borderWidthValue,
        pointRadius: pointRadiusPixel,
        tension: tensionValue,
        fill: false
      },
      {
        label: 'Used Memory',
        data: [],
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: borderWidthValue,
        pointRadius: pointRadiusPixel,
        tension: tensionValue,
        fill: false
      },
      {
        label: 'Free Memory',
        data: [],
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: borderWidthValue,
        pointRadius: pointRadiusPixel,
        tension: tensionValue,
        fill: false
      },
      {
        label: 'Used Percent',
        data: [],
        borderColor: 'rgba(153, 102, 255, 1)',
        borderWidth: borderWidthValue,
        fill: false,
        tension: tensionValue,
        pointRadius: pointRadiusPixel,
      }
    ]
  },
  options: {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'day'
        }
      },
      y: {
        beginAtZero: true
      }
    }
  }
});

const cpuLineChart = new Chart(cpuLineCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      {
        label: 'CPU Usage',
        data: [],
        borderColor: 'rgba(255, 206, 86, 1)',
        borderWidth: borderWidthValue,
        fill: false,
        tension: tensionValue,
        pointRadius: pointRadiusPixel,
      },
      {
        label: 'CPU Load',
        data: [],
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: borderWidthValue,
        fill: false,
        tension: tensionValue,
        pointRadius: pointRadiusPixel,
      }
    ]
  },
  options: {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'day'
        }
      },
      y: {
        beginAtZero: true
      }
    }
  }
});

const realTimeMemoryChart = new Chart(realTimeMemoryLineCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      {
        label: 'Total Memory',
        data: [],
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: borderWidthValue,
        pointRadius: pointRadiusPixel,
        tension: tensionValue,
        fill: false
      },
      {
        label: 'Used Memory',
        data: [],
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: borderWidthValue,
        pointRadius: pointRadiusPixel,
        tension: tensionValue,
        fill: false
      },
      {
        label: 'Free Memory',
        data: [],
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: borderWidthValue,
        pointRadius: pointRadiusPixel,
        tension: tensionValue,
        fill: false
      },
      {
        label: 'Used Percent',
        data: [],
        borderColor: 'rgba(153, 102, 255, 1)',
        borderWidth: borderWidthValue,
        fill: false,
        tension: tensionValue,
        pointRadius: pointRadiusPixel,
      }
    ]
  },
  options: {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'day'
        }
      },
      y: {
        beginAtZero: true
      }
    }
  }
});

const realTimeCpuChart = new Chart(realTimeCpuLineCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      {
        label: 'CPU Usage',
        data: [],
        borderColor: 'rgba(255, 206, 86, 1)',
        borderWidth: borderWidthValue,
        fill: false,
        tension: tensionValue,
        pointRadius: pointRadiusPixel,
      },
      {
        label: 'CPU Load',
        data: [],
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: borderWidthValue,
        fill: false,
        tension: tensionValue,
        pointRadius: pointRadiusPixel,
      }
    ]
  },
  options: {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'day'
        }
      },
      y: {
        beginAtZero: true
      }
    }
  }
});

function loadMemoryData(timeRange) {
  fetch(`v1/metrics/mem?timeRange=${timeRange}`)
  .then(response => response.json())
  .then(data => {
    memoryLineChart.data.labels = [];
    memoryLineChart.data.datasets[0].data = [];
    memoryLineChart.data.datasets[1].data = [];
    memoryLineChart.data.datasets[2].data = [];
    memoryLineChart.data.datasets[3].data = [];

    if (timeRange === '7d' || timeRange === '30d') {
      const aggregatedData = aggregateDataByDay(data);
      aggregatedData.forEach(metric => {
        memoryLineChart.data.labels.push(metric.date);
        memoryLineChart.data.datasets[0].data.push(metric.totalMem);
        memoryLineChart.data.datasets[1].data.push(metric.usedMem);
        memoryLineChart.data.datasets[2].data.push(metric.freeMem);
        memoryLineChart.data.datasets[3].data.push(metric.usedPercent);
        memoryLineChart.options.scales.x.time.unit = 'day';
      });
    } else {
      data.forEach(metric => {
        memoryLineChart.data.labels.push(metric.date);
        memoryLineChart.data.datasets[0].data.push(metric.totalMem);
        memoryLineChart.data.datasets[1].data.push(metric.usedMem);
        memoryLineChart.data.datasets[2].data.push(metric.freeMem);
        memoryLineChart.data.datasets[3].data.push(metric.usedPercent);
        memoryLineChart.options.scales.x.time.unit = 'minute';
      });
    }

    memoryLineChart.update();
  });
}

function loadCPUData(timeRange) {
  fetch(`v1/metrics/cpu?timeRange=${timeRange}`)
  .then(response => response.json())
  .then(data => {
    cpuLineChart.data.labels = [];
    cpuLineChart.data.datasets[0].data = [];
    cpuLineChart.data.datasets[1].data = [];

    if (timeRange === '7d' || timeRange === '30d') {
      const aggregatedData = aggregateDataByDay(data);
      aggregatedData.forEach(metric => {
        cpuLineChart.data.labels.push(metric.date);
        cpuLineChart.data.datasets[0].data.push(metric.cpuUsage);
        cpuLineChart.data.datasets[1].data.push(metric.cpuLoad);
        cpuLineChart.options.scales.x.time.unit = 'day';
      });
    } else {
      data.forEach(metric => {
        cpuLineChart.data.labels.push(metric.date);
        cpuLineChart.data.datasets[0].data.push(metric.cpuUsage);
        cpuLineChart.data.datasets[1].data.push(metric.cpuLoad);
        cpuLineChart.options.scales.x.time.unit = 'minute';
      });
    }

    cpuLineChart.update();
  });
}

function aggregateDataByDay(data) {
  const aggregatedData = {};
  data.forEach(metric => {
    const date = metric.date.split('T')[0];
    if (!aggregatedData[date]) {
      aggregatedData[date] = {
        totalMem: 0,
        usedMem: 0,
        freeMem: 0,
        usedPercent: 0,
        cpuUsage: 0,
        cpuLoad: 0,
        count: 0
      };
    }
    aggregatedData[date].totalMem += metric.totalMem;
    aggregatedData[date].usedMem += metric.usedMem;
    aggregatedData[date].freeMem += metric.freeMem;
    aggregatedData[date].usedPercent += metric.usedPercent;
    aggregatedData[date].cpuUsage += metric.cpuUsage;
    aggregatedData[date].cpuLoad += metric.cpuLoad;
    aggregatedData[date].count += 1;
  });

  return Object.keys(aggregatedData).map(date => {
    const metric = aggregatedData[date];
    return {
      date: date,
      totalMem: metric.totalMem / metric.count,
      usedMem: metric.usedMem / metric.count,
      freeMem: metric.freeMem / metric.count,
      usedPercent: metric.usedPercent / metric.count,
      cpuUsage: metric.cpuUsage / metric.count,
      cpuLoad: metric.cpuLoad / metric.count
    };
  });
}

function connect() {
  const socket = new SockJS("/ws");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log("Connected: " + frame);
    stompClient.subscribe("/topic/metrics", function (data) {
      const metricsData = JSON.parse(data.body);
      updateRealTimeCpuData(metricsData.cpuMetricResponse);
      updateRealTimeMemData(metricsData.memMetricResponse);
    });
  });
}

function updateRealTimeCpuData(cpuData) {
  realTimeCpuChart.data.labels.push(cpuData.date);
  realTimeCpuChart.data.datasets[0].data.push(cpuData.cpuUsage);
  realTimeCpuChart.data.datasets[1].data.push(cpuData.cpuLoad);
  realTimeCpuChart.update();
}

function updateRealTimeMemData(memData) {
  realTimeMemoryChart.data.labels.push(memData.date);
  realTimeMemoryChart.data.datasets[0].data.push(memData.totalMem);
  realTimeMemoryChart.data.datasets[1].data.push(memData.usedMem);
  realTimeMemoryChart.data.datasets[2].data.push(memData.freeMem);
  realTimeMemoryChart.data.datasets[3].data.push(memData.usedPercent);
  realTimeMemoryChart.update();
}

connect();
