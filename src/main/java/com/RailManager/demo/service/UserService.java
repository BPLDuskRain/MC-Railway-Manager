package com.RailManager.demo.service;

import cn.dev33.satoken.stp.StpUtil;
import com.RailManager.demo.DTO.UserDTO;
import com.RailManager.demo.annotation.MyService;
import com.RailManager.demo.mapper.UserMapper;
import com.RailManager.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @MyService
    public boolean canLogIn(UserDTO dto){
        //传入空数据则否
        if(dto == null) return false;

        String userName = dto.getUserName();
        String password = dto.getPassword();
        //有一个空则否
        if(userName == null || password == null) return false;
        User checkUser = userMapper.getUserByName(userName);
        //名字在数据库中不存在，否
        if(checkUser == null) return false;
        StpUtil.login(checkUser.getUserId());
        return checkUser.getPassword().equals(password);
    }
}
