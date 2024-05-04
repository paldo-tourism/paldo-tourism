document.addEventListener('DOMContentLoaded', function() {
    const seats = document.querySelectorAll('.seat-select');
    const numOfPeopleDisplay = document.querySelector('.select-info h3');
    const paymentBtn = document.getElementById('payment-btn');
    let numOfSeatsSelected = 0;

    function updateButtonState() {
        paymentBtn.disabled = numOfSeatsSelected === 0;
    }

    // 페이지 로드시 초기화
    updateButtonState();

    seats.forEach(seat => {
        seat.addEventListener('change', function() {
            if (this.checked) {
                console.log(`선택한 좌석: ${this.id}`);
                numOfSeatsSelected++;
            } else {
                console.log(`취소한 좌석: ${this.id}`);
                numOfSeatsSelected--;
            }

            console.log(`선택한 좌석 수: ${numOfSeatsSelected}`);
            numOfPeopleDisplay.textContent = numOfSeatsSelected;

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
            // const url = '/api/reservation/{busId}';

            //for test
            const url = '/api/reservation/1';
            const data = JSON.stringify({
                seats: selectedSeats,
            });

            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        //요청 과정 완료 응답을 받고, 서버에서 200 응답을 받으면 alert를 보여줍니다.
                        //page 이동 부분여기다가 추가하는게 맞는지,,,
                        alert("결제 페이지로 이동합니다.");
                    } else {
                        alert('서버 오류가 발생했습니다.');
                    }
                }
            };

            xhr.open('POST', url, true);    //동기적으로 처리될 필요가 없다고 판단해서 우선은 비동기 처리 해놨습니다.
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(data);
        }
        console.log("선택된 좌석: " + selectedSeats.join(', '));
    });
});
