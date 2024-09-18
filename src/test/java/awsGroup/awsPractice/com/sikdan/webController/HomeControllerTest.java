package awsGroup.awsPractice.com.sikdan.webController;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void show_home_mustache() {
        //when
        String body = this.restTemplate.getForObject("/", String.class);
        //then
        Assertions.assertThat(body).contains("GNO");

    }

}