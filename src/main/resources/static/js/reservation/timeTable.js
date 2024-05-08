function clickLikeButton(likeBtn) {
  let result;
  let busId = likeBtn.value;

  if(likeBtn.classList.contains('red-heart')) {
    result = confirm("해당 노선 찜을 취소 하시겠습니까? 예약시에는 오른쪽 '좌석 선택'을 눌러주세요.");
  } else {
    result = confirm("해당 노선을 찜하시겠습니까? 예약시에는 오른쪽 '좌석 선택'을 눌러주세요.");
  }

  if(!result) {
    return false;
  }

  if(likeBtn.classList.contains('red-heart')) {
    let url = `/api/likes?busId=${busId}`;
    cancelLike(url,likeBtn,'DELETE');
  } else {
    let url = `/api/likes?busId=${busId}`
    addLike(url,likeBtn,'POST')
  }

}

function cancelLike(url,likeBtn,method) {
  checkLoginStatus();

  fetch(url, {
    method: method
  })
  .then(response => {
    if(!response.ok) {
      return response.json().then(error => {
        alert(error.message);
        throw new Error(error.message);
      });
    }
  })
  .then(data => {
      alert("해당노선이 찜목록에서 삭제되었습니다.")
      likeBtn.classList.remove('red-heart');
      likeBtn.classList.add('empty-heart');
    }
  ).catch(error => {
    console.log(error);
  })
}

function addLike(url,likeBtn,method) {
  checkLoginStatus()
  .then(() => {
    fetch(url, {
      method: method
    })
    .then(response => {
      if(!response.ok) {
        return response.json().then(error => {
          alert(error.message);
          throw new Error(error.message);
        });
      }
    })
    .then(data => {
      alert("해당노선이 찜목록에 추가되었습니다.")
      likeBtn.classList.remove('empty-heart');
      likeBtn.classList.add('red-heart');
    })
    .catch(error => {
      console.log(error);
    })
  })
  .catch(error => {
    console.log('Login required:', error);
  });
}

function checkLoginStatus() {
  return new Promise((resolve, reject) => {
    fetch('/api/auth/status')
    .then(response => {
      if(response.ok) {
        console.log("로그인된유저");
        resolve();  // 로그인이 확인되면 resolve 호출
      } else {
        alert("로그인이 필요합니다.");
        window.location.href = "/login";
        reject(new Error("로그인이 필요합니다.")); // 로그인이 필요하면 reject 호출
      }
    })
    .catch(error => {
      console.error(error);
      reject(error);  // 에러 발생 시 reject 호출
    });
  });
}

function clickToGoSeatSelectPage(selectBtn) {
  let busId = selectBtn.value;

  checkLoginStatus()
  .then(() => {
    window.location.href = `/seatSelect?busId=${busId}`;
  })
}