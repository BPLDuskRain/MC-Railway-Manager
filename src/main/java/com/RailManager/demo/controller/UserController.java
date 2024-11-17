package com.RailManager.demo.controller;

import com.RailManager.demo.DTO.UserDTO;
import com.RailManager.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Value("${crossdomain}")
    private String crossDomainValues;
    final private ResponseEntity<String> crossDomain = ResponseEntity.ok().header("Access-Control-Allow-Origin" ,crossDomainValues).body("");

    @RequestMapping("/userCheck")
    public ResponseEntity<Boolean> userCheck(UserDTO dto){
        return ResponseEntity.ok()
                .headers(crossDomain.getHeaders())
                .body(userService.canLogIn(dto));
    }
}
