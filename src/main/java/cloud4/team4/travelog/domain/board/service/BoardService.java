package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.dto.*;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.entity.BoardMapper;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void saveBoard(BoardRequestDto requestDto) {

        // Mapper를 사용해서 Entity로 변환한한다.
        Board board = BoardMapper.INSTANCE.toEntity(requestDto);

        try {
            // DTO에서 이미지를 추출하여 save
            boardImageService.saveImage(board, requestDto.getImage());

        } catch (Exception e) {
            // 예외 처리 결과 보류
            throw new RuntimeException("게시판 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    /* READ */

    // 사진을 포함한 모든 게시판 조회
    // 초기화면은 Board를 가져오지 않아도 되므로 사용하지 않는다.
//    public List<BoardViewResponse> getAllBoards() {
//        List<Board> boards = boardRepository.findAll();
//
//        List<BoardViewResponse> result = boards.stream()
//                .map(BoardViewResponse::new)
//                .collect(Collectors.toList());
//
//        return result;
//    }

    // 대분류(regionMajor)에 해당되는 중분류 게시판 조회
    public List<Board> getMiddleBoards(String regionMajor) {
        return boardRepository.findByRegionMajor(regionMajor);
    }



    /* UPDATE */

    // 게시판 설명을 수정한다.
    // Board 엔티티의 setter 메서드로 수정한다.
    @Transactional
    public void updateDescription(Long id, BoardUpdateRequestDto requestDto){
        Board board= boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. id= " + id));

        board.updateDescription(requestDto.getDescription());
    }



    // 게시판 이미지를 수정한다.
    // BoardImageService의 saveImage 메서드로 사진을 저장한다.
    @Transactional
    public void updateImage(Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. id= " + id));

        // 새로운 이미지를 저장하기 전에, DB에 저장된 이미지를 삭제한다.
        boardImageService.deleteImage(board);

        try {
            // 게시판에 이미지를 저장한다.
            boardImageService.saveImage(board, requestDto.getImage());
        } catch (Exception e) {
            // 예외 처리 결과 보류
            e.getMessage();
        }

    }



    /* DELETE*/

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        // DB에 저장된 게시판을 삭제한다.
        boardRepository.delete(board);
    }



    /* 기타 메서드 */

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 게시판을 찾을 수 없습니다."));

    }



    // 대분류 게시판의 regionMajor를 한국어로 변환하는 메서드
    public String convertToKorean(String regionMajor) {
        return switch (regionMajor.toLowerCase()) {
            case "seoul" -> "서울";
            case "busan" -> "부산";
            case "incheon" -> "인천";
            case "daegu" -> "대구";
            case "daejeon" -> "대전";
            case "gwangju" -> "광주";
            case "ulsan" -> "울산";
            case "jaeju" -> "제주";
            case "gyeonggi" -> "경기";
            default -> regionMajor;
        };
    }

}