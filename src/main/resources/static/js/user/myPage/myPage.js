function redirectToTimeTable(buttonElement) {
    const depTerminal = buttonElement.getAttribute('data-depTerminal');
    const arrTerminal = buttonElement.getAttribute('data-arrTerminal');
    const depDate = buttonElement.getAttribute('data-depDate').replace(/-/g, '');
    const busGrade = buttonElement.getAttribute('data-busGrade');

    const url = `/timeTable?depTerminalName=${encodeURIComponent(depTerminal)}&arrTerminalName=${encodeURIComponent(arrTerminal)}&depDate=${depDate}&busGrade=${encodeURIComponent(busGrade)}`;
    window.location.href = url;
}

function cancelReservation(buttonElement) {
    const reservationId = buttonElement.getAttribute('data-id');
    if (confirm('정말로 예약을 취소하시겠습니까?')) {
        fetch(`/api/reservation/cancel/${reservationId}`, {
            method: 'PUT'
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('예약이 취소되었습니다.');
                    window.location.reload(); // 페이지를 새로고침하여 변경 사항 반영
                } else {
                    alert('예약 취소에 실패했습니다.');
                }
            })
            .catch(error => console.error('Error:', error));
    }
}