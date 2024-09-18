package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import awsGroup.awsPractice.com.sikdan.service.PostsService;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsResponseDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsUpdateRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostConstruct
    public void createTestData() {
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto("title", "content", "author");
        postsService.save(requestDto);
    }

    //posts리소스 등록
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    //posts리소스 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findPosts(@PathVariable Long id) {
        return postsService.findById(id);
    }

    //posts리소스 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long updatePosts(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {

        return postsService.update(id, requestDto);

    }


    //posts리소스 삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public Long deletePosts(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

}
