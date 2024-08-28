package cloud4.team4.travelog.domain.comment.exception;

public class CommentPhotosCountException extends Exception {

    // 기본 생성자
    public CommentPhotosCountException() {
        super();
    }

    // 메시지를 받는 생성자
    public CommentPhotosCountException(String message) {
        super(message);
    }
}
