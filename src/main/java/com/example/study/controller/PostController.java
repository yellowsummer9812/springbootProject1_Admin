package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    // post가 발생하는 경우 : HTML에서 <form>태그 사용 / Ajax에서 검색 -> 검색 parameter가 많을 때
    // request의 form : JSON형태,xml 형태, multipart-form / text-plain

    //@RequestMapping(method = RequestMethod.POST, path = "/postMethod")
    @PostMapping(value = "/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam){// http통신을 할 때 post body에다가  data를 넣어서 보내겠다
        return searchParam;
    }

    @PutMapping("/putMethod")
    public void put(){

    }

    @PatchMapping("/patchMethod")
    public void parch(){

    }
}
