package awsGroup.awsPractice.com.sikdan.domain.posts;

import awsGroup.awsPractice.com.sikdan.domain.BaseTimeEntity;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;



@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity

public class Posts extends BaseTimeEntity { //게시판
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;

    public Posts() {
    }
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(PostsUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }
}
