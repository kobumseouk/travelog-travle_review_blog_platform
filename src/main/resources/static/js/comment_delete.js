function deleteComment(button) {
    console.log("del");
    const commentId = button.getAttribute('data-id');

    if (confirm('댓글을 삭제하시겠습니까?')) {
        fetch(`/api/comment/delete/${commentId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('댓글이 삭제되었습니다.');
                    // 페이지를 새로고침
                    location.reload();
                } else {
                    alert('댓글 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('댓글 삭제 중 오류가 발생했습니다.');
            });
    }
}