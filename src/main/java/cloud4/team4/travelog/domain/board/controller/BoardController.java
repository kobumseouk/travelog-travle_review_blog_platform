package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardCreateRequestDto;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public void createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        boardService.save(boardCreateRequestDto);
    }
}
