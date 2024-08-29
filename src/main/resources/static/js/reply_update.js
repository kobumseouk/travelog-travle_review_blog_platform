document.addEventListener('DOMContentLoaded', function() {
    $('form').on('submit', function(event) {
        event.preventDefault(); // 폼 기본 동작 방지

        // 폼 데이터를 FormData 객체로 생성
        var formData = new FormData(this);

        var boardId = $(this).data('board-id');
        var postId = $(this).data('post-id');
        var replyId = $(this).data('reply-id');
        var regionMajor = $(this).data('region-major');

        // AJAX 요청 보내기
        $.ajax({
            url: '/api/reply/' + replyId,
            type: 'PUT',
            data: formData,
            processData: false, // FormData를 처리하지 않음
            contentType: false, // 콘텐츠 타입을 자동으로 설정
            success: function() {
                // 성공적으로 처리된 경우의 로직 (리다이렉트)
                window.location.href = "/board/" + regionMajor + "/" + boardId + "/posts/" + postId; // 리다이렉션
            },
            error: function(xhr) {
                // 에러 발생 시
                if (xhr.status === 400) {
                    alert(xhr.responseText); // 서버에서 전달된 에러 메시지 출력
                } else {
                    alert('서버와의 통신 중 오류가 발생했습니다. 다시 시도해 주세요.');
                }
            }
        });
    });
});
