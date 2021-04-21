package com.vg.project.system.user.mapper;

import com.vg.project.system.user.domain.UserDept;

import java.util.List;

/**
 * @Author: James
 * @Date: 2021/2/21 21:27
 * @Description:
 */
public interface UserDeptMapper {
    /**
     * 通过用户ID删除用户和部门关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserDeptByUserId(Long userId);

    /**
     * 批量删除用户和部门关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserDept(Long[] ids);

    /**
     * 批量新增用户部门信息
     *
     * @param userDeptList 用户部门列表
     * @return 结果
     */
    public int batchUserDept(List<UserDept> userDeptList);
}
