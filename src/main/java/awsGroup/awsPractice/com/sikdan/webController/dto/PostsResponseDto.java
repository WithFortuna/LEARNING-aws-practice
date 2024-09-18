package awsGroup.awsPractice.com.sikdan.webController.dto;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public PostsResponseDto(Posts posts) {
        if (posts != null) {
            this.title = posts.getTitle() != null?posts.getTitle() : "";
            this.content = posts.getContent() != null ? posts.getContent() : "";
            this.author = posts.getAuthor() != null ? posts.getAuthor() : "";
            this.id = posts.getId() != null? posts.getId() : 0;
        } else {
            System.out.println("===================================위치:ResponseDto생성자");
        } 
    }
}
