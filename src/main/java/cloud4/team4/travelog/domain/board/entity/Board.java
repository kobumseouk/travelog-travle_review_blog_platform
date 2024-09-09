package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
    @Column(name = "region_major", nullable = false)
    private String regionMajor;


    // 여행지 소분류 - 강남, 강북 등.
    // 이 필드는 사용자 입력으로 받는다.
     @Column(name = "region_middle", nullable = false)
     private String regionMiddle;


    // 게시판에 대한 설명
    // 사용자가 게시판에 대한 설명을 입력한다.
    @Column(name = "description")
    private String description;


    // 게시판 분류
    @Column(name = "board_category", nullable = false)
    private String boardCategory;


    // 생성일
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;


    // 수정일
    @Column(name = "edited_at")
    @LastModifiedDate
    private LocalDateTime editedAt;


    // 이미지 저장 경로
    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;


    public void setImage(String imageName, byte[] imageData) {
        this.imageName = imageName;
        this.imageData = imageData;
    }


    public Board(String description, String regionMajor, String regionMiddle,
                 String boardCategory, LocalDateTime createdAt) {
        this.description = description;
        this.regionMajor = regionMajor;
        this.regionMiddle = regionMiddle;
        this.boardCategory = boardCategory;
        this.createdAt = createdAt;
    }

    // 이미지 저장 로직을 구현하기 위한 생성자.
    public Board(String imageName, byte[] imageData) {
        this.imageName = imageName;
        this.imageData = imageData;
    }


    public void updateDescription(String description) {
        this.description = description;
    }


    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(this.imageData);
    }

}
