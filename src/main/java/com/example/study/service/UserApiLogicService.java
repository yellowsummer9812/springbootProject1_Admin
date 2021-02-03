package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    // 1. request data를 가져오기
    // 2. user를 생성
    // 3. 생성된 데이터 -> UserApiResponse 만들어서 return

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = baseRepository.save(user);

        // 3. 생성된 데이터 -> UserApiResponse 만들어서 return
        // -> create 뿐만 아니라 read든 다른 method에서도 쓸 수 있기 때문에 따로 빼둠
        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> repository getOne, getById로 데이터 가져오기
        Optional<User> optional = baseRepository.findById(id); // user가 있을 수 있거나 없을 수 있는 상태로 가져옴

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                //.map(userApiResponse -> Header.OK(userApiResponse))
                .map(Header::OK)
                .orElseGet(// user가 없다면
                        ()->Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest>  request) {

        // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user데이터 찾기
        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        return optional.map(user -> {
            // 3. data -> update시켜주기
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;

            // 4. userApiResponse 만들기
        })
                .map(user -> baseRepository.save(user)) // update
                .map(updateUser -> response(updateUser)) // userApiResponse
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        // id -> repository -> user 찾기
        Optional<User> optional = baseRepository.findById(id);


        // repository -> delete
        return optional.map(user -> {
            baseRepository.delete(user);

            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));

        // response return

    }


    public UserApiResponse response(User user){

        // user 객체를 가지고 userApiResponse 만듦
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unRegisteredAt(user.getUnregisteredAt())
                .build();

        // Header에 Data 부분을 합쳐서 return
        return userApiResponse;
    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id){

        // user 가져옴
        User user = baseRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);

        // orderGroup 가져옴
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                   OrderGroupApiResponse orderGroupApiResponse =  orderGroupApiLogicService.response(orderGroup);

                   // 해당 orderGroup에 있는 item api response 찾아옴
                   List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                           .map(detail -> detail.getItem())
                           .map(item -> itemApiLogicService.response(item))
                           .collect(Collectors.toList());

                   orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                   return orderGroupApiResponse;
                })
                .collect(Collectors.toList());

        // user에다가 group내용 지정하고
        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build()
                ;
        return Header.OK(userOrderInfoApiResponse);
    }
}
