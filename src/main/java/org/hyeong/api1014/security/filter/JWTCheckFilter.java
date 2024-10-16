package org.hyeong.api1014.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hyeong.api1014.security.util.JWTUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static java.lang.System.out;


@Log4j2
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        log.info("--------------------------------------");
        log.info("shouldNotFilter!!!");

        String uri = request.getRequestURI();

        if(uri.equals("/api/v1/member/makeToken")) return true;

        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("++++++++++++++++++++++++++++++++++++++++++");
        log.info("doFilterInternal!!! 다음 단계로 넘어감");

        log.info(request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        String token = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
        }else {

            makeError(response, Map.of("status", 401, "msg", "No Access Token"));

            return;
        }

        //JWT validate
        try{

            Map<String, Object> claims = jwtUtil.validateToken(token);
            log.info(claims);


            filterChain.doFilter(request, response);
        }catch(JwtException e){

            log.info(e.getClass().getName());
            log.info(e.getMessage());
            log.info("111111111111111111111111111");

            String classFullName = e.getClass().getName();
            String shortClassName = classFullName.substring(classFullName.lastIndexOf(".") + 1);

            makeError(response,
                    Map.of("status", 401, "msg", shortClassName));

            e.printStackTrace();
        }
    }


    private void makeError(HttpServletResponse response, Map<String, Object> map) {

        Gson gson = new Gson();

        String jsonStr = gson.toJson(map);

        response.setContentType("application/json");
        response.setStatus((int)map.get("status"));
        try{

            Writer writer = response.getWriter();

            out.println(jsonStr);
            out.close();
        }catch(IOException e) {

            throw new RuntimeException(e);
        }

    }
}
