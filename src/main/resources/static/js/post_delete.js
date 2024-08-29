// 삭제 기능
function deletePost(button) {
    console.log("del-post");
    const postId = button.getAttribute('data-postId');
    const regionMajor = button.getAttribute('data-region');
    const boardId = button.getAttribute('data-board');

    if (confirm('게시글을 삭제하시겠습니까?')) {
        fetch(`/api/posts/delete/${postId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('게시글이 삭제되었습니다.');
                    // 게시글 목록 페이지 이동
                    location.replace(`/boards/${regionMajor}/${boardId}/posts`);
                } else {
                    alert('게시글 삭제를 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('게시글 삭제 중 오류가 발생했습니다.');
            });
    }
}

