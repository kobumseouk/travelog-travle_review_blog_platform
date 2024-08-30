4팀 트래블로그

도메인 별 설명



## 게시글

- 게시글 목록 조회
    - 원하는 도시의 지역의 게시판, 카테고리를 선택
        - 선택한 도시와 지역의 이름, 카테고리의 게시판의  게시글 조회
    - 도시 내에 있는 지역은 select option으로 선택가능하여 조회 가능
    - 그 옆 모두, 제목,  내용 중 select option을 선택하여 검색창에 게시글 검색 조회 기능
    - 게시글을 목록 정렬 방법을 select option 선택 가능
    - 조회된 게시글 리스트들은 10개까지 출력되며 게시글 번호, 제목, 작성자, 작성일, 조회, 추천수 대로 나타난다.
        - 게시글 목록에 페이징 기능을 통해 게시글이 10개가 넘어가는 경우 다음 페이지로 이동한다.
    - 게시글을 선택 시 해당 게시글 화면으로 넘어간다.
- 게시글 작성
    - 회원인 경우에만 우측의 게시글 작성 버튼이 보이고 사용할 수 있다.
    - 게시글은 제목, 여행시작과 종료일(날짜형식 YYYY-MM-DD 입력), 내용을 입력해야 하고 하나라도 입력하지 않는다면 validation 검사에 의해 게시글을 저장할 수 없다는 오류가 뜬다.
    - 파일 선택 버튼을 통해 사진만 첨부 가능하며 제한된 용량과 개수만큼 업로드 가능하다.
        - 그 이상 선택 시 오류
    - 작성 버튼을 누를 시 해당 게시판에 게시글이 생성되고 해당 게시판의 게시글 목록으로 이동한다.
    - 게시글 작성 취소 선택 시에는 해당 게시판의 게시글 목록으로 이동한다.

## 게시글 기능

- 게시글 조회
    - 제목, 작성자, 여행시작과 종료일, 작성일, 조회수와 추천수, 내용과 사진을 출력한다.
    - 사진 첨부 시 grid형태의 이미지 컨테이너 형태로 나열된다.
        - 사진 클릭 시 해당 사진을 새 창에 확대하여 출력한다.
    - 목록 버튼을 누르면 해당 게시판으로 이동한다.
    - 회원인 경우에만 추천 버튼이 표시되고 사용 가능하다.
        - 추천 버튼을 누르면 추천수가 증가하고 같은 회원이 다시 한번 추천 버튼을 누르면 추천수가 감소한다.
- 게시글 수정
    - 회원이면서 동시에 이 글의 작성자인 경우에만 수정 버튼이 보이고 누를 수 있으며 수정 버튼은 위의 작성 버튼의 형식과 동일하다.
    - 수정 버튼을 누를 시 사진을 제외한 기존 게시글의 내용을 작성란에 그대로 출력되며 직접 수정할 부분을 수정할 수 있다.
    - 기존의 첨부된 사진은 삭제되면 제한된 용량과 개수만큼 업로드 가능하다.
    - 수정 버튼을 누를 시 해당 게시판에 게시글이 수정되고 해당 게시글로 이동한다.
    - 게시글 작성 취소 선택 시에는 해당 게시판의 게시글 목록으로 이동한다.
- 게시글 삭제
    - 회원이면서 동시에 이 글의 작성자인 경우에만 삭제 버튼이 보이고 누를 수 있다.
    - 삭제 버튼을 누를 시 삭제 경고 알림이 위에 표시되면 확인을 누를 경우 해당 게시글을 삭제하고 게시글 목록 화면으로 넘어가고 취소 선택 시 게시글을 계속해서 조회할 수 있다.

## 구현 사진

### 게시글 목록 조회(비회원)

![스크린샷 2024-08-30 191356.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/c5dd0372-937b-40f4-bcc3-18e4e6b3947f/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-08-30_191356.png)

### 게시글 조회 시(비회원)

![스크린샷 2024-08-30 194449.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/77985a1c-9762-4495-ae7f-fcbecd89fd18/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-08-30_194449.png)

### 게시글 이미지 클릭시

![스크린샷 2024-08-30 194551.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/51fca152-4a7e-43bd-a1ab-2a5f4843b9d0/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-08-30_194551.png)





##댓글

---

## 구현 기능 및 사용법

- 댓글 작성 **(Create)**
    - 로그인 시 댓글 작성 가능
        - 평점 (1~5점) 선택 가능 (추가)
        - 사진 업로드 가능 (추가)
            - 최대 5장 업로드 가능
            - 각 사진은 16mb 이하만 가능
            - 사진이 아닌 파일은 업로드 불가능
    - 비로그인 시 “댓글 작성은 로그인 후 가능합니다!” 알림창 출력
- 댓글 출력 **(Read)**
    - 작성된 댓글 (평점, 내용, 사진)  출력
    - 이미지 돋보기 기능 (추가)
        - 작성된 댓글에 포함된 사진을 클릭시 기존 크기로 새창에서 열리며 확대 가능
    - 정렬 기능 (추가)
        - 한 페이지 당 출력 댓글 개수 선택 가능 (1개, 5개, 10개씩 보기 중 선택)
        - 최신순, 추천순으로 정렬 가능
    - 페이징 기능 (추가)
        - 선택된 정렬 기능 포함 페이징 기능
- 댓글 수정 **(Update)**
    - 댓글 작성자만 수정 가능
    - 작성과 동일하게 평점, 내용, 사진 수정 가능
    - 댓글 수정 시 댓글 작성 날짜가 아닌 수정 날짜로 출력
- 댓글 삭제 **(Delete)**
    - 댓글 작성자만 삭제 가능

