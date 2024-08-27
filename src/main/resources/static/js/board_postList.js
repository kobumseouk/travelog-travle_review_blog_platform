document.addEventListener('DOMContentLoaded', function() {
    const regionMajorSelect = document.getElementById('regionMajorSelect');
    const searchTypeSelect = document.getElementById('searchTypeSelect');
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const sortSelect = document.getElementById('sortSelect');
    const sortButton = document.getElementById('sortButton');

    sortButton.addEventListener('click', sort);

    // 게시판 이동
    regionMajorSelect.addEventListener('change', function() {
        window.location.href = `/board/${this.value}/posts`;
    });

    searchBtn.addEventListener('click', search);
    sortLatestBtn.addEventListener('click', () => sort('createdAt'));
    sortRecommendsBtn.addEventListener('click', () => sort('recommends'));

    // 제목, 내용, 세부 지역으로 분류를 나누고 현재 정렬 방식으로 찾기
    function search() {
        const regionMajor = regionMajorSelect.value;
        const searchType = searchTypeSelect.value;
        const keyword = searchInput.value;
        const currentSort = document.querySelector('.bg-blue-500').id === 'sortLatest' ? 'createdAt' : 'recommends';

        let url = `/board/${regionMajor}/posts?sortBy=${currentSort}`;

        if (searchType && keyword) {
            url += `&searchType=${searchType}&keyword=${keyword}`;
        }

        window.location.href = url;
    }

    // 정렬(최신순, 추천순)
    function sort(sortType) {
        const sortBy = sortSelect.value;
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('sortBy', sortBy);
        window.location.search = urlParams.toString();
    }


});