package com.vg.project.system.user.service;

import com.vg.project.system.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService{
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public Long[] selectUserIdsByRoleIds(Long[] roleIds) {
        return userRoleMapper.selectUserIdsByRoleIds(roleIds);
    }

    @Override
    public Long[] selectRoleIdsByUserId(Long userId) {
        return userRoleMapper.selectRoleIdsByUserId(userId);
    }
}
