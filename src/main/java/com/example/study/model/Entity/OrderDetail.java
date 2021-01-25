package com.example.study.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // order_detail
@ToString(exclude = {"user", "item"}) // OrderDetail과 User / Item이 서로 상호충돌하는 것 방지
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderAt;

    // N : 1
    @ManyToOne
    private User user; // 반드시 Long 형이 아니라 객체를 연결시켜야한다.

    // N : 1
    @ManyToOne
    private Item item;
}
