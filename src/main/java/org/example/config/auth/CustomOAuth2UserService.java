package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.config.auth.dto.OAuthAttributes;
import org.example.config.auth.dto.SessionUser;
import org.example.domain.user.User;
import org.example.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/*
* 리소스 서버 (구글)에서 가져온 사용자 정보 (email, name, picture,...)를 기반으로 가입 & 정보 수정 & 세션 저장 등의 기능을 지원
* */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();  // OAuth2UserService<OAuth2UserRequest, OAuth2User> 인터페이스가 DefaultOAuth2UserService의 부모
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /* 현재 로그인 진행중인 서비스를 구분하는 코드, 구글 로그인만 있으면 상관없지만, 네이버 로그인이 추가될 경우 이를 구분해야 함 */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        /* OAuth2 로그인 진행 시 키가 되는 필드 값 (PK) */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        /* registrationId -> 어느 소셜로 로그인 중인지?,
        userNameAttributeName -> PK,
        oAuth2User.getAttributes() -> Map<String, Object> attributes
        */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));    // 세션에 SessionUser을 "user"이라는 이름으로 담기

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );

    }

    /*
    * 1. findByEmail로 이미 DB에 저장되어 있는 User인지 확인
    * 2. 있는 User -> OAuth2 로그인하면서 받은 정보로 User의 attributes를 수정
    * 3. 없는 User -> 받은 attributes로 새 User을 생성
    * */
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return user;
    }
}
