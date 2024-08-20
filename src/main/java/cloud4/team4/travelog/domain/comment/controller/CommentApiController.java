package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.CommentMapper;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentResponseDto;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.ExMember;
import cloud4.team4.travelog.domain.comment.entity.ExPost;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.comment.service.ExMemberService;
import cloud4.team4.travelog.domain.comment.service.ExPostService;
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

    // 예시 서비스 의존관계 주입 -> 변경 필요
    private final ExPostService exPostService;
    private final ExMemberService exMemberService;

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
    public ResponseEntity addComment(@PathVariable("postId") Long postId,
                                              @RequestBody CommentRequestDto commentRequestDto) {
        // 아직 member와 post를 설정하지 않음
        Comment createdComment = CommentMapper.INSTANCE.toEntity(commentRequestDto);

        // 예시 서비스 사용 -> 변경 필수!
        // member, post 설정 위해 단건 조회함
        ExMember member = exMemberService.findMemberById(commentRequestDto.getMemberId());
        ExPost post = exPostService.findPostById(postId);

        // 저장
        commentService.saveComment(member, post, createdComment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
