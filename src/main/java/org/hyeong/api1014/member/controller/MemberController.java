package org.hyeong.api1014.member.controller;

import lombok.extern.log4j.Log4j2;
import org.hyeong.api1014.member.dto.TokenRequestDTO;
import org.hyeong.api1014.member.dto.TokenResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@Log4j2
public class MemberController {

    @PostMapping("makeToken")
    public ResponseEntity<TokenResponseDTO> makeToken(
            @RequestBody @Validated TokenRequestDTO tokenRequestDTO) {

        log.info("===============================");
        log.info("Make Token");

        return null;
    }
}
