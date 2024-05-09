// 예매하기 버튼 클릭 이벤트
function doReservation(button) {
    const busId = button.getAttribute('data-id');
    window.location.href = `/seatSelect?busId=${busId}`;
}

// 취소 버튼 이벤트 핸들러
function cancelLike(button) {
    const busId = button.getAttribute('data-id');
    if (confirm('이 찜을 취소하시겠습니까?')) {
        fetch(`/api/likes?busId=${busId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('찜이 성공적으로 삭제되었습니다.');
                    window.location.reload(); // 페이지 새로고침
                } else {
                    alert('찜 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('네트워크 오류가 발생했습니다.');
            });
    }
}