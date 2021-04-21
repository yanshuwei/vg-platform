package com.vg.project.system.dept.domain;

import com.vg.framework.web.domain.BaseEntity;

import java.util.List;
import java.util.Objects;


/**
 * 部门对象 sys_dept
 * 
 * @author ruoyi
 */
public class Dept extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** 部门ID */
    private Long deptId;
    /** 父部门ID */
    private Long parentId;
    /** 祖级列表 */
    private String ancestors;
    /** 部门名称 */
    private String deptName;
    /** 显示顺序 */
    private String orderNum;
    /** 负责人 */
    private Long leader;
    /** 管理员 */
    private Long administrator;
    /** 联系电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 部门状态:0正常,1停用 */
    private String status;
    /** 父部门名称 */
    private String parentName;

    private List<Dept> children;

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Long getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Long administrator) {
        this.administrator = administrator;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<Dept> getChildren() {
        return children;
    }

    public void setChildren(List<Dept> children) {
        this.children = children;
    }

    @Override
    public String toString()
    {
        return "Dept [deptId=" + deptId + ", parentId=" + parentId + ", ancestors=" + ancestors + ", deptName="
                + deptName + ", orderNum=" + orderNum + ", leader=" + leader + ", administrator=" + administrator + ", phone=" + phone + ", email=" + email
                + ", status=" + status + ", parentName=" + parentName + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dept)) return false;
        Dept dept = (Dept) o;
        return Objects.equals(getDeptId(), dept.getDeptId()) &&
                Objects.equals(getDeptName(), dept.getDeptName()) &&
                Objects.equals(getStatus(), dept.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeptId(), getDeptName(), getStatus());
    }
}
