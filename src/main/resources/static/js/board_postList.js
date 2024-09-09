document.addEventListener('DOMContentLoaded', function() {
    const regionMiddleSelect = document.getElementById('regionMiddleSelect');
    const searchTypeSelect = document.getElementById('searchTypeSelect');
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const sortSelect = document.getElementById('sortSelect');
    const sortButton = document.getElementById('sortButton');

    // 현재 URL에서 regionMajor와 boardId 추출
    const urlParts = window.location.pathname.split('/');
    const currentRegionMajor = urlParts[2];
    const currentBoardId = urlParts[3];

    // 게시판 이동 (regionMiddle 선택 시)
    regionMiddleSelect.addEventListener('change', function() {
        const selectedRegionMiddle = this.value;
        window.location.href = `/board/${currentRegionMajor}/${currentBoardId}/posts?regionMiddle=${selectedRegionMiddle}`;
    });

    searchBtn.addEventListener('click', search);
    sortButton.addEventListener('click', sort);

    // 제목, 내용으로 분류를 나누고 현재 정렬 방식으로 찾기
    function search() {
        const regionMiddle = regionMiddleSelect.value;
        const searchType = searchTypeSelect.value;
        const keyword = searchInput.value;
        const currentSort = sortSelect.value;

        let url = `/board/${currentRegionMajor}/${currentBoardId}/posts?sortBy=${currentSort}&regionMiddle=${regionMiddle}`;

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
        urlParams.set('regionMiddle', regionMiddleSelect.value);
        window.location.search = urlParams.toString();
    }
});