package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.dto.*;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.entity.BoardMapper;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    private final BoardImageService boardImageService;


    /* CREATE */

    @Transactional
    public void saveBoard(Long id, BoardRequestDto requestDto) {

        Board board = BoardMapper.INSTANCE.toEntity(requestDto);

        try {
            String imagePath = boardImageService.saveImage(requestDto.getImage());

            /* 사진을 같이 저장하기 때문에, 사진은 필수로 업로드해야 함 */

            // board에 imagePath 저장
            board.setImagePath(imagePath);

            // Mapper로 나머지 항목 저장
            boardRepository.save(board);

        } catch (Exception e) {
            // 예외 처리 결과 보류
            e.getMessage();
        }
    }


    /* READ */

    // 사진을 포함한 모든 게시판 조회
    public List<BoardViewResponse> getAllBoards() {
        List<Board> boards = boardRepository.findAll();

        List<BoardViewResponse> result = boards.stream()
                .map(BoardViewResponse::new)
                .collect(Collectors.toList());

        return result;
    }

    // 사진과 함께 대분류(regionMajor)에 따른 게시판 조회
    public List<BoardViewResponse> getMiddleBoards(String regionMajor) {
        List<Board> boards = boardRepository.findByRegionMajor(regionMajor);

        List<BoardViewResponse> result = boards.stream()
                .map(BoardViewResponse::new)
                .collect(Collectors.toList());

        return result;
    }


    /* UPDATE */

    // 게시판 설명 업데이트
    @Transactional
    public Long updateDescription(Long id, BoardDescRequestDto requestDto){
        Board board= boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. id= " + id));
        board.updateDescription(requestDto.getDescription());
        return id;
    }

    @Transactional
    public void updateImage(Long id, BoardImageRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. id= " + id));

        boardImageService.deleteImage(board.getImagePath());

        try {
            String imagePath = boardImageService.saveImage(requestDto.getImage());

            // board에 imagePath 저장
            board.setImagePath(imagePath);

            // Mapper로 나머지 항목 저장
            boardRepository.save(board);

        } catch (Exception e) {
            // 예외 처리 결과 보류
            e.getMessage();
        }

    }


    // 게시판 사진 업데이트
//    @Transactional
//    public void T_updateBoard(Long id, BoardUpdateRequestDto requestDto) {
//        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
//
//        board.update(requestDto.getDescription());
//
//        boardImageService.T_updateImage(requestDto.getImage(), board);
//    }



    /* DELETE*/

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        boardRepository.delete(board);
    }


    public BoardResponseDto findById(Long id){
        Board board= boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. id= " + id));
        return new BoardResponseDto(board);
    }
}