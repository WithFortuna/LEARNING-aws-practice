package awsGroup.awsPractice.com.sikdan.service;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import awsGroup.awsPractice.com.sikdan.domain.posts.PostsRepository;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsResponseDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostsServiceTest {
    @AfterEach
    public void clear() {
        postsRepository.deleteAll();
    }
    @Autowired
    PostsService postsService;
    @Autowired
    PostsRepository postsRepository;


    @Test
    public void posts_저장_및_조회() {
        //given
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto("testTitle", "testContent", "olaf");
        Long id = postsService.save(requestDto);

        //when
        PostsResponseDto responseDto = postsService.findById(id);

        //then
        assertThat(responseDto.getTitle()).isEqualTo(requestDto.getTitle());
    }

    @Test
    public void posts_수정() {
        //given
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto("testTitle", "testContent", "olaf");
        Long id = postsService.save(requestDto);
        PostsUpdateRequestDto updateRequestDto = new PostsUpdateRequestDto("updateTitle", "updateContent");
        //when
        postsService.update(id, updateRequestDto);

        //then
        PostsResponseDto responseDto = postsService.findById(id);
        assertThat(responseDto.getTitle()).isEqualTo(updateRequestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(updateRequestDto.getContent());
    }

}