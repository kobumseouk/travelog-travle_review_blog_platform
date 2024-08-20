package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.dto.BoardCreateRequestDto;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
//@NoArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // Create
    public void save(BoardCreateRequestDto request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .region(request.getRegion())
                .build();
        boardRepository.save(board);
    }
}
