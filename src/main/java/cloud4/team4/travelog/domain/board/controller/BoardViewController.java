package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mainboard")
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping
    public String getMainBoard(Model model) {
        List<BoardViewResponse> board = boardService.getAllBoards();

        model.addAttribute("board", board);

        return "mainboard";
    }

    // 사진과 함께 대분류(regionMajor)에 따른 게시판 조회
//    @GetMapping("/{regionMajor}")
//    public String getSubBoards(@PathVariable("regionMajor") String regionMajor, Model model) {
//        List<BoardViewResponse> subboard = boardService.getMiddleBoards(regionMajor);
//
//        model.addAttribute("subboard", subboard);
//
//        return "subboard";
//    }

    // Todo: 임시 코드
    @GetMapping("/{regionMajor}")
    public String getSubBoards(@PathVariable("regionMajor") String regionMajor, Model model) {
        List<BoardViewResponse> seoulBoards = boardService.getMiddleBoards("서울");
        List<BoardViewResponse> busanBoards = boardService.getMiddleBoards("부산");

        model.addAttribute("seoulBoards", seoulBoards);
        model.addAttribute("busanBoards", busanBoards);

        return "subboard";
    }


}
