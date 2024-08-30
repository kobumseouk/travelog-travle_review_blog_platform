package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardUpdateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.service.BoardService;
import cloud4.team4.travelog.domain.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Board> middleboard = boardService.getMiddleBoards(regionMajor);

        // regionMajor를 한글로 변환
        String regionMajorKorean = boardService.convertToKorean(regionMajor);


        // 모델에 middleboard와 regionMiddleKorean 값을 추가
        model.addAttribute("middleboard", middleboard);
        model.addAttribute("regionMajorKorean", regionMajorKorean);
        return "middleboard";
    }



    @GetMapping("/board/{regionMajor}/qna/{postId}")
    public String getQnaPost(@PathVariable String regionMajor, @PathVariable String postId, Model model) {
        // postId가 숫자여야 하는지 확인하고 숫자가 아닌 경우 에러를 처리하거나
        // postId가 숫자라면 Long으로 변환하여 사용
        try {
            Long postIdLong = Long.parseLong(postId);
            // postIdLong을 사용하여 비즈니스 로직 수행
        } catch (NumberFormatException e) {
            // postId가 숫자가 아닌 경우 처리할 로직
            return "errorPage"; // 에러 페이지로 이동
        }
        return "qnaPage"; // 정상적인 경우 처리할 뷰
    }



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

        BoardUpdateRequestDto requestDto = new BoardUpdateRequestDto(board.getDescription(), board.getRegionMajor());

        // convertKorean 메서드를 사용하여 변환된 값을 모델에 추가
        String regionMajorKorean = boardService.convertToKorean(board.getRegionMajor());

        model.addAttribute("board", board);
        model.addAttribute("id", id);
        model.addAttribute("regionMajorKorean", regionMajorKorean);
        model.addAttribute("regionMajor", board.getRegionMajor());
        model.addAttribute("requestDto", requestDto);
        return "updateboard"; // 수정 화면의 템플릿 이름
    }

    // 로그인 여부 확인
    @ModelAttribute("loginMember")
    public Long loginMemberId(HttpSession session) {
        // 세션에서 로그인한 멤버의 id 값 가져옴
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        if(memberDto == null) return null;
        return memberDto.getId();
    }



}
