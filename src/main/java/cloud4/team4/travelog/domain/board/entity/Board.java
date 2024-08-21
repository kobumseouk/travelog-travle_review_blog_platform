package cloud4.team4.travelog.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title")
    private String title; // '즐거운 강릉 ~'과 같은 형식의 게시판 이름

    @Column(name = "region")
    private String region;

//    오류로 인해 임시 주석
//    @Column(name = "current_time")
//    private LocalDateTime currentTime;

//    private long postId;

    @Builder
    public Board(Long id, String title, String region) {
        this.id = id;
        this.title = title;
        this.region = region;
    }

    public void update(String title, String region) {
        this.title = title;
        this.region = region;
    }

}
