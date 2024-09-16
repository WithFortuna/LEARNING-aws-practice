package awsGroup.awsPractice.com.sikdan.service;

import awsGroup.awsPractice.com.sikdan.domain.posts.PostsRepository;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto);
    }
}
