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


    /* READ */
    /* READ 뷰는 BoardViewController에 구현. */



    /* CREATE */

    // 사진과 함께 게시판을 추가하는 POST 메서드.
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

    // 게시판의 id로 검색한 후, 해당 게시판의 사진 / 설명을 수정하는 메서드.
    // html에서는 PUT Mapping 불가로 POST Mapping으로 구현한 뒤, js에서 처리했다.
    @PostMapping("/board-update/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") Long id,
                                            @ModelAttribute("requestDto") BoardUpdateRequestDto requestDto) {

        try {
            boardService.updateDescription(id, requestDto);

            // 이미지가 업로드되지 않으면 이미지 저장하지 않음.
            if (requestDto.getImage() != null) {
                boardService.updateImage(id, requestDto);
            }
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/")) // 수정 후 리다이렉트할 경로
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build(); // 예외 메시지는 사용자에게 직접 노출하지 않음
        }
    }



    /* DELETE */

    // 게시판 id에 해당하는 게시판을 삭제한다.
    @DeleteMapping("/board-delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }

}
