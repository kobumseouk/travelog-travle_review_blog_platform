const datePattern = /^\d{4}-\d{2}-\d{2}$/;

function validateInputs() {
    const titleInput = document.getElementById('title');
    const periodStartInput = document.getElementById('periodStart');
    const periodEndInput = document.getElementById('periodEnd');
    const contentInput = document.getElementById('content');
    const submitBtn = document.getElementById('submit-btn');
    const errorMessage = document.getElementById('error-message');
    const periodStartError = document.getElementById('periodStartError');
    const periodEndError = document.getElementById('periodEndError');

    const isTitleValid = titleInput.value.trim() !== '';
    const isPeriodStartValid = datePattern.test(periodStartInput.value);
    const isPeriodEndValid = datePattern.test(periodEndInput.value);
    const isContentValid = contentInput.value.trim() !== '';

    let isFormValid = isTitleValid && isPeriodStartValid && isPeriodEndValid && isContentValid;

    if (!isPeriodStartValid) {
        periodStartError.style.display = 'block';
    } else {
        periodStartError.style.display = 'none';
    }

    if (!isPeriodEndValid) {
        periodEndError.style.display = 'block';
    } else {
        periodEndError.style.display = 'none';
    }

    if (isFormValid) {
        submitBtn.disabled = false;
        submitBtn.classList.remove('opacity-50', 'cursor-not-allowed');
        errorMessage.classList.add('hidden');
    } else {
        submitBtn.disabled = true;
        submitBtn.classList.add('opacity-50', 'cursor-not-allowed');
        errorMessage.textContent = '모든 필드를 올바르게 입력해주세요.';
        errorMessage.classList.remove('hidden');
    }

    return isFormValid;
}

// 전역 함수로 설정
window.validateForm = validateInputs;

document.addEventListener('DOMContentLoaded', function() {
    const titleInput = document.getElementById('title');
    const periodStartInput = document.getElementById('periodStart');
    const periodEndInput = document.getElementById('periodEnd');
    const contentInput = document.getElementById('content');

    // 각 입력 필드에서 'input' 이벤트가 발생할 때마다 유효성 검사 실행
    [titleInput, periodStartInput, periodEndInput, contentInput].forEach(input => {
        input.addEventListener('input', validateInputs);
    });

    // 페이지 로드 시 초기 상태에서 유효성 검사 실행
    validateInputs();
});