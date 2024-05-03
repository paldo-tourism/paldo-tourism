function clickToGoLoginFormButton() {
  location.href="/login";
}

function clickToGoSignUpFormButton() {
  location.href="/signup";
}

function clickLogoutButton() {
  const confirmation = confirm("로그아웃 하시겠습니까?");

  if(confirmation) {
    alert("로그아웃 되었습니다.");
    location.href = "";
  }

}

function clickToGoBoardButton() {
  location.href=""
}