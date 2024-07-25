const memoryLineCtx = document.getElementById('memoryLineChart').getContext(
    '2d');
const cpuLineCtx = document.getElementById('cpuLineChart').getContext('2d');
const memoryPieCtx = document.getElementById('memoryPieChart').getContext('2d');
const cpuPieCtx = document.getElementById('cpuPieChart').getContext('2d');

const memoryLineChart = new Chart(memoryLineCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      {
        label: 'Total Memory',
        data: [],
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
        fill: false
      },
      {
        label: 'Used Memory',
        data: [],
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1,
        fill: false
      },
      {
        label: 'Free Memory',
        data: [],
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1,
        fill: false
      },
      {
        label: 'Used Percent',
        data: [],
        borderColor: 'rgba(153, 102, 255, 1)',
        borderWidth: 1,
        fill: false
      }
    ]
  },
  options: {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'minute'
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
        borderWidth: 1,
        fill: false
      },
      {
        label: 'CPU Load',
        data: [],
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
        fill: false
      }
    ]
  },
  options: {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'minute'
        }
      },
      y: {
        beginAtZero: true
      }
    }
  }
});

const memoryPieChart = new Chart(memoryPieCtx, {
  type: 'pie',
  data: {
    labels: ['Used Memory', 'Free Memory'],
    datasets: [{
      data: [],
      backgroundColor: ['rgba(255, 99, 132, 0.6)', 'rgba(54, 162, 235, 0.6)'],
      borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)'],
      borderWidth: 1
    }]
  }
});

const cpuPieChart = new Chart(cpuPieCtx, {
  type: 'pie',
  data: {
    labels: ['CPU Usage', 'CPU Load'],
    datasets: [{
      data: [],
      backgroundColor: ['rgba(255, 206, 86, 0.6)', 'rgba(75, 192, 192, 0.6)'],
      borderColor: ['rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
      borderWidth: 1
    }]
  }
});

const socket = new WebSocket('ws://localhost:8888/metrics');

socket.onmessage = function (event) {
  const data = JSON.parse(event.data);

  if (data.totalMem !== undefined) {
    updateMemoryCharts(data);
  } else if (data.cpuUsage !== undefined) {
    updateCpuCharts(data);
  }
};

function updateMemoryCharts(data) {
  memoryLineChart.data.labels.push(data.date);
  memoryLineChart.data.datasets[0].data.push(data.totalMem);
  memoryLineChart.data.datasets[1].data.push(data.usedMem);
  memoryLineChart.data.datasets[2].data.push(data.freeMem);
  memoryLineChart.data.datasets[3].data.push(data.usedPercent);
  memoryLineChart.update();

  memoryPieChart.data.datasets[0].data = [data.usedMem, data.freeMem];
  memoryPieChart.update();
}

function updateCpuCharts(data) {
  cpuLineChart.data.labels.push(data.date);
  cpuLineChart.data.datasets[0].data.push(data.cpuUsage);
  cpuLineChart.data.datasets[1].data.push(data.cpuLoad);
  cpuLineChart.update();

  cpuPieChart.data.datasets[0].data = [data.cpuUsage, data.cpuLoad];
  cpuPieChart.update();
}

//

function loadMemoryDate(timeRange) {
  fetch(`v1/metrics/mem?timeRange=${timeRange}`)
  .then(response => response.json())
  .then(data => {
    memoryLineChart.data.labels = [];
    memoryLineChart.data.datasets[0].data = [];
    memoryLineChart.data.datasets[1].data = [];
    memoryLineChart.data.datasets[2].data = [];
    memoryLineChart.data.datasets[3].data = [];

    data.forEach(metric => {
      memoryLineChart.data.labels.push(metric.date);
      memoryLineChart.data.datasets[0].data.push(metric.totalMem);
      memoryLineChart.data.datasets[1].data.push(metric.usedMem);
      memoryLineChart.data.datasets[2].data.push(metric.freeMem);
      memoryLineChart.data.datasets[3].data.push(metric.usedPercent);
    })

    memoryLineChart.update();
  })
}

function loadCPUDate(timeRange) {
  fetch(`v1/metrics/cpu?timeRange=${timeRange}`)
  .then(response => response.json())
  .then(data => {
    cpuLineChart.data.labels = [];
    cpuLineChart.data.datasets[0].data = [];
    cpuLineChart.data.datasets[1].data = [];

    data.forEach(metric => {
      cpuLineChart.data.labels.push(metric.date);
      cpuLineChart.data.datasets[0].data.push(metric.cpuUsage);
      cpuLineChart.data.datasets[1].data.push(metric.cpuLoad);
    });

    cpuLineChart.update();
  });
}

// 날짜 범위 조회 보류
// function loadHistoricalData() {
//   const start = new Date(document.getElementById('start').value).toISOString();
//   const stop = new Date(document.getElementById('stop').value).toISOString();
//
//   if (!start || !stop) {
//     alert('Please select both start and stop times');
//     return;
//   }
//
//   fetch(`/metrics/mem?start=${start}&stop=${stop}`)
//   .then(response => response.json())
//   .then(data => {
//     memoryLineChart.data.labels = [];
//     memoryLineChart.data.datasets[0].data = [];
//     memoryLineChart.data.datasets[1].data = [];
//     memoryLineChart.data.datasets[2].data = [];
//     memoryLineChart.data.datasets[3].data = [];
//
//     data.forEach(metric => {
//       memoryLineChart.data.labels.push(metric.date);
//       memoryLineChart.data.datasets[0].data.push(metric.totalMem);
//       memoryLineChart.data.datasets[1].data.push(metric.usedMem);
//       memoryLineChart.data.datasets[2].data.push(metric.freeMem);
//       memoryLineChart.data.datasets[3].data.push(metric.usedPercent);
//     });
//
//     memoryLineChart.update();
//   });
//
//   fetch(`/metrics/cpu?start=${start}&stop=${stop}`)
//   .then(response => response.json())
//   .then(data => {
//     cpuLineChart.data.labels = [];
//     cpuLineChart.data.datasets[0].data = [];
//     cpuLineChart.data.datasets[1].data = [];
//
//     data.forEach(metric => {
//       cpuLineChart.data.labels.push(metric.date);
//       cpuLineChart.data.datasets[0].data.push(metric.cpuUsage);
//       cpuLineChart.data.datasets[1].data.push(metric.cpuLoad);
//     });
//
//     cpuLineChart.update();
//   });
//
// }