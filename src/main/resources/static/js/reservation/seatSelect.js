document.addEventListener('DOMContentLoaded', function() {
    const seats = document.querySelectorAll('.seat-select');

    seats.forEach(seat => {
        seat.addEventListener('change', function() {
            if (this.checked) {
                console.log(`선택한 좌석: ${this.id}`);
            } else {
                console.log(`취소한 좌석: ${this.id}`);
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const seats = document.querySelectorAll('.seat-select');
    const numOfPeopleDisplay = document.querySelector('.select-info h3'); // 선택된 좌석 수를 표시할 요소를 가져옵니다.
    const paymentBtn = document.getElementById('payment-btn');
    let numOfSeatsSelected = 0;

    seats.forEach(seat => {
        seat.addEventListener('change', function() {
            if (this.checked) {
                numOfSeatsSelected++;
            } else {
                numOfSeatsSelected--;
            }
            console.log(`선택한 좌석 수: ${numOfSeatsSelected}`);

            numOfPeopleDisplay.textContent = numOfSeatsSelected;

            // 좌석을 선택할 때마다 버튼 활성화/비활성화 여부를 결정합니다.
            if (numOfSeatsSelected > 0) {
                paymentBtn.disabled = false; // 선택된 좌석이 있으면 버튼을 활성화합니다.
            } else {
                paymentBtn.disabled = true; // 선택된 좌석이 없으면 버튼을 비활성화합니다.
            }
        });
    });
});