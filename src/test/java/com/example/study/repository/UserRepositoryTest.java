package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.Entity.Item;
import com.example.study.model.Entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired // Spring의 가장 대표적인 디자인 패턴
    private UserRepository userRepository; // Singleton Pattern

    @Test
    public void create(){
        // String sql = insert into user (%s, %s, %d) value (account, email, age);
        User user = new User();
        // user.setId(); -> Not Null에 Auto Increament이기 때문에 자동으로 해줌
        user.setAccount("TestUser03");
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-1111-3333");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser3"); // "TestUser01" 해도 상관 없음

        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);

    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findById(4L);

        user.ifPresent(selectUser -> { // selectUser가 Optional에 들어있으면 아래문장 실행
            selectUser.getOrderDetailList().stream().forEach(detail -> {
                Item item = detail.getItem();
                System.out.println(item);
            });
        });
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("PPPP");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional // query는 실행되지만 roll back해줌, 데이터에 변경 X
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

        Assertions.assertTrue(user.isPresent()); // 이 과정이 무조건 있어야함

        user.ifPresent(selectUser->{
            userRepository.delete(selectUser);
        });
        Optional<User> deleteUser = userRepository.findById(3L); // 삭제됐는지 확인하기 위해서

        Assertions.assertFalse(deleteUser.isPresent());
    }


}
