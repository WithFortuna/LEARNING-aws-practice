package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @Autowired
    private PostsService postsService;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postsService.findAll());
        return "home";
    }
}
