package cloud4.team4.travelog.domain.member.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column( name = "name")
    private String name;

    @Column(name ="password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

}
