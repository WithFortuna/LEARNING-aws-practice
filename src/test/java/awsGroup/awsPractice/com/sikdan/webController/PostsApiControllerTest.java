package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import awsGroup.awsPractice.com.sikdan.domain.posts.PostsRepository;
import awsGroup.awsPractice.com.sikdan.service.PostsService;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsResponseDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> posts = postsRepository.findAll();
        assertThat(posts.get(0).getTitle())
                .isEqualTo(title);
        assertThat(posts.get(0).getContent())
                .isEqualTo(content);

    }


    @Test
    public void posts_조회() {
        //given
        String title = "titleTest";
        String content = "contentTest";
        String author = "olaf";
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto(title, content, author);
        Long id = postsService.save(requestDto);

        String url = "http://localhost:" + port + "/api/v1/posts/{id}";

        postsRepository.flush(); //DB에 데이터 저장 OK
        //when
        ResponseEntity<PostsResponseDto> responseEntity = restTemplate.getForEntity(url, PostsResponseDto.class, id);
        PostsResponseDto responseDto = responseEntity.getBody();
        System.out.println("==============================status 코드:"+ responseEntity.getStatusCode());

        //then
        assertThat(responseDto.getAuthor()).isEqualTo(author);
        assertThat(responseDto.getContent()).isEqualTo(content);
        assertThat(responseDto.getTitle()).isEqualTo(title);

    }

    @Test
    public void Posts_수정() throws Exception {
        //given
       Long id = postsRepository.save(Posts.builder() //DB에 테스트 데이터 저장
                .title("title")
                .content("Content")
                .author("author")
                .build()
        );
        String expectedTitle = "title2";
        String expectedContent = "content2";
        PostsUpdateRequestDto requestDto = new PostsUpdateRequestDto(expectedTitle, expectedContent); //updateReqeustDTO생성

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); //요청 보내기
        postsRepository.flush();
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> posts = postsRepository.findAll();

        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
    }
}