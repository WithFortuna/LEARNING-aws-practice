package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import awsGroup.awsPractice.com.sikdan.domain.posts.PostsRepository;
import awsGroup.awsPractice.com.sikdan.service.PostsService;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PostsApiControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    PostsService postsService;
    @Autowired
    PostsRepository postsRepository;
    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();

    }


    @Test
    void posts_저장() throws Exception{
        //given
        String title = "titleTest";
        String content = "contentTest";
        String author = "olaf";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";


        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> posts = postsRepository.findAll();
        Assertions.assertThat(posts.get(0).getTitle())
                .isEqualTo(title);
        Assertions.assertThat(posts.get(0).getContent())
                .isEqualTo(content);

    }
}