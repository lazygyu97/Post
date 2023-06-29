package com.sparta.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@EnableJpaAuditing //Timestamped에서 선언한 @EntityListeners(AuditingEntityListener.class)을 사용한다고 알려주기 위한 어노테이션
@SpringBootApplication //--> 어노테이션
public class PostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

}
