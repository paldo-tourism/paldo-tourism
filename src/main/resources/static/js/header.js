function clickToGoLoginFormButton() {
  location.href="/login";
}

function clickToGoSignUpFormButton() {
  location.href="/signup";
}

function clickLogoutButton() {
  if (confirm('로그아웃 하시겠습니까?')) {
    fetch('/logout', {
      method: 'POST',

    })
        .then(response => {
          if (response.ok) {
            window.location.href = '/'; // 로그아웃 후 홈페이지로 이동
          } else {
            alert('로그아웃 실패, 다시 시도해 주세요.');
          }
        })
        .catch(error => console.error('Error:', error));
  }
}

function clickToGoBoardButton() {
  location.href=""
}