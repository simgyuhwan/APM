function connectToInfluxDB() {
  const url = document.getElementById("urlInput").value;
  const org = document.getElementById("orgInput").value
  const bucket = document.getElementById("bucketInput").value;
  const token = document.getElementById("tokenInput").value;

  const influxDBConfig = {
    url: url,
    org: org,
    bucket: bucket,
    token: token
  }

  fetch("/influxdb/clients", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(influxDBConfig)
  })
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      console.log("connected to influxDB");
      alert("연결 성공");
      window.location.replace(`/dashboard?org=${org}`);
    } else {
      alert(data.message);
    }
  })
  .catch(error => {
    console.error("Error:", error); // 오류 로그 추가
    alert("연결 실패");
  });

}

