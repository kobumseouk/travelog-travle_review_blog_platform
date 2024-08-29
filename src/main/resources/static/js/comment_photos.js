document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('file-input');
    const fileInfo = document.getElementById('file-info');

    fileInput.addEventListener('change', function() {
        const files = fileInput.files;
        fileInfo.innerHTML = ''; // 기존 내용을 지웁니다.

        if (files.length > 0) {
            const fileNames = Array.from(files).map(file => file.name).join(', ');
            fileInfo.innerHTML = `선택한 파일: ${fileNames}`;
        } else {
            fileInfo.innerHTML = '선택한 파일이 없습니다.';
        }
    });
});

$(document).ready(function() {
    $('form').on('submit', function(event) {
        event.preventDefault(); // 폼 기본 동작 방지

        // 폼 데이터를 FormData 객체로 생성
        var formData = new FormData(this);

        // postId를 URL에서 가져오기
        var postId = $(this).data('post-id');
        var boardId = $(this).data('board-id');
        var regionMajor = $(this).data('region-major');
        // AJAX 요청 보내기
        $.ajax({
            url: '/api/comment/' + postId, // postId를 포함한 URL
            type: 'POST',
            data: formData,
            processData: false, // FormData를 처리하지 않음
            contentType: false, // 콘텐츠 타입을 자동으로 설정
            success: function() {
                // 성공적으로 처리된 경우의 로직 (리다이렉트)
                window.location.href = "/board/" + regionMajor + '/' + boardId + '/posts/' + postId; // 리다이렉션
            },
            error: function(xhr) {
                // 에러 발생 시 (BAD_REQUEST 포함)
                if (xhr.status === 400) {
                    alert(xhr.responseText); // 서버에서 전달된 에러 메시지 출력
                } else {
                    alert('댓글 작성 중 오류가 발생했습니다. 다시 시도해 주세요.');
                }
            }
        });
    });
});

function validateForm() {
    const fileInput = document.getElementById('file-input');
    if (fileInput.files.length === 0) {
        // 파일이 선택되지 않은 경우
        fileInput.remove(); // 파일 입력 필드를 제거하거나 다른 처리를 할 수 있습니다.
        console.log("file 없음!!");
    }
    return true; // 폼 제출을 계속 진행
}

function openImage(base64) {
    const newWindow = window.open();
    newWindow.document.write('<img src="data:image/jpeg;base64,' + base64 + '" style="max-width:100%;"/>');
    newWindow.document.close();
}