package com.vg.project.system.user.mapper;

import com.vg.project.system.user.domain.UserRole;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface UserRoleMapper
{

    /**
     * 通过用户ID删除用户和角色关联
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除用户和角色关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     * 
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<UserRole> userRoleList);

    //根据roleIds批量查询userid
    public Long[] selectUserIdsByRoleIds(Long[] roleIds);

    public Long[] selectRoleIdsByUserId(Long userId);

}
