package com.RailManager.demo.sa_token;

import cn.dev33.satoken.stp.StpInterface;
import com.RailManager.demo.mapper.PermissionMapper;
import com.RailManager.demo.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class STPermission implements StpInterface {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissions = new ArrayList<>();
        Permission permission =  permissionMapper.getPermission(Integer.valueOf((String)loginId));
        if(permission.getCanAdd()) permissions.add("canAdd");
        if(permission.getCanDelete()) permissions.add("canDelete");
        if(permission.getCanUpdate()) permissions.add("canUpdate");
        if(permission.getCanSelect()) permissions.add("canSelect");

        return permissions;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
