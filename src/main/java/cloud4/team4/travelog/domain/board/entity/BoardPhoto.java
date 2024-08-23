package cloud4.team4.travelog.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BoardPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_photos_id")
    private Long id;

    private String imagePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardPhoto(String imagePath, Board board) {
        this.imagePath = imagePath;
        this.board = board;
    }
}
