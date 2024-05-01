document.getElementById('forgotForm').onsubmit = function(event) {
    event.preventDefault();
    var email = document.getElementById('email').value;
    var phoneNumber = document.getElementById('phoneNumber').value;
    fetch('/api/forgot-pw', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'email=' + encodeURIComponent(email) + '&phoneNumber=' + encodeURIComponent(phoneNumber)
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            alert(email+' 로 임시 비밀번호가 발송되었습니다!');
            location.replace("/login");
        })
        .catch(function(error) {
            alert('해당 정보가 없습니다!');
        });
};