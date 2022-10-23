package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing  // JPA Auditing 활성화
@SpringBootApplication  // 스프링 부트의 자동 설정, 스프링 빈 읽기와 생성 모두 자동 설정
// 이 어노테이션이 있는 위치부터 읽음 -> 항상 프로젝트 최상단
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실행
        // 외부에 별도의 WAS 설치 X, 스프링 부트로 만들어진 Jar 파일로 실행하면 됨
        // 내장 WAS 사용을 권유
        // 외장 WAS -> 모든 서버는 WAS의 종류와 버전, 설정을 일치시켜야 하기 때문에 힘듬
        // 내장 WAS는 이 문제를 모두 해결
    }
}
