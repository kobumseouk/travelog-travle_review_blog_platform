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

    /*----------Create----------*/

    // 게시판 생성
    // 기존 게시판 추가 메서드
    @PostMapping("/board-create")
    public void createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        boardService.save(boardRequestDto);
    }

    // 사진과 함께 게시판을 추가하는 메서드
    @PostMapping(value = "/t-board-create/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> T_createBoard(@PathVariable("id") Long id,
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

    /*----------Read----------*/

    // 모든 대분류(regionMajor) 게시판 조회
    @GetMapping
    public List<BoardViewResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    // Todo: 사진과 함께 소분류 게시판 조회 - 완료
    @GetMapping("/boardlist-{regionMajor}")
    public List<BoardViewResponse> getMiddleBoards(@PathVariable("regionMajor") String regionMajor) {
        return boardService.getMiddleBoards(regionMajor);
    }

    /*----------Update----------*/

    // 게시판 수정
    @PutMapping("/board-update/{id}")
    public BoardUpdateResponseDto update(@PathVariable("id") Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    // Delete: 게시판 삭제
    @DeleteMapping("/board-delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
}
