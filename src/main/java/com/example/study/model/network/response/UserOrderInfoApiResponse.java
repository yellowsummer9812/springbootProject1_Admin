package com.example.study.model.network.response;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderInfoApiResponse { // 주문 정보 조회 response

    private UserApiResponse userApiResponse;

}
