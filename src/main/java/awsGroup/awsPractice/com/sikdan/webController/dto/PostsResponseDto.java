package awsGroup.awsPractice.com.sikdan.webController.dto;

import awsGroup.awsPractice.com.sikdan.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostsResponseDto {
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public PostsResponseDto(Posts posts) {
        if (posts != null) {
            this.title = posts.getTitle() != null?posts.getTitle() : "";
            this.content = posts.getContent() != null ? posts.getContent() : "";
            this.author = posts.getAuthor() != null ? posts.getAuthor() : "";
        } else {
            System.out.println("===================================위치:ResponseDto생성자");
        } 
    }
}
