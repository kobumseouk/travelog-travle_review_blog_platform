package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.CommentMapper;
import cloud4.team4.travelog.domain.comment.dto.CommentResponseDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;

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


}
