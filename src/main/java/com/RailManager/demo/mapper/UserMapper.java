package com.RailManager.demo.mapper;

import com.RailManager.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(Integer id);
    List<User> getUsers();
}
