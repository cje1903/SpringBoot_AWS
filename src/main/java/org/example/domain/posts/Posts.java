package org.example.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.BaseTimeEntity;

import javax.persistence.*;

// Entity 클래스에 절대 Setter을 만들지 말기, 값 변경이 필요하면 목적과 의도를 알 수 있게 메서드 만들기
// 그런데, Setter가 없는 상황에서 어떻게 값을 채워 DB에 삽입??
// 기본 구조는 *생성자를 통해* 값을 채운 후 DB에 삽입
// 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메서드를 호출 (목적과 의도를 알 수 있게 메서드)

// 여기선 생성자 대신 Builder 사용 (@Builder를 통해 제공되는 빌더 클래스)
// 생성자나 빌더나 생성 시점에 값을 채워주는 역할은 똑같음
// 하지만 생성자는 지금 채우는 필드가 무엇인지 명확하게 지정 X ( new Example(b,a)처럼 a, b 위치가 바뀌어도 코드 실행 전까지는 문제를 못 찾음 )
// 하지만? 빌더는 -> Example.builder().a(a).b(b).build() -> 명확하게 인지 가능

@Getter
@NoArgsConstructor
@Entity // DB의 테이블과 링크될 클래스임을 알림
public class Posts extends BaseTimeEntity { // -> createdDate, modifiedDate 필드 상속

    @Id // 해당 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙, IDENTITY - auto_increment 휴지좀...
    private Long id;    // MySQL 기준 Long -> bigint

    @Column(length = 500, nullable = false) // 길이를 500으로 늘림 (기본 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)    // 타입을 TEXT로 변환
    private String content;

    private String author;

    @Builder    // 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // 변경 메서드 (setter)
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
