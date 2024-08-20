package cloud4.team4.travelog.domain.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private long id;

    @Column(name = "board_title")
    private String title; // '즐거운 강릉 ~'과 같은 형식의 게시판 이름

    @Column(name = "region")
    private String region;

    @Column(name = "current_time")
    private LocalDateTime currentTime;

//    private long postId;


    @Builder
    public Board(String title, String region) {
        this.title = title;
        this.region = region;
    }

}
