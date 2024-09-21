package awsGroup.awsPractice.com.sikdan.webController;

import awsGroup.awsPractice.com.sikdan.config.auth.SecurityConfig;
import awsGroup.awsPractice.com.sikdan.webController.dto.HelloResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }

)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }
    @WithMockUser(roles = "USER")
    @Test
    void response가_리턴된다() throws Exception{
        String name = "tryn";
        int amount = 20;

        mvc.perform(
                get("/hello/dto")
                    .param("name",name)
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.name", is(name)))
                .andExpect( jsonPath("$.amount", is(amount)));
    }
}