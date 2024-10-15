package org.hyeong.api1014.member.repository;

import org.hyeong.api1014.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {


}
