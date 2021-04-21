package com.vg.project.system.user.service;

public interface IUserRoleService {
    public Long[] selectUserIdsByRoleIds(Long[] roleIds);

    public Long[] selectRoleIdsByUserId(Long userId);
}
