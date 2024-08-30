package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.*;
import cloud4.team4.travelog.domain.board.service.BoardImageService;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardApiController {
    private final BoardService boardService;
    private final BoardImageService boardImageService;


//    /* READ */
//
//    // 모든 대분류(regionMajor) 게시판 조회
//    @GetMapping
//    public List<BoardViewResponse> getAllBoards() {
//        return boardService.getAllBoards();
//    }
//
//
//
//    // 사진과 함께 대분류(regionMajor)에 따른 게시판 조회
//    @GetMapping("/boardlist-{regionMajor}")
//    public List<BoardViewResponse> getMiddleBoards(@PathVariable("regionMajor") String regionMajor) {
//        return boardService.getMiddleBoards(regionMajor);
//    }
//


    /* CREATE */

    // 사진과 함께 게시판을 추가
    // boards/board-new로 api 수정할 것
    @PostMapping(value = "/board/board-new", consumes = "multipart/form-data")
    public ResponseEntity<String> createBoard(@ModelAttribute BoardRequestDto boardRequestDto) {
        try {
            boardService.saveBoard(boardRequestDto);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/"))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }



    /* UPDATE */

    // 게시판 설명 수정
//    @PutMapping("/update-description/{id}")
//    public Long updateDescription(@PathVariable("id") Long id, @RequestBody BoardDescRequestDto requestDto){
//        return boardService.updateDescription(id, requestDto);
//    }


    @PostMapping("/board-update/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") Long id,
                                            @ModelAttribute("requestDto") BoardUpdateRequestDto requestDto) {

        System.out.println("requestDto = " + requestDto.getDescription());
        System.out.println("requestDto = " + requestDto.getRegionMajor());
        try {
            boardService.updateDescription(id, requestDto);

            boardService.updateImage(id, requestDto);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/")) // 수정 후 리다이렉트할 경로
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build(); // 예외 메시지는 사용자에게 직접 노출하지 않음
        }
    }



    /* DELETE */

    @DeleteMapping("/board-delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
}