- 대댓글 (추가)
    - 대댓글 작성 (Create)
        - 로그인 시 대댓글 작성 가능
    - 대댓글 출력 (Read)
        - 각 댓글 별 대댓글 보기 버튼 클릭시 아래에 대댓글 출력됨
        - 다시 대댓글 보기 버튼 클릭시 대댓글 숨김
    - 대댓글 수정 (Update)
        - 대댓글 작성자만 수정 가능
        - 대댓글 수정 시 대댓글 작성 날짜가 아닌 수정 날짜로 출력
    - 대댓글 삭제 (Delete)
        - 대댓글 작성자만 삭제 가능
- 게시글 평균 평점 (추가)
    - 댓글 작성 시 입력받은 평점의 평균값을 댓글 목록 최상단에 출력

## 구현 사진

- 게시글 페이지 중 댓글 (브라우저의 가로 너비에 따른 배치 변경)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/49120747-9073-4516-821f-51517c1f4815/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/20c8f606-36fa-4f9c-9be1-3c2a465bb1fd/image.png)

- 댓글 작성 예시
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/aa591dfe-e444-418f-bdca-fb683afaac72/image.png)
    

- 댓글 작성 결과

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/5b1712a0-4d2e-479c-9017-60c389314443/image.png)

- 댓글 수정

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/6e1d4286-318f-40bb-923e-be879dfa4911/image.png)

- 댓글 삭제

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/a336c672-1265-411e-a902-3e46304194b5/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/daa27fcc-a7f6-4a40-ac0a-3974248ed357/image.png)

- 대댓글 보기
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/f99f2990-9574-4ac1-bd16-daee8f5213cf/image.png)
    

- 대댓글 작성 예시

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/d668e817-016c-40b2-86ad-bd8a26103cfc/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/69050f9f-c558-44a5-acea-16366f5e8c77/image.png)

- 대댓글 수정

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/5248a4fe-0887-436d-b7ed-bcce5e7fc293/image.png)

- 대댓글 삭제

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/1a956c36-c450-4581-abe6-39476bbca5e5/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/a4561a44-2280-4b99-b78b-9856ceedfd93/image.png)

- 추천 (좋아요) 이전

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/44edd64f-9fa0-4de7-93b8-5b6b7fba422a/image.png)

- 추천 (좋아요) 이후

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/489a5af3-3e6a-48a9-bf66-e688f70ff320/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/97fda5b9-d444-4732-a7c9-732828bebd8a/image.png)

- 추천 (좋아요) 취소

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/2ff37081-dd59-454e-b928-8369f439cb47/image.png)

- 평점 기능 + 게시글 평균 평점 기능

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/9aed66b6-5632-4d3a-a47f-26659a1128b8/image.png)

- 댓글 페이징, 정렬

- 1개씩, 5개씩, 10개씩 보기 (예시는 1개씩 보기 + 페이징)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/ee7939e3-cac7-4911-af8d-561fb348b398/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/e38c31a0-73a0-4dcb-a823-99868e1bd18a/image.png)

- 최신순, 추천순 정렬

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/c64c2617-5305-4054-8be6-436e36485bdc/image.png)

- 사진 업로드 시 에러

- 6개 이상 업로드 시

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/662711aa-6407-4321-a6e9-8d929d524b7a/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/1f860c65-3aef-44d5-a1a9-4c5d0d9e81c2/image.png)

- 사진 이외의 파일 업로드 시

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/706c74a0-c464-429c-88ae-4b36b70f7f5e/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/26dce37a-5e17-4e70-97ae-47292ddbb2c8/image.png)

- 용량이 16MB 초과하는 사진 업로드 시

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/f9cb5dd0-5ce7-4d36-b974-40eb59321e7b/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d85fd78d-de88-4966-9b27-62e3e2e8c2a0/bb6507c1-a439-401f-ba30-d15321395aba/image.png)



## 게시판

# 게시판 기능

게시판 생성 (Create)

로그인을 한 사용자만 게시판을 생성할 수 있다.

필드는 다음과 같다.

- 대분류(regionMajor)
- 중분류(regionMiddle)
- 설명(description)
- 사진(Image)
- 게시판 분류(boardCategory)
→ 여행 후기 게시판 조회 화면에서 생성하면 ‘여행후기’로 자동 입력

이외 게시판 번호, 생성일, 수정일은 자동 생성된다.

게시판 조회 (Read)

로그인 여부 상관 없이, 모든 사용자가 게시판을 조회할 수 있다.
뷰는 다음과 같다.

- 메인화면(mainboard)
    - 미리 정의해둔 대분류 게시판 목록을 보여준다.
- 중분류 게시판(middleboard)
    - 대분류를 파라미터로, 일치하는 게시판 목록을 보여준다.
    - 게시판을 선택하면 해당 지역의 게시글 목록으로 이동한다.
    - 게시판 추가 버튼을 눌러, 게시판을 추가하는 뷰로 넘어갈 수 있다.

게시판 수정 (Update)

로그인을 한 사용자만 게시판을 수정할 수 있다.

중분류 게시판 목록 화면에서 버튼을 확인할 수 있다.

- 사진과 설명을 수정할 수 있다.
!!!단, 사진을 변경하지 않으면 수정이 되지 않는 버그가 있다!!!

게시판 삭제 (Delete)

로그인을 한 사용자만 게시판을 삭제할 수 있다.

마찬가지로 중분류 게시판 목록 화면에서 버튼을 확인할 수 있다.

# 게시글 디렉토리 구조

-Controller
BoardApiController
BoardViewController

-Service
BoardService
BoardImageService

-Repository
BoardRepository

-Board
BoardMapper

-Dto
BoardRequestDto
BoardUpdateRequestDto
BoardViewResponseDto
BoardDescRequestDto

-HTML
mainboard.html
middleboard.html
createboard.html
updateboard.html


## 댓글

