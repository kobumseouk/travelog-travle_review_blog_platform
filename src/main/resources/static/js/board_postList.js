document.addEventListener('DOMContentLoaded', function() {
    const boardSelect = document.getElementById('boardSelect');
    const regionSelect = document.getElementById('regionSelect');
    const detailRegionInput = document.getElementById('detailRegion');
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const sortLatestBtn = document.getElementById('sortLatest');
    const sortRecommendsBtn = document.getElementById('sortRecommends');

    boardSelect.addEventListener('change', updateRegionOptions);
    searchBtn.addEventListener('click', searchPosts);
    sortLatestBtn.addEventListener('click', () => sortPosts('latest'));
    sortRecommendsBtn.addEventListener('click', () => sortPosts('recommends'));

    // 게시판에 따라 지역 옵션 선택
    function updateRegionOptions() {
        const selectedBoard = boardSelect.value;
        fetch(`/posts/regions?board=${selectedBoard}`)
            .then(response => response.json())
            .then(regions => {
                regionSelect.innerHTML = '<option value="">지역</option>';
                regions.forEach(region => {
                    const option = document.createElement('option');
                    option.value = region;
                    option.textContent = region;
                    regionSelect.appendChild(option);
                });
            });
    }

    // 내용 일치 일 때 검색 기능
    function searchPosts() {
	const searchTerm = searchInput.value;
        const board = boardSelect.value;
        const region = regionSelect.value;
        const detailRegion = detailRegionInput.value;

        const currentSort = document.querySelector('.sort-btn.active').id === 'sortLatest' ? 'latest' : 'recommends';

        window.location.href = `/posts?board=${board}&region=${region}&detailRegion=${detailRegion}&search=${searchTerm}&sort=${currentSort}`;
    }

    // 정렬 기능
    function sortPosts(sortType) {
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('sort', sortType);
        window.location.search = urlParams.toString();
    }

    // 페이징 기능
    document.querySelectorAll('.pagination a').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const urlParams = new URLSearchParams(window.location.search);
            urlParams.set('page', this.getAttribute('data-page'));
            window.location.search = urlParams.toString();
        });
    });

    // 초기 지역 옵션 로드
    updateRegionOptions();
});