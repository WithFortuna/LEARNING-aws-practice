package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import awsGroup.awsPractice.com.sikdan.domain.posts.PostsRepository;
import awsGroup.awsPractice.com.sikdan.service.PostsService;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsResponseDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    //-------------------- MockMvc 설정
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    //--------------------
    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();

    }


    @Test
    @WithMockUser(roles="USER")
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
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        /*
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> posts = postsRepository.findAll();
        assertThat(posts.get(0).getTitle())
                .isEqualTo(title);
        assertThat(posts.get(0).getContent())
                .isEqualTo(content);
*/
        List<Posts> posts = postsRepository.findAll();
        assertThat(posts.get(1).getTitle()).isEqualTo(title); //Posts클래스에 @PostConstruct로 테스트 데이터가 한개 있으므로 get(1)로 다른걸 꺼내야함
        assertThat(posts.get(1).getContent()).isEqualTo(content);

    }


    @Test
    @WithMockUser(roles="USER")
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
    @WithMockUser(roles="USER")
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
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); //요청 보내기
//        postsRepository.flush();
        mvc.perform(put(url)
                        .contentType(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        /*assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> posts = postsRepository.findAll();

        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
 */
        List<Posts> posts = postsRepository.findAll();
        assertThat(posts.get(1).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(1).getContent()).isEqualTo(expectedContent);
    }
}