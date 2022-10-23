package org.example.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After  // 단위테스트가 끝날 때마다 수행 -> DB의 모든 데이터를 삭제
    void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글 타이틀";
        String content = "테스트 게시글 본문";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build()
            );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }
}