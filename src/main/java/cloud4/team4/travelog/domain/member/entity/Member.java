package cloud4.team4.travelog.domain.member.entity;


import cloud4.team4.travelog.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column( name = "nickname")
    private String name;

    @Column(name ="password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();


}
