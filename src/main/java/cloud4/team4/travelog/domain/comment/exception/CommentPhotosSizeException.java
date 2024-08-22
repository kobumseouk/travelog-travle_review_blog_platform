package cloud4.team4.travelog.domain.comment.exception;

public class CommentPhotosSizeException extends Exception {

    // 기본 생성자
    public CommentPhotosSizeException() {
        super();
    }

    // 메시지를 받는 생성자
    public CommentPhotosSizeException(String message) {
        super(message);
    }
}
