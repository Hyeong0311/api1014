package org.hyeong.api1014.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hyeong.api1014.common.exception.CommonExceptions;
import org.hyeong.api1014.member.domain.MemberEntity;
import org.hyeong.api1014.member.dto.MemberDTO;
import org.hyeong.api1014.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO authenticate(String email, String password) {

        Optional<MemberEntity> result = memberRepository.findById(email);

        MemberEntity member = result.orElseThrow(() -> CommonExceptions.READ_ERROR.get());

        String enPw = member.getPw();

        boolean match = passwordEncoder.matches(password, enPw);

        if(!match) {
            throw CommonExceptions.READ_ERROR.get();
        }

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(email);
        memberDTO.setPw(enPw);
        memberDTO.setRole(member.getRole().toString());

        return memberDTO;
    }
}
