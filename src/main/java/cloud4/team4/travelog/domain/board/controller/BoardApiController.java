package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardCreateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateResponseDto;
import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class BoardApiController {
    private final BoardService boardService;

    /*----------Create----------*/

    // 게시판 생성
    @PostMapping("/board-create")
    public void createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        boardService.save(boardCreateRequestDto);
    }

    /*----------Read----------*/

    // 모든 대분류(regionMajor) 게시판 조회
    @GetMapping
    public List<BoardViewResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    // 모든 소분류(regionMiddle) 게시판 조회
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
