package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.config.auth.LoginUser;
import awsGroup.awsPractice.com.sikdan.config.auth.dto.SessionUser;
import awsGroup.awsPractice.com.sikdan.service.PostsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAll()); //posts 모델에 담기
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName()); //user모델에 담기
        }
        return "home";
    }
}
