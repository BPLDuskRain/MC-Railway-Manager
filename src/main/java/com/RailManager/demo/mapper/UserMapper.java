package com.RailManager.demo.mapper;

import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public interface UserMapper {
    @MySelect
    User getUserById(Integer id);
    @MySelect
    User getUserByName(String userName);
    @MySelect
    List<User> getUsers();
}
