package awsGroup.awsPractice.com.sikdan.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void clear() {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_저장_조회(){
        //given
        String title = "testTitle1";
        String content = "testContent1";
        String author = "olaf";
        Posts post = Posts.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        //when
        postsRepository.save(post);
        Posts findPost = postsRepository.findOne(post.getId());
        //then
        org.assertj.core.api.Assertions.assertThat(findPost).isEqualTo(post);
    }

}