package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController // 컨트롤러로 사용할 거라고 spring에게 알리는 지시자
@RequestMapping("/api") // 이곳(Localhost:8080/api)으로 들어올 API주소를 mapping하기 위해서
public class GetController {
    // 사용자의 요청을 받는 method
    // annotation 매개변수 : 메서드는 어떤 타입으로 받을 건지, 어떤 주소로 받아들일지
    @RequestMapping(method = RequestMethod.GET, path = "/getMethod") // Localhost:8080/api/getMethod
    public String getRequest(){

        return "Hi getMethod";
    }

    // GET에 대해서 처리하기 때문에
    @GetMapping("/getParameter") // Localhost:8080/api/getParameter?id=1234&password=abcd
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd){
        String password = "bbbb";

        System.out.println("id : " + id);
        System.out.println("pwd : " + pwd);

        return id+pwd;
    }

    // 검색할 때 여러가지 parameter 받기
    // Localhost:8080/api/multiParameter?account=abcd&email=study@gmail.com&page=10
    @GetMapping("/getMultiParameter")
    public SearchParam multiParameter(SearchParam searchParam){
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

        // 네트워크 통신을 할 때는 json형태로
        // { "account" : "", "email" : "", "page" : 0}
        return searchParam;
    }
}
