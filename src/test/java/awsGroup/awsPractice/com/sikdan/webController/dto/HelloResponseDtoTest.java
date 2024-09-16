package awsGroup.awsPractice.com.sikdan.webController.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HelloResponseDtoTest {

    @Test
    void 롬복_기능_테스트() {
        String name = "olaf";
        int amount = 123;
        HelloResponseDto response = new HelloResponseDto(name, amount);

        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getAmount()).isEqualTo(amount); //동등비교
    }

}