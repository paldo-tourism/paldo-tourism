const signupBtn = document.getElementById('signup-btn');
if (signupBtn) {
    signupBtn.addEventListener('click', event => {
        event.preventDefault(); // 기본 제출 동작을 방지
        fetch(`/api/signup`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: document.getElementById('email').value,
                nickName: document.getElementById('nickName').value,
                password: document.getElementById('password').value,
                phoneNumber: document.getElementById('phoneNumber').value
            }),
        }).then(response => {
            if (response.ok) {
                alert('등록 완료되었습니다');
                location.replace("/login");
            } else {
                alert('등록에 실패했습니다.');
            }
        })
    })
}
