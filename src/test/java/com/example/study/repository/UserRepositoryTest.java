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
        String account = "Test03";
        String password = "Test03";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);

        User u = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .build();

        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);

    }

    @Test
    @Transactional
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        user
                .setEmail("")
                .setPhoneNumber("")
                .setStatus("");

        User u = new User().setAccount("").setEmail("").setPassword("");

        if(user != null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("----------주문 묶음----------");
                System.out.println("수령인 : " + orderGroup.getRevName());
                System.out.println("수령지 : " + orderGroup.getRevAddress());
                System.out.println("총 금액 : " + orderGroup.getTotalPrice());
                System.out.println("총 수량 : " + orderGroup.getTotalQuantity());

                System.out.println("----------주문 상세----------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : " + orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " + orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문 상태 : " + orderDetail.getStatus());
                    System.out.println("도착 예정 일자 : " + orderDetail.getArrivalDate());


                });
            });
        }

        Assertions.assertNotNull(user);
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
