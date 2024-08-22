package cloud4.team4.travelog.domain.member.repository;


import cloud4.team4.travelog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByNameAndEmail(String name, String email);
    Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);



}
