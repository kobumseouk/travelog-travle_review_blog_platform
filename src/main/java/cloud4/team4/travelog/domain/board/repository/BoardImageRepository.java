package cloud4.team4.travelog.domain.board.repository;

import cloud4.team4.travelog.domain.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    List<BoardImage> findBoardImageByBoard_Id(Long id);
}
