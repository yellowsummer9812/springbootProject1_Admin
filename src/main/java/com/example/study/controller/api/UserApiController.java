package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// User의 정보를 가져옴
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@EnableSpringDataWebSupport
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {

    @GetMapping("")
    public Header<List<UserApiResponse>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 15) Pageable pageable){
        log.info("{}", pageable);
        return baseService.search(pageable);
    }

}
