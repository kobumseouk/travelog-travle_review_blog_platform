package cloud4.team4.travelog.domain.board.controller;

import cloud4.team4.travelog.domain.board.dto.BoardImageRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardDescRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.service.BoardImageService;
import cloud4.team4.travelog.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update-description/{id}")
    public Long updateDescription(@PathVariable("id") Long id, @RequestBody BoardDescRequestDto requestDto){
        return boardService.updateDescription(id, requestDto);
    }



    // 게시판 사진 수정
    @PutMapping(value = "/update-image/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateImage(@PathVariable("id") Long id,
                                              @ModelAttribute BoardImageRequestDto requestDto) {
        try {
            boardService.updateImage(id, requestDto);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/"))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }



    /* DELETE */

    @DeleteMapping("/board-delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
}
