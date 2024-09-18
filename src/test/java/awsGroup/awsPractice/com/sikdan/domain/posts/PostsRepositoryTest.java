package awsGroup.awsPractice.com.sikdan.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    public void 베이스엔티티_등록() {
        //given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("olaf")
                .build()
        );

        //when
        List<Posts> posts = postsRepository.findAll();
        Posts post = posts.get(0);
        //then
        System.out.println("====================================" +
                "\nnow:"+now);
        System.out.println("createDate: "+ post.getCreatedDate() );
        System.out.println("modifiedDate: "+ post.getModifiedDate() );

        org.assertj.core.api.Assertions.assertThat(post.getCreatedDate()).isBefore(now);
    }

}