document.addEventListener('DOMContentLoaded', function() {
    // 이미지 컨테이너에 대한 참조
    const imgContainer = document.querySelector('.img-container');

    // 이미지 확대 이벤트
    if (imgContainer) {
        // 이미지 컨테이너 내의 모든 이미지 링크에 대해 이벤트 리스너 추가
        imgContainer.querySelectorAll('.image-link:not(.listener-added)').forEach(link => {
             link.addEventListener('click', function(e) {
                e.preventDefault();
                openImage(this.dataset.base64);
            });
            link.classList.add('listener-added');
        });
    }
});

let imageWindow = null;

function openImage(base64) {
    if (imageWindow && !imageWindow.closed) {
        imageWindow.focus();
    } else {
        imageWindow = window.open();
        imageWindow.document.write('<img src="data:image/jpeg;base64,' + base64 + '" style="max-width:100%;"/>');
        imageWindow.document.close();
    }
}
