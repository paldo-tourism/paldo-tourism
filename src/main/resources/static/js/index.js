//출발날짜를 선택할때 select-bar 날짜가 동적으로 변하는 함수
function updateDateOptions() {
  const dateSelect = document.getElementById('date-select'); //select-bar
  dateSelect.innerHTML = '';

  //"출발날짜를 선택해주세요"가 기본값으로 오게한다.
  const option = document.createElement('option');
  option.value = '';
  option.textContent = '출발날짜를 선택해주세요.';
  dateSelect.appendChild(option);


  for(let i = 0; i < 3; i++) {
    const date = new Date(); //날짜 객체 생성
    date.setDate(date.getDate() + i);
    const dateString = formatDate(date); //날짜 형식을 맞추기 위한 함수 실행

    const option = document.createElement('option');
    option.value = date.toISOString().substring(0,10).replace(/-/g, ''); //YYYY-MM-DD 형식으로 표기(ISO8601 형식의 문자열로 변환)
    console.log(option.value);
    option.textContent = dateString;
    dateSelect.appendChild(option);
  }
}

// date를 포매팅한 형식으로 return해주는 함수
function formatDate(date) {
  const weekDays = ['일요일','월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
  const day = weekDays[date.getDay()];
  const yyyy = date.getFullYear();
  const mm = String(date.getMonth() + 1).padStart(2,'0');//padStart를 통해 월을 항상 두 자리 숫자로 표현한다.
  const dd = String(date.getDate()).padStart(2,'0');

  return `${yyyy}년 ${mm}월 ${dd}일 ${day}`;
}

//메인화면에서 '조회하기'버튼을 눌렀을때 함수
function clickToGoTimeTableButton() {
  event.preventDefault();
  //사용자가 입력한 출발지,도착지,출발날짜,버스등급 값을 가져온다
  let depTerminalName = document.getElementById('depTerminalName').value
  let arrTerminalName = document.getElementById('arrTerminalName').value
  let depDate = document.getElementById('date-select').value;
  let busGrade = document.getElementById('bus-grade-select').value;

  if(!depTerminalName || !arrTerminalName || !depDate || !busGrade) {
    alert("모든 필드를 올바르게 입력해주세요.");
    return false;
  }

  if(depTerminalName === arrTerminalName) {
    alert("출발지와 도착지를 다르게 입력해주세요.")
    return false
  }

  const params = new URLSearchParams({
    depTerminalName: depTerminalName,
    arrTerminalName: arrTerminalName,
    depDate: depDate,
    busGrade: busGrade
  }).toString();

  fetch(`/timeTable?${params}`)
  .then(response => {
        if (!response.ok) {
          return response.json().then(error => {
            alert(error.message);
            throw new Error(error.message);
          });
        }
      }
  ).then(data => {
    window.location.href = `/timeTable?${params}`;
  })
  .catch(error => {
        console.error("에러 발생", error);
      }
  )

  return false;
}

document.addEventListener('DOMContentLoaded',updateDateOptions); //메인페이지가 로드되면 updateDateOptions 실행