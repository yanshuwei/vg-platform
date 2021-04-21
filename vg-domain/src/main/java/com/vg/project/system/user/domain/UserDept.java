package com.vg.project.system.user.domain;

/**
 * @Author: James
 * @Date: 2021/2/21 21:22
 * @Description:
 */
public class UserDept {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 部门ID
     */
    private Long deptId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "UserDept [userId=" + userId + ", deptId=" + deptId + "]";
    }
}
