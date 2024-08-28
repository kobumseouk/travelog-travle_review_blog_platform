document.addEventListener('DOMContentLoaded', function() {
    // 이미지 컨테이너에 대한 참조
    const imgContainer = document.querySelector('.img-container');

    if (imgContainer) {
        // 이미지 컨테이너 내의 모든 이미지 링크에 대해 이벤트 리스너 추가
        imgContainer.querySelectorAll('.image-link').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault(); // 기본 링크 동작 방지
                openImage(this.dataset.base64);
            });
        });
    }
});

function openImage(base64) {
    const newWindow = window.open();
    newWindow.document.write('<img src="data:image/jpeg;base64,' + base64 + '" style="max-width:100%;"/>');
    newWindow.document.close();
}