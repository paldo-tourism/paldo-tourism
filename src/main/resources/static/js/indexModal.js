//사용자가 지역별 터미널(전체,서울,인천/경기... 등을 눌렀을때)
function viewTerminalList(regionName, type) {

  //사용자가 클릭한 지역 li에 효과를 주기위한 코드
  const areaList = document.querySelectorAll('.area-list li');
  areaList.forEach(li => {
    li.classList.remove('active'); // 모든 li에서 active 클래스 제거
  });

  event.currentTarget.classList.add('active'); // 현재 클릭된 li에 active 클래스 추가


  const listContainer = document.getElementById(type === 'departure' ? 'departure-terminal-list' : 'arrival-terminal-list');
  console.log(`region: ${regionName}, type: ${type}`); // 로깅 추가

  fetch(`/api/terminals?regionName=${regionName}`)
  .then(response => {
    if (!response.ok) {
      throw new Error('네트워크 오류');
    }
    return response.json();
  })
  .then(data => {
    console.log(data); //로깅 추가
    listContainer.innerHTML = '';
    data.forEach(terminal => {
      const listItem = document.createElement('li');
      listItem.innerHTML = `<span onclick="selectTerminal('${terminal.name}', '${type}')">${terminal.name}</span>`;
      listContainer.appendChild(listItem);
    });
  })
  .catch(error => console.error('터미널 정보를 가져오는데 실패했습니다. ', error));
}

//지역 터미널을 클릭해서 나온 터미널 li를 클릭하면 '출발지','도착지'에 해당 터미널이름을 표시해준다
function selectTerminal(terminalName, type) {
  if (type === 'departure') {
    document.getElementById('selectedDeparture').textContent = terminalName;
  } else if (type === 'arrival') {
    document.getElementById('selectedArrival').textContent = terminalName;
  }
}

//출발지 모달에서 출발지를 선택하고 '선택 완료'를 눌렀을때
function confirmDeparture() {
  const selectedDeparture = document.getElementById('selectedDeparture').textContent;

  if(selectedDeparture === '') {
    alert("출발지를 선택해주세요.");
    return false; //모달창이 안닫히게 설정
  }

  document.getElementById('depTerminalName').value = selectedDeparture;
  closeModal('departureModal');
}

//도착지 모달에서 도착지를 선택하고 '선택 완료'를 눌렀을때
function confirmArrival() {
  const selectedArrival = document.getElementById('selectedArrival').textContent;

  if(selectedArrival === '') {
    alert("도착지를 선택해주세요.");
    return false; //모달창이 안닫히게 설정
  }

  document.getElementById('arrTerminalName').value = selectedArrival;
  closeModal('arrivalModal');
}

//모달을 닫는다
function closeModal(modalId) {
  document.getElementById(modalId).style.display = 'none';
}

//모달을 연다
function openModal(modalId) {
  document.getElementById(modalId).style.display = 'block';
}

//터미널을 검색했을때 결과를 return해주는 함수 ..리팩토링 필수..
function submitForm(type) {
  let inputTerminalName;
  let listContainer;

  if(type === 'departure') {
    inputTerminalName = document.getElementById('inputDepartureTerminalName').value;
    listContainer = document.getElementById('departure-terminal-list');
  } else if (type === 'arrival') {
    inputTerminalName = document.getElementById('inputArrivalTerminalName').value;
    listContainer = document.getElementById('arrival-terminal-list');
  }

  if(inputTerminalName === "") {
    alert("검색어를 입력해주세요");
    return false; // 빈 키워드일 경우 폼 제출 방지
  }

  fetch(`/api/search?terminalName=${inputTerminalName}`)
    .then(response => {
      if(!response.ok) throw new Error('네트워크 오류');
      return response.json();
    })
    .then(data => {

      listContainer.innerHTML = '';

      document.querySelectorAll('.area-list li.active').forEach(li => {
        li.classList.remove('active');
      });

      if(data.length > 0) {
        data.forEach(terminal => {
          const listItem = document.createElement('li');
          listItem.textContent = terminal.name;
          listItem.addEventListener('click', () => {
            document.getElementById(type === 'departure' ? 'selectedDeparture' : 'selectedArrival').textContent = terminal.name;
          });
          listContainer.appendChild(listItem);
        });
      } else {
        listContainer.innerHTML = '';
        //TODO 서버에서 발생한 에러 받아오기
        alert("해당 터미널은 존재하지 않는 터미널입니다.")
      }
    })
    .catch(error => {
      console.error("에러 발생",error);
      alert("검색 api 오류");
    })

  return false; //폼 제출 막기
}