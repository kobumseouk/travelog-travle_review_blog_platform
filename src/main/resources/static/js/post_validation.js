document.addEventListener('DOMContentLoaded', function() {
    const titleInput = document.getElementById('title');
    const periodStartInput = document.getElementById('periodStart');
    const periodEndInput = document.getElementById('periodEnd');
    const contentInput = document.getElementById('content');
    const submitBtn = document.getElementById('create-btn') || document.getElementById('modify-btn');
    const errorMessage = document.getElementById('error-message');

    function validateInputs() {
        const isTitleValid = titleInput.value.trim() !== '';
        const isPeriodStartValid = periodStartInput.value !== '';
        const isPeriodEndValid = periodEndInput.value !== '';
        const isContentValid = contentInput.value.trim() !== '';

        const isFormValid = isTitleValid && isPeriodStartValid && isPeriodEndValid && isContentValid;

        if (isFormValid) {
            submitBtn.disabled = false;
            submitBtn.classList.remove('opacity-50', 'cursor-not-allowed');
            errorMessage.classList.add('hidden');
        } else {
            submitBtn.disabled = true;
            submitBtn.classList.add('opacity-50', 'cursor-not-allowed');
            errorMessage.textContent = '모든 필드를 입력해주세요.';
            errorMessage.classList.remove('hidden');
        }

        return isFormValid;
    }

    [titleInput, periodStartInput, periodEndInput, contentInput].forEach(input => {
        input.addEventListener('input', validateInputs);
    });

    validateInputs(); // 초기 상태 체크

});

// 다른 파일에서 호출할 수 있도록 전역 함수로 선언 ( post_update.js에서 호출 )
window.validateForm = validateInputs;