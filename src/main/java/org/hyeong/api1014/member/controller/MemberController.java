package org.hyeong.api1014.member.controller;

import lombok.extern.log4j.Log4j2;
import org.hyeong.api1014.member.dto.MemberDTO;
import org.hyeong.api1014.member.dto.TokenRequestDTO;
import org.hyeong.api1014.member.dto.TokenResponseDTO;
import org.hyeong.api1014.member.service.MemberService;
import org.hyeong.api1014.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Value("${org.zerock.accessTime}")
    private int accessTime;

    @Value("${org.zerock.refreshTime}")
    private int refreshTime;

    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("makeToken")
    public ResponseEntity<TokenResponseDTO> makeToken(
            @RequestBody @Validated TokenRequestDTO tokenRequestDTO) {

        log.info("===============================");
        log.info("Make Token");

        MemberDTO memberDTO =
                memberService.authenticate(tokenRequestDTO.getEmail(), tokenRequestDTO.getPw());

        log.info(memberDTO);

        Map<String, Object> claimMap =
                Map.of("email", memberDTO.getEmail(), "role", memberDTO.getRole());

        String accessToken = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setEmail(memberDTO.getEmail());

        return ResponseEntity.ok(tokenResponseDTO);
    }
}
