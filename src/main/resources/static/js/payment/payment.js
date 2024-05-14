var IMP = window.IMP;
IMP.init("imp26578218");

var pageLoadTime = new Date(); // 페이지 로드 시간 저장

document.addEventListener('DOMContentLoaded', function() {
    const paymentButton = document.querySelector('.submit-button'); // 결제 버튼 선택
    paymentButton.addEventListener('click', function(event) {
        const currentTime = new Date(); // 현재 시간
        const timeDifference = (currentTime - pageLoadTime) / 60000; // 분 단위로 차이 계산

        if (timeDifference > 5) { // 5분이 넘었는지 확인
            alert("5분이 초과되어 예약이 취소되었습니다. 다시 예약해주세요");
            event.preventDefault(); // 이벤트의 기본 동작을 중단
            window.location.href = '/';
        }
        else {
            const selectedIssue = document.querySelector('input[name="issue"]:checked');
            if (!selectedIssue) {
                alert("발급 방법을 선택해주세요."); // 발급 방법이 선택되지 않았다면 경고 메시지 표시
                event.preventDefault(); // 이벤트의 기본 동작을 중단 (결제 처리를 막음)
            } else {
                requestPay(); // 결제 처리 함수 호출
            }
        }
    });
});

function requestPay() {
    IMP.request_pay({
            pg: "kakaopay",
            pay_method: "EASY_PAY",
            merchant_uid: reservationData.reservationNumber,   // 주문번호
            name: "고속버스 예매",
            amount: reservationData.totalCharge,                         // 숫자 타입
            buyer_email: reservationData.userEmail,
            buyer_name: reservationData.userNickName,
            buyer_tel: reservationData.userPhone
        },
        function (rsp) {

            const issueMethod = document.querySelector('input[name="issue"]:checked').value; // 선택된 발급 방법
            console.log("선택된 발급 방법:", issueMethod);
            //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            if (rsp.success) {
                $.ajax({
                    url: '/verify/' + rsp.imp_uid + '/' + reservationData.reservationId,
                    method: "POST",
                }).done(function (data) {
                    // 가맹점 서버 결제 API 성공시 로직
                    if (rsp.paid_amount === data.response.amount) {
                        alert("결제 성공");

                        if(issueMethod === "email")
                        {
                            sendEmail();
                        }
                        else if(issueMethod === "message")
                        {
                            sendMessage()
                        }

                    } else {
                        alert("결제 금액 불일치");
                    }
                })
            } else {
                alert(rsp.error_msg);
            }
        }
    );
}


//메일 전송
function sendEmail() {
    $.ajax({
        url: '/mail',
        method: "POST",
        data: {reservationNumber: reservationData.reservationNumber},
        success: function (response) {
            // /mail 엔드포인트로의 POST 요청이 성공한 경우 처리
            console.log("메일 전송 성공:", response);
            redirectToPaymentCompletePage();
        },
        error: function (xhr, status, error) {
            // /mail 엔드포인트로의 POST 요청이 실패한 경우 처리
            console.log("메일 전송 실패:", xhr.responseText);
            redirectToPaymentCompletePage();
        }
    });
}

function sendMessage() {
    $.ajax({

        url: '/message',
        method: "POST",
        data: {reservationId: reservationData.reservationId},
        success: function (response) {
            // /mail 엔드포인트로의 POST 요청이 성공한 경우 처리
            console.log("문자 전송 성공:", response);
            redirectToPaymentCompletePage();
        },
        error: function (xhr, status, error) {
            // /mail 엔드포인트로의 POST 요청이 실패한 경우 처리
            console.log("문자 전송 실패:", xhr.responseText);
            redirectToPaymentCompletePage();
        }
    });
}

//결제 완료 페이지로 이동
function redirectToPaymentCompletePage() {
    window.location.href = '/' + reservationData.reservationId + '/paymentComplete';

}