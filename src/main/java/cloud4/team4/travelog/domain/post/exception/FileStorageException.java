package cloud4.team4.travelog.domain.post.exception;

public class FileStorageException extends RuntimeException {

  // 기본 생성자
  public FileStorageException(String message) {
    super(message);  // 메시지만 전달
  }

  // 메시지와 원인(다른 예외)을 함께 전달하는 생성자
  public FileStorageException(String message, Throwable cause) {
    super(message, cause);  // 메시지와 예외의 원인 전달
  }
}
