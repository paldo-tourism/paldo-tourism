document.getElementById('changePwdBtn').addEventListener('click', function() {
    var currentPassword = document.getElementById('currentPassword').value;
    var newPassword = document.getElementById('newPassword').value;

    fetch('/api/change-pw', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ currentPassword: currentPassword, newPassword: newPassword })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);  // 성공 메시지
                window.location.href = '/login';  // 로그인 페이지로 리디렉션
            } else {
                alert(data.message);  // 실패 메시지
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('네트워크 오류로 비밀번호 변경에 실패했습니다.');  // 네트워크 오류 처리
        });
});

document.addEventListener('DOMContentLoaded', function() {
    var deleteAccountBtn = document.getElementById('deleteAccountBtn');
    deleteAccountBtn.addEventListener('click', function(event) {
        var password = document.getElementById('password').value;
        if (!password) {
            alert('비밀번호를 입력해주세요.');
            return;
        }

        if (!confirm('정말로 계정을 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
            return;
        }

        fetch('/api/delete-account', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ password: password })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(data.message); // 성공 메시지
                    window.location.href = '/'; // 로그인 페이지로 리디렉션
                } else {
                    alert(data.message); // 실패 메시지
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('회원 탈퇴 처리 중 오류가 발생했습니다.');
            });
    });
});