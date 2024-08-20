package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.CommentMapper;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentResponseDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.service.PostService;
import cloud4.team4.travelog.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;

    // 서비스 의존관계 주입 다시 확인!
    private final PostService postService;
    private final MemberService memberService;

    // READ
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponseDto>> findAllComments(@PathVariable("postId") Long postId) {

        List<CommentResponseDto> result = commentService.findAllByPostId(postId)
                .stream()
                .map(comment -> {
                    CommentResponseDto dto = CommentMapper.INSTANCE.toResponseDto(comment);
                    dto.setPostId(postId);
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(result);
    }

    // CREATE
    @PostMapping("/{postId}")
    public ResponseEntity<Void> addComment(@PathVariable("postId") Long postId,
                                              @RequestBody CommentRequestDto commentRequestDto) {
        // 아직 member와 post를 설정하지 않음
        Comment createdComment = CommentMapper.INSTANCE.toEntity(commentRequestDto);


        // member, post 설정 위해 단건 조회함
        MemberEntity member = memberService.findMemberById(commentRequestDto.getMemberId());        // 아직 불완전함 -> 메서드 다시 확인! (멤버만)
        Post post = postService.getPostById(postId);

        // 저장
        commentService.saveComment(member, post, createdComment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // UPDATE
    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("commentId") Long commentId,
                                                            @RequestBody CommentUpdateDto commentUpdateDto) {


        Comment updatedComment = commentService.updateComment(commentId, commentUpdateDto);

        CommentResponseDto commentResponseDto = CommentMapper.INSTANCE.toResponseDto(updatedComment);

        return ResponseEntity.ok(commentResponseDto);
    }

    // DELETE
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
