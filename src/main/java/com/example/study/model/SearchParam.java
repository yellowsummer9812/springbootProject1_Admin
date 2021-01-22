package com.example.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor// 모든 변수를 매개변수로 받는 생성자를 만들어줌
@NoArgsConstructor
public class SearchParam {
    private String account;
    private String email;
    private int page;
}
