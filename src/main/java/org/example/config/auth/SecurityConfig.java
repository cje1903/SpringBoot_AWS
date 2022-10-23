package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정들을 활성화 (application-oauth.properties)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해
                .and()
                    /* URL 별 권한 관리 설정의 진입점 */
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()  // 모두 permit - 열람 권한 줌
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // USER 권한을 가진 사람만 가능
                    .anyRequest().authenticated()   // 인증된 == 로그인한 사용자들만 가능
                .and()
                    /* 로그아웃 기능 설정의 진입점 */
                    .logout()
                    .logoutSuccessUrl("/")  // 로그아웃 성공시 / 주소로 이동
                .and()
                    /* OAuth2 로그인 기능 설정의 진입점 */
                    .oauth2Login()
                    .userInfoEndpoint() // 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                    .userService(customOAuth2UserService);  // 소셜 로그인 성공 시 후속 조치를 시행할 서비스 인터페이스의 구현체를 등록
                    // -> 리소스 서버 (소셜 서비스 - 구글)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
    }
}
