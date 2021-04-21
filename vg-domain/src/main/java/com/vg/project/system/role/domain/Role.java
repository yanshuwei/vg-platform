package com.vg.project.system.role.domain;

import com.vg.framework.aspectj.lang.annotation.Excel;
import com.vg.framework.web.domain.BaseEntity;

import java.util.Arrays;


/**
 * 角色对象 sys_role
 * 
 * @author ruoyi
 */
public class Role extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @Excel(name = "角色序号")
    private Long roleId;

    /** 角色名称 */
    @Excel(name = "角色名称")
    private String roleName;

    /**角色类型*/
    @Excel(name="角色类型")
    private String roleType;
    /** 角色权限 */
    @Excel(name = "角色权限")
    private String roleKey;

    /** 角色排序 */
    @Excel(name = "角色排序")
    private String roleSort;

    /** 角色状态（0正常 1停用） */
    @Excel(name = "角色状态")
    private String status;
    //创建类型
    private int createType;
    //用户id
    private Long userId;
    //关联客户
    private Long[] operatorIds;
    //运营商关联
    private Long operatorId;
    //供应商编码
    private String supplierCode;

    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;
    /** 菜单组 */
    private Long[] menuIds;
    /** 运营商名称 */
    private String operatorName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey(String roleKey)
    {
        this.roleKey = roleKey;
    }

    public String getRoleSort()
    {
        return roleSort;
    }

    public void setRoleSort(String roleSort)
    {
        this.roleSort = roleSort;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public Long[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds)
    {
        this.menuIds = menuIds;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long useId) {
        this.userId = useId;
    }

    public Long[] getOperatorIds() {
        return operatorIds;
    }

    public void setOperatorIds(Long[] operatorIds) {
        this.operatorIds = operatorIds;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleType='" + roleType + '\'' +
                ", roleKey='" + roleKey + '\'' +
                ", roleSort='" + roleSort + '\'' +
                ", status='" + status + '\'' +
                ", createType=" + createType +
                ", flag=" + flag +
                ", menuIds=" + Arrays.toString(menuIds) +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }

    /* @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleKey='" + roleKey + '\'' +
                ", roleSort='" + roleSort + '\'' +
                ", status='" + status + '\'' +
                ", flag=" + flag +
                ", menuIds=" + Arrays.toString(menuIds) +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }*/
}
