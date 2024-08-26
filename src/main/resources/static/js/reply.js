function toggleReplies(button) {
    const commentId = button.getAttribute('data-comment-id');
    const replyContainer = document.getElementById(`replyContainer-${commentId}`);

    if (replyContainer) {
        if (replyContainer.style.display === 'none' || replyContainer.style.display === '') {
            fetch(`/api/comment/${commentId}/reply`)
                .then(response => {
                    if (!response.ok) throw new Error('대댓글을 불러오는 데 실패했습니다.');
                    return response.json();
                })
                .then(replies => {
                    displayReplies(replies, commentId);
                    replyContainer.style.display = 'block';
                })
                .catch(error => {
                    console.error('에러:', error);
                    alert('대댓글을 불러오는 중 오류가 발생했습니다.');
                });
        } else {
            replyContainer.style.display = 'none';
        }
    } else {
        console.error(`Reply container with ID replyContainer-${commentId} not found.`);
    }
}

function displayReplies(replies, commentId) {
    const replyContainer = document.getElementById(`replyContainer-${commentId}`);
    replyContainer.classList.add('reply-container');
    if (!replyContainer) {
        console.error(`Reply container with ID replyContainer-${commentId} not found.`);
        return;
    }

    replyContainer.innerHTML = '';

    // 대댓글 제목 추가
    const titleElement = document.createElement('h2'); // 제목 요소 생성
    titleElement.textContent = '대댓글 보기'; // 제목 텍스트 설정
    titleElement.classList.add('reply-title'); // 필요 시 클래스 추가
    replyContainer.appendChild(titleElement); // 제목을 replyContainer에 추가

    const replyForm = createReplyForm(replyContainer.dataset.loginMemberId, commentId);
    const repliesWrapper = document.createElement('div');
    repliesWrapper.classList.add('replies-wrapper');

    if (replies.length === 0) {
        const noRepliesMessage = document.createElement('p');
        noRepliesMessage.classList.add('no-reply');
        noRepliesMessage.textContent = '대댓글이 없습니다.';
        repliesWrapper.appendChild(noRepliesMessage);
    } else {
        replies.forEach(reply => repliesWrapper.appendChild(createReplyElement(reply, replyContainer.dataset.loginMemberId, commentId)));
    }

    replyContainer.appendChild(replyForm);
    replyContainer.appendChild(repliesWrapper);

    replyForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const newReply = {
            memberId: replyForm.querySelector('input[type="hidden"]').value,
            content: replyForm.querySelector('textarea').value
        };

        fetch(`/api/comment/${commentId}/reply`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newReply)
        })
            .then(response => {
                if (response.status === 201) {
                    return fetch(`/api/comment/${commentId}/reply`); // 대댓글 목록 재조회
                }
                return response.text().then(errorMessage => { throw new Error('대댓글 추가 실패: ' + errorMessage); });
            })
            .then(response => response.json())
            .then(replies => {
                console.log("대댓글을 성공적으로 입력했습니다.");
                displayReplies(replies, commentId); // 새로 가져온 대댓글 목록으로 업데이트
            })
            .catch(error => {
                console.error('에러:', error);
                alert('대댓글 추가 중 오류가 발생했습니다.');
            });

        replyForm.querySelector('textarea').value = '';
    });
}

function createReplyForm(loginMemberId, commentId) {
    const replyForm = document.createElement('form');
    replyForm.classList.add('reply-form');

    const memberIdInput = document.createElement('input');
    memberIdInput.type = 'hidden';
    memberIdInput.value = loginMemberId;

    const contentInput = document.createElement('textarea');
    contentInput.placeholder = '대댓글 내용을 입력하세요';
    contentInput.required = true;

    const submitButton = document.createElement('button');
    submitButton.type = 'submit';
    submitButton.textContent = '대댓글 작성';

    replyForm.appendChild(memberIdInput);
    replyForm.appendChild(contentInput);
    replyForm.appendChild(submitButton);

    return replyForm;
}

function createReplyElement(reply, loginMemberId, commentId) {

    const replyElement = document.createElement('div');
    replyElement.classList.add('reply');

    const contentWrapper = document.createElement('div');
    contentWrapper.classList.add('content-wrapper');

    const header = document.createElement('div');
    header.classList.add('header');

    const nameElement = document.createElement('p');
    nameElement.classList.add('name');
    nameElement.textContent = reply.nickname;

    header.appendChild(nameElement);
    contentWrapper.appendChild(header);

    const commentWrapper = document.createElement('div');
    commentWrapper.classList.add('relative', 'mb-1', 'comment-box'); // comment-box 클래스를 추가하여 회색 박스 스타일 적용

    const commentBox = document.createElement('div');
    commentBox.textContent = reply.content;
    commentWrapper.appendChild(commentBox); // 대댓글 내용을 commentWrapper에 추가

    if (reply.memberId == loginMemberId) {
        const actionsWrapper = document.createElement('div');
        actionsWrapper.classList.add('actions');

        const editLink = document.createElement('a');
        editLink.href = '/comment/' + commentId + '/reply/'+ reply.id + '/update';
        const editIcon = document.createElement('span');
        editIcon.classList.add('material-symbols-outlined');
        editIcon.style.color = 'blue';
        editIcon.textContent = 'edit_note';
        editLink.appendChild(editIcon);
        actionsWrapper.appendChild(editLink);

        const deleteButton = document.createElement('button');
        deleteButton.type = 'button';
        deleteButton.id = reply.id;
        deleteButton.onclick = function() { deleteReply(this); };
        const deleteIcon = document.createElement('span');
        deleteIcon.classList.add('material-symbols-outlined');
        deleteIcon.style.color = 'red';
        deleteIcon.textContent = 'delete';
        deleteButton.appendChild(deleteIcon);
        actionsWrapper.appendChild(deleteButton);

        commentWrapper.appendChild(actionsWrapper); // actionsWrapper를 commentWrapper에 추가
    }

    contentWrapper.appendChild(commentWrapper); // commentWrapper를 contentWrapper에 추가

    // 날짜 형식 설정
    const dateElement = document.createElement('p');
    dateElement.classList.add('date');
    const dateToShow = reply.editedAt ? '(수정) ' + formatDate(reply.editedAt) : formatDate(reply.createdAt);
    dateElement.textContent = dateToShow; // 날짜 포맷 함수 호출
    contentWrapper.appendChild(dateElement);

    replyElement.appendChild(contentWrapper); // contentWrapper를 replyElement에 추가

    return replyElement;
}

function deleteReply(button) {

    const replyId = button.id;

    if (confirm('대댓글을 삭제하시겠습니까?')) {
        fetch(`/api/reply/${replyId}/delete`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.status === 204) {
                    alert('대댓글이 삭제되었습니다.');
                    // 페이지를 새로고침
                    location.reload();
                } else {
                    alert('대댓글 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('대댓글 삭제 중 오류가 발생했습니다.');
            });
    }
}

// 날짜 포맷 함수
function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}