package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.CommentMapper;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentResponseDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.service.CommentPhotosService;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;
    private final CommentPhotosService commentPhotosService;

    // READ
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponseDto>> findAllComments(@PathVariable("postId") Long postId) {

        List<CommentResponseDto> result = commentService.findAllByPostId(postId)
                .stream()
                .map(comment -> {
                    CommentResponseDto dto = CommentMapper.INSTANCE.toResponseDto(comment);
                    dto.setPostId(postId);
                    dto.setPhotos(commentPhotosService.findPhotosPathByCommentId(comment.getId()));
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(result);
    }

    // CREATE
    @PostMapping(value = "/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<Void> addComment(@PathVariable("postId") Long postId,
                                           @ModelAttribute CommentRequestDto commentRequestDto) {
        // 저장
        commentService.saveComment(postId, commentRequestDto);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/")) // 리다이렉트할 URL 수정 필요
                .build();
    }

    // UPDATE
    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("commentId") Long commentId,
                                                            @ModelAttribute CommentUpdateDto commentUpdateDto,
                                                            @RequestParam(value = "photos", required = false) List<MultipartFile> photos) {

        Comment updatedComment = commentService.updateComment(commentId, commentUpdateDto, photos);

        return ResponseEntity.ok(CommentMapper.INSTANCE.toResponseDto(updatedComment));
    }

    // DELETE
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
