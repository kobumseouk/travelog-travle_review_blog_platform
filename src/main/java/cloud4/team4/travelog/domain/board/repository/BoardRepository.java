package cloud4.team4.travelog.domain.board.repository;

import cloud4.team4.travelog.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // regionMajor로 게시판을 검색한다.
    List<Board>findByRegionMajor(String regionMajor);

}
