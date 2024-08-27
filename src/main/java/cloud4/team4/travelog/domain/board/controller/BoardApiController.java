package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateResponseDto;
import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.service.BoardImageService;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/main")
public class BoardApiController {
    private final BoardService boardService;
    private final BoardImageService boardImageService;

    /* CREATE */

    // 사진과 함께 게시판을 추가
    @PostMapping(value = "/board-create/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> createBoard(@PathVariable("id") Long id,
                                              @ModelAttribute BoardRequestDto boardRequestDto) {
        try {
            boardService.saveBoard(id, boardRequestDto);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/"))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    /* READ */

    // 모든 대분류(regionMajor) 게시판 조회
    @GetMapping
    public List<BoardViewResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    // 사진과 함께 대분류(regionMajor)에 따른 게시판 조회
    @GetMapping("/boardlist-{regionMajor}")
    public List<BoardViewResponse> getMiddleBoards(@PathVariable("regionMajor") String regionMajor) {
        return boardService.getMiddleBoards(regionMajor);
    }


    /* UPDATE */

    // 게시판 수정
//    @PutMapping(value = "/t-board-update/{id}")
//    public ResponseEntity<String> T_updateBoard(@PathVariable("id") Long id,
//                                                @ModelAttribute BoardUpdateRequestDto requestDto) {
//        try {
//            boardService.T_updateBoard(id, requestDto);
//
//            return ResponseEntity.status(HttpStatus.FOUND)
//                    .location(URI.create("/"))
//                    .build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage());
//        }
//    }

    @PutMapping("/board-update/{id}")
    public BoardUpdateResponseDto update(@PathVariable("id") Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }


    /* DELETE */

    @DeleteMapping("/board-delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
}
