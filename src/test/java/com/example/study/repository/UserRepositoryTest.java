package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired // Spring의 가장 대표적인 디자인 패턴
    private UserRepository userRepository; // Singleton Pattern

    @Test
    public void create(){
        // String sql = insert into user (%s, %s, %d) value (account, email, age);
        User user = new User();
        // user.setId(); -> Not Null에 Auto Increament이기 때문에 자동으로 해줌
        user.setAccount("TestUser02");
        user.setEmail("TestUser02@gmail.com");
        user.setPhoneNumber("010-1111-1111");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser2"); // "TestUser01" 해도 상관 없음

        User newUser = userRepository.save(user);
        System.out.println("newUser : " + newUser);

    }

    public void read(){

    }

    public void update(){

    }

    public void delete(){

    }


}
