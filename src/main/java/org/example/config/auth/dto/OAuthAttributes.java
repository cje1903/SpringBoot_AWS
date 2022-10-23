package org.example.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.domain.user.Role;
import org.example.domain.user.User;

import java.util.Map;

/*
* OAuth2UserService (CustomOAuth2UserService) 를 통해 가져온 OAuth2User의 attribute를 담은 클래스
* */
@Getter
public class OAuthAttributes {  // User { id, name, email, picture, role }
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /* OAuth2User에서 반환하는 사용자의 정보는 Map이기 때문에 값 하나하나를 변환해서 주는 역할의 메서드 */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofGoogle(userNameAttributeName, attributes);
    }

    /* OAuth2User에서  <String (필드명) - Object (필드)> 맵을 주기 때문에 OAuthAttribute DTO로 변환*/
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)    // userNameAttributeName - OAuth2 로그인 진행 시 키가 되는 필드 값 (PK)
                .build();
    }

    /*
    * OAuthAttributes DTO를 User Entity로 변환
    * OAuthAttributes에서 Entity를 생성하는 시점은 처음 가입했을 때
    * 가입할 때 기본 권한을 GUEST로 설정 -> User의 Role을 Role.GUEST로 설정
    * */
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}









