package org.example.config.auth.dto;

import lombok.Getter;
import org.example.domain.user.User;

/*
* 인증된 사용자의 정보만 필요
* 애초에 세션에 사용자를 저장하는 것부터 ROLE_USER이니깐...
* GUEST는 저장해 둘 필요도 없음
* */

/* DTO, User 클래스를 그대로 사용하면 안 됨
 * 그대로 사용 -> "직렬화를 구현하지 않았다"라는 에러
 * 그렇다고 Entity 클래스인 User에 직렬화 코드까지 추가??
 * 엔티티 클래스에는 언제 다른 엔티티와 관계가 형성될 지 모름 (@OneToMany, @ManyToMany 등 자식 엔티티까지 가지면 직렬화 대상에 자식 엔티티까지 추가 -> 성능 이슈 & 부수 효과)
 * -> 직렬화 기능을 가진 세션 DTO 추가
 */
@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
