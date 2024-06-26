document.addEventListener('DOMContentLoaded', function() {
    const seats = document.querySelectorAll('.seat-select');
    const numOfPeopleDisplay = document.querySelector('.select-info h3');
    const paymentBtn = document.getElementById('payment-btn');
    const busId = document.getElementById('payment-btn').value;
    let numOfSeatsSelected = 0;

    function updateButtonState() {
        paymentBtn.disabled = numOfSeatsSelected === 0 || numOfSeatsSelected > 6;
    }

    // 페이지 로드시 초기화
    updateButtonState();

    seats.forEach(seat => {
        seat.addEventListener('change', function() {
            if (this.checked) {
                if(numOfSeatsSelected >= 6) {
                    alert("좌석은 최대 6개까지 선택 가능합니다.");
                    this.checked = false;
                    return
                }
                numOfSeatsSelected++;

            } else {
                numOfSeatsSelected--;
            }

            numOfPeopleDisplay.textContent = `${numOfSeatsSelected}명`;

            updateButtonState();
        });
    });

    paymentBtn.addEventListener('click', function() {
        const selectedSeats = [];


        seats.forEach(seat => {
            if (seat.checked) {
                selectedSeats.push(seat.value);
            }
        });

        if (paymentBtn.disabled){
            alert("좌석을 먼저 선택해 주세요.")
        }
        else{

            const xhr = new XMLHttpRequest();
            // for origin (변수 사용해서 url 만들어줘야함)
            const url = `/api/reservation/${busId}`;

            const data = JSON.stringify({
                seatNumbers: selectedSeats,
            });

            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        const reservationId = JSON.parse(xhr.responseText);
                        alert("결제 페이지로 이동합니다.");
                        window.location.href = `/${reservationId}/payment`; // 결제 페이지로 리다이렉트
                    } else {
                        alert('서버 오류가 발생했습니다.');
                    }
                }
            };

            xhr.open('POST', url, true);    //동기적으로 처리될 필요가 없다고 판단해서 우선은 비동기 처리 해놨습니다.
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(data);
        }
    });
});
