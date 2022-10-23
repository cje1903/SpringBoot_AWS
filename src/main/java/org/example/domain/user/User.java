package org.example.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;
    @Enumerated(EnumType.STRING)    //기본 - int로 된 숫자, 숫자로 저장되면 DB를 확인할 때 그 값이 무엇을 의미하는지 알 수 없음 -> STRING으로 저장
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){    // 유저 정보 수정 (나머지 필드들은 생성 이후 수정 불가)
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
