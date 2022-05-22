package com.board.boardmaking.domain.global.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonUsernamePasswordAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter {
 /*AbstractAuthenticationProcessingFilter에는 인증 성공과 실패에 대한 대부분의 로직이 들어있음.
 * formLogin 방식과 비슷하게 데이터를 처리하는 방식만 json으로 바꾸어서 처리
 * */

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";

    private static final String HTTP_METHOD = "POST";

    private static final String CONTENT_TYPE = "application/json";

    private final ObjectMapper objectMapper;

    private static final String USERNAME_KEY="username";
    private static final String PASSWORD_KEY="password";

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);

        this.objectMapper = objectMapper;

    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if(request.getContentType() == null && !request.getContentType().equals(CONTENT_TYPE)){
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String username = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);

        /* DEFAULT_LOGIN_REQUEST_URL을 통해 "/login"으로 들어오는 요청에 대해서만 작동하도록 설정했습니다.
        * HTTP_METHOD, CONTENT_TYPE:POST에 json타입으로 오는 데이터만 처리하도록 설정했습니다.
        * json을 파싱하여 값을 읽어와야 하므로 ObjectMapper를 받아오겠습니다.
        * attemptAuthentication 메서드를 구현했습니다. username과 password를 받아와 FormLogin과 동일하게 UsernamePasswordAuthentication을
        * 사용했습니다. JSON으로 로그인하는 방식만 달라졌을뿐이지 username과 password를 사용하여 로그인하는 전략은 똑닽아서 굳이 따로 구현하지 않고 있는
        * 걸 가져다썼습니다.
        * return은 authenticationManager의 authenticate메서드를 실행했다. 여기서 사용되는 AuthenticationManager는 ProviderManager이고 이 또한 이후
        * SecurityConfig 파일에서 설정해주겠다.
        * */
    }
}
