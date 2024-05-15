let isEmailValid = false; // 이메일 중복 검사 결과를 저장하는 변수
document.addEventListener('DOMContentLoaded', function() {
    const emailInput = document.getElementById('email');
    const checkEmailBtn = document.getElementById('checkEmailBtn');
    const emailFeedback = document.getElementById('emailFeedback');
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 간단한 이메일 형식 검증

    const phoneInput = document.getElementById('phoneNumber');
    const phoneFeedback = document.getElementById('phoneFeedback');

    phoneInput.addEventListener('input', function() {
        const phonePattern = /^(01[016789]{1})[0-9]{4}[0-9]{4}$/; // 최대 11자리 숫자
        if (!phonePattern.test(phoneInput.value)) {
            phoneFeedback.textContent = '유효하지 않은 휴대폰 번호입니다.';
            phoneFeedback.style.color = 'red';
        } else {
            phoneFeedback.textContent = '유효한 휴대폰 번호입니다.';
            phoneFeedback.style.color = 'green';
        }
    });

    phoneInput.addEventListener('keypress', function(event) {
        const charCode = event.charCode;
        if (charCode !== 0 && (charCode < 48 || charCode > 57)) {
            event.preventDefault(); // 숫자가 아닌 입력을 방지
        }
    });

    // 이메일 형식 검증 이벤트 리스너
    emailInput.addEventListener('input', function() {
        if (emailInput.value.length > 0) {
            if (!emailPattern.test(emailInput.value)) {
                emailFeedback.textContent = '유효한 이메일 주소를 입력해주세요.';
                emailFeedback.style.color = 'red';
            } else {
                emailFeedback.textContent = ''; // 형식이 맞으면 메시지를 지웁니다.
            }
        } else {
            emailFeedback.textContent = ''; // 입력값이 없으면 메시지를 지웁니다.
        }
    });

    // 이메일 중복 검사 이벤트 리스너
    checkEmailBtn.addEventListener('click', function() {
        const email = emailInput.value;
        if (!emailPattern.test(email)) {
            emailFeedback.textContent = '유효한 이메일 주소를 입력해주세요.';
            emailFeedback.style.color = 'red';
        } else {
            // 이메일 형식이 유효하면 서버에 중복 검사 요청
            fetch(`/api/check-email?email=${encodeURIComponent(email)}`, {
                method: 'GET'
            }).then(response => response.json())
                .then(data => {
                    if (data.available) {
                        emailFeedback.textContent = '사용 가능한 이메일입니다.';
                        emailFeedback.style.color = 'green';
                        isEmailValid = true;
                    } else {
                        emailFeedback.textContent = '이미 사용 중인 이메일입니다.';
                        emailFeedback.style.color = 'red';
                        isEmailValid = false;
                    }
                }).catch(error => {
                console.error('Error:', error);
                emailFeedback.textContent = '서버 오류가 발생했습니다.';
                emailFeedback.style.color = 'red';
                isEmailValid = false;
            });
        }
    });
});

document.addEventListener('DOMContentLoaded', function (){
    document.getElementById('passwordConfirm').addEventListener('keyup', function (event){
        var password = document.getElementById('password').value;
        var passwordConfirm = document.getElementById('passwordConfirm').value;
        var pw_check_msg = document.getElementById('pw_check_msg');

        if (password === passwordConfirm){
            pw_check_msg.textContent = '비밀번호가 일치합니다';
            pw_check_msg.style.color = 'green';
        }
        else{
            pw_check_msg.textContent = '비밀번호가 일치하지 않습니다.';
            pw_check_msg.style.color = 'red';
        }
    });
});


let isNickNameValid = false;
document.getElementById('checkNicknameBtn').addEventListener('click', function() {
    const nickname = document.getElementById('nickName').value;
    if (nickname) {
        fetch(`/api/check-nickname?nickname=${encodeURIComponent(nickname)}`, {
            method: 'GET'
        }).then(response => response.json())
            .then(isAvailable => {
                const nicknameStatus = document.getElementById('nicknameStatus');
                if (isAvailable) {
                    nicknameStatus.textContent = '사용 가능한 닉네임입니다.';
                    nicknameStatus.style.color = 'green';
                    isNickNameValid = true;
                } else {
                    nicknameStatus.textContent = '이미 사용 중인 닉네임입니다.';
                    nicknameStatus.style.color = 'red';
                    isNickNameValid = false;
                }
            }).catch(error => {
            console.error('Error:', error);
            isNickNameValid = false;
        });
    } else {
        alert('닉네임을 입력하세요.');
    }
});

// 회원가입 버튼 클릭 이벤트
const signupBtn = document.getElementById('signup-btn');
signupBtn.addEventListener('click', function(event) {
    event.preventDefault(); // 폼 기본 제출 방지

    const email = document.getElementById('email').value;
    const nickName = document.getElementById('nickName').value;
    const password = document.getElementById('password').value;
    const passwordConfrim = document.getElementById('passwordConfirm').value;
    const phoneNumber = document.getElementById('phoneNumber').value;

    if(!email) {
        alert('이메일을 입력하세요.');
        return;
    } else if(!nickName) {
        alert('닉네임을 입력하세요.');
        return;
    } else if(!password) {
        alert('비밀번호를 입력하세요.');
        return;
    } else if(!phoneNumber) {
        alert('전화번호를 입력하세요.');
        return;
    } else if(passwordConfrim !== password) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    } else if (!/^(01[016789]{1})[0-9]{4}[0-9]{4}$/.test(phoneNumber)) {
        alert('유효하지 않은 휴대폰 번호입니다.');
        return;
    }

    if (!isEmailValid) {
        alert('이메일 중복 확인이 필요합니다.');
    } else if (!isNickNameValid) {
        alert('닉네임 중복 확인이 필요합니다.');
    } else {
        // 회원가입 처리
        const formData = {
            email: document.getElementById('email').value,
            nickName: document.getElementById('nickName').value,
            password: document.getElementById('password').value,
            phoneNumber: document.getElementById('phoneNumber').value
        };
        fetch('/api/signup', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)
        }).then(response => {
            if (response.ok) {
                alert('등록 완료되었습니다');
                location.replace("/login");
            } else {
                alert('등록에 실패했습니다.');
            }
        }).catch(error => {
            console.error('Error:', error);
        });
    }
});
