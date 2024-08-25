package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    // post와 일대다 관계 설정
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    //  여행지 대분류 - 서울, 경기도, 충청도 등.
    @Column(name = "region_major")
    private String regionMajor;             //  미리 정의할 필드

    // 여행지 소분류 - 강남, 강북 등.
    @Column(name = "region_middle")
    private String regionMiddle;            // 사용자 입력으로 받을 필드

    // 게시판에 대한 설명
    @Column(name = "description")
    private String description;             // 사용자가 설명을 추가하도록 한다.

    // ToDo: 사진 추가
    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private BoardImage boardImage;

    //@Builder
    public Board(String description, String regionMajor, String regionMiddle) {
        this.description = description;
        this.regionMajor = regionMajor;
        this.regionMiddle = regionMiddle;
    }

    public void update(String description) {
        this.description = description;
    }

}
