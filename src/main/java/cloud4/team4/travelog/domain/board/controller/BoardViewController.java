package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardViewController {

    private final BoardService boardService;

    // 사진과 함께 대분류(regionMajor)에 따른 게시판 조회
//    @GetMapping("/{regionMajor}")
//    public String getSubBoards(@PathVariable("regionMajor") String regionMajor, Model model) {
//        List<BoardViewResponse> subboard = boardService.getMiddleBoards(regionMajor);
//
//        model.addAttribute("subboard", subboard);
//
//        return "subboard";
//    }

    // Todo: 여행 후기 화면 임시 뷰
    @GetMapping("/test")
    public String getSubBoards(Model model) {
        List<BoardViewResponse> seoulBoards = boardService.getMiddleBoards("서울");
        List<BoardViewResponse> busanBoards = boardService.getMiddleBoards("부산");

        model.addAttribute("seoulBoards", seoulBoards);
        model.addAttribute("busanBoards", busanBoards);

        return "subboard";
    }

    // Todo: 이게 기본화면입니다.
    @GetMapping
    public String getAllBoards(Model model) {
        List<BoardViewResponse> board = boardService.getAllBoards();

        model.addAttribute("board", board);

        return "testboard";
    }


    // 게시판 생성 화면
    @GetMapping("/board/board-new")
    public String getCreateBoard() {
        return "createboard";
    }

}
