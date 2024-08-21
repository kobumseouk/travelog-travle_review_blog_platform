package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.dto.BoardCreateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateResponseDto;
import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
//@NoArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    /*----------Create----------*/
    public void save(BoardCreateRequestDto request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .region(request.getRegion())
                .build();
        boardRepository.save(board);
    }

    /*----------Read----------*/
    // 전체 조회
    public List<BoardViewResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(BoardViewResponse::new)
                .collect(Collectors.toList());
    }

    /*----------Update----------*/
    @Transactional
    public BoardUpdateResponseDto update(Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        board.update(requestDto.getTitle(), requestDto.getRegion());
        return new BoardUpdateResponseDto(board);
    }




    /*----------Delete----------*/
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        boardRepository.delete(board);
    }



}