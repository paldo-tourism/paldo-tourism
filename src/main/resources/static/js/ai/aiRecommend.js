
// 현재 날짜를 가져오는 함수
function getCurrentDate() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();

    month = month < 10 ? '0' + month : month;
    day = day < 10 ? '0' + day : day;

    return year + '-' + month + '-' + day;
}

// 페이지가 로드될 때 실행되는 함수
window.onload = function() {
    // input 요소의 min 속성에 현재 날짜를 설정합니다.
    document.getElementById('travelDate').setAttribute('min', getCurrentDate());
};
