package awsGroup.awsPractice.com.sikdan.webController.dto;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {

        return Posts.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();
    }
}
