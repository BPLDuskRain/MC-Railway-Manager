package com.RailManager.demo.mapper;

import com.RailManager.demo.annotation.MySelect;
import com.RailManager.demo.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public interface PermissionMapper {
    @Transactional(readOnly = true)
    @MySelect
    Permission getPermission(Integer userId);
}
