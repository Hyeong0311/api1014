package org.hyeong.api1014.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.hyeong.api1014.member.repository.MemberRepository;
import org.hyeong.api1014.product.repository.ProductRepository;

@Component
@Log4j2
@RequiredArgsConstructor
@Order(2)
public class ProductLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        log.info("product loader.................");
        log.info("product loader.................");log.info("product loader.................");
        log.info("product loader.................");
        log.info("product loader.................");
        log.info("product loader.................");

    }
}