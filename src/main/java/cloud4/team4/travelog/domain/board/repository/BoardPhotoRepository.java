package cloud4.team4.travelog.domain.board.repository;

import cloud4.team4.travelog.domain.board.entity.BoardPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardPhotoRepository extends JpaRepository<BoardPhoto, Long> {

    List<BoardPhoto> findBoardPhotoByBoard_Id(Long id);
}
