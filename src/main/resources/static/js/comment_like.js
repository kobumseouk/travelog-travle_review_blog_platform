function likeComment(element) {
    var commentId = $(element).data('comment-id');
    var boardId = $(element).data('board-id');
    var postId = $(element).data('post-id');
    var regionMajor = $(element).data('region-major');

    fetch(`/api/comment/${commentId}/like`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.text().then(text => {
                    // 서버에서 반환한 메시지를 alert로 표시
                    alert(text); // 서버에서 반환한 텍스트 메시지
                    location.reload();
                });
            } else {
                alert('좋아요 처리 중 오류가 발생했습니다.');
                location.reload();
            }
        })
        .catch(error => {
            console.error('에러:', error);
            alert('서버와의 통신 중 오류가 발생했습니다.');
            location.reload();
        });

}

