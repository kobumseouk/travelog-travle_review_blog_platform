document.addEventListener('DOMContentLoaded', function() {
    const regionMajorSelect = document.getElementById('regionMajorSelect');
    const searchTypeSelect = document.getElementById('searchTypeSelect');
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const sortSelect = document.getElementById('sortSelect');
    const sortButton = document.getElementById('sortButton');


    // 게시판 이동
    regionMajorSelect.addEventListener('change', function() {
        const currentRegionMajor = new URL(window.location.href).pathname.split('/')[2]; // 값 받기
        if (this.value !== currentRegionMajor) {        // 값이 변경되면 페이지 이동
            window.location.href = `/boards/${this.value}`;
        }
    });

    searchBtn.addEventListener('click', search);
    sortButton.addEventListener('click', sort);

    // 제목, 내용, 세부 지역으로 분류를 나누고 현재 정렬 방식으로 찾기
    function search() {
        const regionMajor = regionMajorSelect.value;
        const searchType = searchTypeSelect.value;
        const keyword = searchInput.value;
        const currentSort = sortSelect.value;

        let url = `/boards/${regionMajor}?sortBy=${currentSort}`;

        if (searchType && keyword) {
            url += `&searchType=${searchType}&keyword=${keyword}`;
        }

        window.location.href = url;
    }

    // 정렬(최신순, 추천순)
    function sort() {
        const sortBy = sortSelect.value;
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('sortBy', sortBy);
        window.location.search = urlParams.toString();
    }


});