package cloud4.team4.travelog.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberDto {
    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

}
