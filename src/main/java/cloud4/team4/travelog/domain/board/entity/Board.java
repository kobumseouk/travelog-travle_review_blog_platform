package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    // 사진 필드 - 미사용
//    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private BoardImage boardImage;

    // 게시판 분류
    @Column(name = "board_category")
    private String boardCategory;

    // 생성일
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    // 수정일
    @Column(name = "edited_at")
    @LastModifiedDate
    private LocalDateTime editedAt;

    @Column(name = "image_path")
    private String imagePath;


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    //@Builder
    public Board(String description, String regionMajor, String regionMiddle,
                 String boardCategory, LocalDateTime createdAt) {
        this.description = description;
        this.regionMajor = regionMajor;
        this.regionMiddle = regionMiddle;
        this.boardCategory = boardCategory;
        this.createdAt = createdAt;
    }

    public void update(String description) {
        this.description = description;
    }

}
