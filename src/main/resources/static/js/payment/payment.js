var IMP = window.IMP;
IMP.init("imp26578218");

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
            //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            if (rsp.success) {
                $.ajax({
                    url: '/verify/' + rsp.imp_uid,
                    method: "POST",
                }).done(function (data) {
                    // 가맹점 서버 결제 API 성공시 로직
                    if (rsp.paid_amount === data.response.amount) {
                        alert("결제 성공");
                        // console.log(rsp);
                        // console.log(data.response);

                        $.ajax(
                            {
                                url: '/mail',
                                method: "POST",
                                success: function (response) {
                                    // /mail 엔드포인트로의 POST 요청이 성공한 경우 처리
                                    console.log("메일 전송 성공:", response);
                                },
                                error: function (xhr, status, error) {
                                    // /mail 엔드포인트로의 POST 요청이 실패한 경우 처리
                                    console.log("메일 전송 실패:", xhr.responseText);
                                }
                            }
                        )

                        //결제 완료 페이지로 이동
                        $.ajax(
                            {
                                url: '/'+reservationData.reservationId+'/paymentComplete',
                                method: "GET"
                            }
                        )
                    }
                })
            } else {
                alert(rsp.error_msg);
            }
        }
    );
}