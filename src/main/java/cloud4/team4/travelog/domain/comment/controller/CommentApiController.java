package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.service.CommentPhotosService;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;
    private final CommentPhotosService commentPhotosService;

    // READ -> 사용 x (Controller에서 조회)
//    @GetMapping("/{postId}")
//    public ResponseEntity<List<CommentResponseDto>> findAllComments(@PathVariable("postId") Long postId) {
//
//        List<CommentResponseDto> result = commentService.findAllByPostId(postId)
//                .stream()
//                .map(comment -> {
//                    CommentResponseDto dto = CommentMapper.INSTANCE.toResponseDto(comment);
//                    dto.setPostId(postId);
//                    dto.setPhotos(commentPhotosService.findPhotosPathByCommentId(comment.getId()));
//                    return dto;
//                })
//                .toList();
//
//        return ResponseEntity.ok(result);
//    }

    // CREATE
    @PostMapping(value = "/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<String> addComment(@PathVariable("postId") Long postId,
                                             @ModelAttribute CommentRequestDto commentRequestDto) {

        // 로그인된 멤버의 아이디 저장 => 세션 방식으로 변경해야함!
        commentRequestDto.setMemberId(loginMember());

        // 저장
        try {
            commentService.saveComment(postId, commentRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("comment created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Long commentId,
                                                @ModelAttribute CommentUpdateDto commentUpdateDto) {
        try {
            commentService.updateComment(commentId, commentUpdateDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("comment updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 테스트 코드 -> 세션등에 로그인 멤버 정보 담기는 것으로 바뀌면 코드 지울것!
    @ModelAttribute("loginMember")
    public Long loginMember() {
        return 1L;
    }
}
