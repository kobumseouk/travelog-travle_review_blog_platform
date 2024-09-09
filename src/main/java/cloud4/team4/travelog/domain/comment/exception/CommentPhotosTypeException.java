package cloud4.team4.travelog.domain.comment.exception;

public class CommentPhotosTypeException extends Exception {

    // 기본 생성자
    public CommentPhotosTypeException() {
        super();
    }

    // 메시지를 받는 생성자
    public CommentPhotosTypeException(String message) {
        super(message);
    }
}
