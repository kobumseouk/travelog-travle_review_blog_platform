document.addEventListener('DOMContentLoaded', function() {
    // 사진 첨부 처리 ( 파일 갯수 제한 + 파일 명 출력 )
    const fileInput = document.getElementById('file-input');
    const fileInfo = document.getElementById('file-info');

    fileInput.addEventListener('change', function() {
        const files = fileInput.files;
        fileInfo.innerHTML = '';  // 기존 내용을 지웁니다.

        if (files.length > 0) {
            if (files.length > 3) {
                alert('최대 3개의 사진만 업로드할 수 있습니다.');
                fileInput.value = '';  // 파일 선택 초기화
                return;
            }
            const fileNames = Array.from(files).map(file => file.name).join(', ');
            fileInfo.innerHTML = `선택한 파일: ${fileNames}`;
        } else {
            fileInfo.innerHTML = '선택한 파일이 없습니다.';
        }
    });
});

// 폼 제출 처리
$(document).ready(function() {
    $('form').on('submit', function(event) {
        event.preventDefault();

        var formData = new FormData(this);
        var postId = $(this).data('post-id');
        var regionMajor = $(this).data('region-major');
        var boardId = $(this).data('board-id');
        var url = postId ? `/api/posts/update/${postId}` : `/api/posts`;
        var method = postId ? 'PUT' : 'POST';  // postId 유무로 url과 수정, 작성 변경

        $.ajax({
            url: url,
            type: method,
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                        window.location.href = `/board/${regionMajor}/${boardId}/posts/${response.id}`;
                    },
            error: function(xhr) {
                console.log(xhr);
                alert(xhr.responseText || '게시글 저장 중 오류가 발생했습니다. 다시 시도해 주세요.');
            }
        });
    });
});


// 폼 유효성 검사 함수
function validateForm() {
    const fileInput = document.getElementById('file-input');
    if (fileInput.files.length === 0) {
        fileInput.remove();
        console.log("사진 파일 없음!!");
    }

    // 추가: 다른 필드의 유효성 검사도 여기서 수행
    return validateInputs(); // post_validation.js에서 정의된 함수 호출
}

