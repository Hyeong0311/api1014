package org.hyeong.api1014.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity {

    @Id
    private String email;

    private String pw;

    private MemberRole role;
}
