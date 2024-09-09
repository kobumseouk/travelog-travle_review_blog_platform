package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.service.CommentLikeService;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

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
                                             @Valid @ModelAttribute CommentRequestDto commentRequestDto, HttpSession session) {

        // 로그인 하지 않은 경우
        if(loginMemberId(session) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("댓글 작성은 로그인 후 가능합니다!");
        }

        // 로그인된 멤버의 아이디 저장
        commentRequestDto.setMemberId(loginMemberId(session));

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

    // 댓글 좋아요
    @GetMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable("commentId") Long commentId, Model model) {

        // 로그인 한 멤버의 id
        Long memberId = (Long) model.getAttribute("loginMember");
        boolean likeBoolean = commentLikeService.likeComment(memberId, commentId);


        if(likeBoolean) {
            return ResponseEntity.status(HttpStatus.OK).body("해당 댓글에 좋아요를 눌렀습니다!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("좋아요를 취소했습니다!");
        }


//        return "redirect:/board/" + regionMajor + "/" + boardId + "/posts/" + postId;
    }

    @ModelAttribute("loginMember")
    public Long loginMemberId(HttpSession session) {
        // 세션에서 로그인한 멤버의 id 값 가져옴
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        if(memberDto == null) return null;
        return memberDto.getId();
    }
}
