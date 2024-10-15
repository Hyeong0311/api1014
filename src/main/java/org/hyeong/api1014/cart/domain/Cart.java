package org.hyeong.api1014.cart.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hyeong.api1014.member.domain.MemberEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @OneToOne
    private MemberEntity member;
}
