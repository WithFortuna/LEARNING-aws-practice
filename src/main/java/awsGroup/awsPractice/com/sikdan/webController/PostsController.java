package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping("/posts/save")
    public String savePosts() {
        return "posts-save";
    }
}
