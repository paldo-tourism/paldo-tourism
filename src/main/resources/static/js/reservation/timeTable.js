function clickLikeButton(likeBtn) {
  const result = confirm("해당 노선을 찜하시겠습니까? 예약시에는 오른쪽 '좌석 선택'을 눌러주세요.");
  let busId = likeBtn.value;

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
      likeBtn.classList.remove('red-heart');
      likeBtn.classList.add('empty-heart');
    }
  ).catch(error => {
    console.log(error);
  })
}

function addLike(url,likeBtn,method) {
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
        likeBtn.classList.remove('empty-heart');
        likeBtn.classList.add('like-heart');
      }
  ).catch(error => {
    console.log(error);
  })
}

function clickToGoSeatSelectPage() {

}