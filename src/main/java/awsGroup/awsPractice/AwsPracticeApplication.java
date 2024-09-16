package awsGroup.awsPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //프로젝트 상단에 위치해야 하위 SpringBean들을 읽어냄
public class AwsPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsPracticeApplication.class, args);
	}

}
