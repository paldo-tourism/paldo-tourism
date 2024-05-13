function getCurrentDate() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();

    month = month < 10 ? '0' + month : month;
    day = day < 10 ? '0' + day : day;

    return year + '-' + month + '-' + day;
}

window.onload = function() {
    document.getElementById('travelDate').setAttribute('min', getCurrentDate());
};

document.getElementById('form').addEventListener('submit', function (event) {
    var travelDate = document.getElementById("travelDate").value;

    if (travelDate === "") {
        event.preventDefault(); // 제출 중단
        alert("여행 날짜를 입력해주세요.");
        return;
    }

    // 기본 폼 제출 동작을 중단
    event.preventDefault();

    // 로딩 화면 표시
    document.getElementById('loading').style.display = 'block';

    // 폼 데이터를 실제로 제출
    this.submit();
});


