package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.Entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    // 1. request data를 가져오기
    // 2. user를 생성
    // 3. 생성된 데이터 -> UserApiResponse 만들어서 return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISTERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = userRepository.save(user);

        // 3. 생성된 데이터 -> UserApiResponse 만들어서 return
        // -> create 뿐만 아니라 read든 다른 method에서도 쓸 수 있기 때문에 따로 빼둠
        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> repository getOne, getById로 데이터 가져오기
        Optional<User> optional = userRepository.findById(id); // user가 있을 수 있거나 없을 수 있는 상태로 가져옴

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                .orElseGet(// user가 없다면
                        ()->Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest>  request) {

        // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user데이터 찾기
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

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
                .map(user -> userRepository.save(user)) // update
                .map(updateUser -> response(updateUser)) // userApiResponse
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    private Header<UserApiResponse> response(User user){

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
        return Header.OK(userApiResponse);
    }
}
