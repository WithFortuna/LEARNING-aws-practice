package awsGroup.awsPractice.com.sikdan.domain;

//베이스 엔티티(부모)

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //엔티티 클래스가 해당클래스를 상속받으면, 아래의 멤버변수들을 칼럼으로 포함시킴
@EntityListeners(AuditingEntityListener.class) //Auditing기능 포함
public class BaseTimeEntity {

    @CreatedDate //엔티티가 생성될 때
    private LocalDateTime createdDate;
    @LastModifiedDate //엔티티가 변경될 때
    private LocalDateTime modifiedDate;
}

