document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM이 로드되었습니다."); // 이 줄 추가

    $('form').on('submit', function(event) {
        event.preventDefault(); // 폼 기본 동작 방지
        console.log("폼이 제출되었습니다."); // 이 줄 추가

        var formData = new FormData(this);
        var id = $(this).data('id');


        $.ajax({
            url: '/api/board-update/' + id,
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                window.location.href = '/';
            },
            error: function(xhr) {
                if (xhr.status === 400) {
                    alert('잘못된 요청입니다. 다시 시도해 주세요.');
                } else {
                    alert('서버와의 통신 중 오류가 발생했습니다. 다시 시도해 주세요.');
                }
            }
        });
    });
});


function previewImage(event) {
    var reader = new FileReader();
    reader.onload = function () {
        var output = document.getElementById('imagePreview');
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}