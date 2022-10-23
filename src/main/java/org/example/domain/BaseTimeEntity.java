package org.example.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // JPA Entity 클래스들이 BaseTimeEntity를 상속 -> BaseTimeEntity의 필드 (createdDate, modifiedDate)도 column으로 인식
@EntityListeners(AuditingEntityListener.class)  // BaseTimeEntity 클래스에 Auditing 기능을 포함
public class BaseTimeEntity {

    @CreatedDate    // 생성 시간이 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 변경 시간이 저장
    private LocalDateTime modifiedDate;
}
