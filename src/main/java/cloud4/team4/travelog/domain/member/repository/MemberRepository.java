package cloud4.team4.travelog.domain.member.repository;


import cloud4.team4.travelog.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByLoginId(String loginId);
    Optional<MemberEntity> findByNameAndEmail(String name, String email);
    Optional<MemberEntity> findByNameAndNum(String name, String phoneNumber);



}
