package cloud4.team4.travelog.domain.board.repository;

import cloud4.team4.travelog.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
