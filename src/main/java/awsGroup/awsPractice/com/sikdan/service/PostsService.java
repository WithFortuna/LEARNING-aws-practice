package awsGroup.awsPractice.com.sikdan.service;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import awsGroup.awsPractice.com.sikdan.domain.posts.PostsRepository;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsListResponseDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsResponseDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
/*Service 레이어는 1. 도메인 간 순서 2. 트랜잭션 관리만 담당한다
 * ->
 * 비즈니스 로직은 각 도메인에서 처리
 *
 * */
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    //등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto);
    }

    //조회
    @Transactional(readOnly = true) //조회 속도 개선
    public PostsResponseDto findById(Long id) {

        Optional<Posts> optionalPosts = postsRepository.findByOptionalId(id);
        Posts findPosts = optionalPosts.orElseThrow(() -> new IllegalArgumentException("해당 게시글 없음, id: " + id));

        return new PostsResponseDto(findPosts);
    }

    @Transactional(readOnly = true) //조회 속도 개선

    public List<PostsListResponseDto> findAll() {
        return postsRepository.findAll().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    //수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts findPosts = null;
        try {
             findPosts = postsRepository.findOne(id);
        } catch (IllegalArgumentException exception) {
            log.info("해당 게시글이 없습니다. id = {}", id);
        }
        if (findPosts != null) {
            findPosts.update(requestDto); //비즈니스 로직은 도메인에 위치함.

        }

        return id;
    }

    //삭제
    @Transactional
    public void delete(Long id) {
        Optional<Posts> optionalPosts = postsRepository.findByOptionalId(id);
        Posts posts = optionalPosts.orElseThrow(() -> new IllegalArgumentException("해당게시글 없음 id: " + id));

        postsRepository.delete(posts);
    }
}
