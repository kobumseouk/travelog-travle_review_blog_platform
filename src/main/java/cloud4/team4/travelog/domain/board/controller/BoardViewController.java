package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
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
    public String getSubBoards(@PathVariable("regionMajor") String regionMajor, Model model) {
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

    @GetMapping("/{regionMajor}/add-board")
    public String getCreateBoard(@PathVariable("regionMajor") String regionMajor, Model model) {

        // regionMajor를 한글로 변환
        String regionMajorKorean = boardService.convertToKorean(regionMajor);
        model.addAttribute("regionMajorKorean", regionMajorKorean);
        return "createboard";
    }




}
