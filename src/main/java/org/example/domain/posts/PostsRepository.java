package org.example.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Entity 클래스와 함께 위치
public interface PostsRepository extends JpaRepository<Posts, Long> {   // Posts - id(Long)
    // CRUD 자동 생성

    // 전체 글 조회
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // querydsl, FK 조인, 복잡한 조건 등으로 Entity 클래스만으로 처리하기 어려운 데이터의 조회를 처리해주는 조회용 프레임워크
    List<Posts> findAllDesc();
}
