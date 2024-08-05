document.getElementById('connectButton').addEventListener('click', function() {
  const ipInput = document.getElementById('ipInput').value;
  const messageDiv = document.getElementById('message');

  // IP 주소 유효성 검사 (간단한 정규 표현식 사용)
  const ipPattern = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

  if (ipPattern.test(ipInput)) {
    messageDiv.textContent = `서버에 연결 중... (IP: ${ipInput})`;
    // 여기에 서버 연결 로직 추가
  } else {
    messageDiv.textContent = '유효한 IP 주소를 입력하세요.';
  }
});

document.getElementById("collectMetricsButton").onclick = function() {
  const urlParams = new URLSearchParams(window.location.search);
  const orgValue = urlParams.get('org');

  const collectButton = document.getElementById("collectMetricsButton");

  fetch(`/api/orgs/${orgValue}/metrics`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(response => response.json())
  .catch(error => console.log(error))

  // 버튼 상태 변경
  collectButton.innerText = "수집 중...";
  collectButton.classList.add("collecting");
}

document.getElementById("stopCollectingMetricsButton").onclick = function() {
  const collectButton = document.getElementById("collectMetricsButton");

  fetch(`/api/orgs/metrics/stop`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(response => response.json())
  .catch(error => console.log(error))

  collectButton.innerText = "수집 시작";
  collectButton.classList.add("collecting");
}
