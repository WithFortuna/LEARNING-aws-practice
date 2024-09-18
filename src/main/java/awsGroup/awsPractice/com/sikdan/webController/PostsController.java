package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.service.PostsService;
import awsGroup.awsPractice.com.sikdan.webController.dto.PostsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping("/posts/save")
    public String postsSaveForm() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdateForm(@PathVariable Long id, Model model) {
        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("post", responseDto);

        return "posts-update";
    }
}
