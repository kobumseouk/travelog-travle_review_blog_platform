package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardViewController {

    private final BoardService boardService;



    // 기본화면. 대분류 게시판 조회
    @GetMapping
    public String getMainBoards() {
        return "mainboard";
    }



    // 중분류 게시판 조회
    @GetMapping("/board/{regionMajor}")
    public String getMiddleBoards(@PathVariable("regionMajor") String regionMajor, Model model) {
        List<BoardViewResponse> middleboard = boardService.getMiddleBoards(regionMajor);

        // regionMajor를 한글로 변환
        String regionMiddleKorean = boardService.convertToKorean(regionMajor);

        // 모델에 middleboard와 regionMiddleKorean 값을 추가
        model.addAttribute("middleboard", middleboard);
        model.addAttribute("regionMajorKorean", regionMiddleKorean);

        return "middleboard";
    }



    // 게시판 생성 화면
    // 기존
//    @GetMapping("/{regionMajor}/add-board")
//    public String getCreateBoard() {
//        return "createboard";
//    }

    @GetMapping("/board-new/{regionMajor}")
    public String getCreateBoard(@PathVariable("regionMajor") String regionMajor, Model model) {

        // regionMajor를 한글로 변환
        String regionMajorKorean = boardService.convertToKorean(regionMajor);
        model.addAttribute("regionMajorKorean", regionMajorKorean);
        return "createboard";
    }

    @GetMapping("/board-update/{id}")
    public String getUpdateBoard(@PathVariable("id") Long id, Model model) {
        // 가정: BoardService에서 board 정보를 가져올 수 있다고 가정함
        Board board = boardService.getBoardById(id);

        // convertKorean 메서드를 사용하여 변환된 값을 모델에 추가
        String regionMajorKorean = boardService.convertToKorean(board.getRegionMajor());

        model.addAttribute("board", board);
        model.addAttribute("id", id);
        model.addAttribute("regionMajorKorean", regionMajorKorean);
        return "updateboard"; // 수정 화면의 템플릿 이름
    }





}
