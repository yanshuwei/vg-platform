package com.vg.project.system.role.mapper;

import com.vg.project.system.role.domain.Role;

import java.util.List;


/**
 * 角色表 数据层
 * 
 * @author ruoyi
 */
public interface RoleMapper
{

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<Role> selectRoleList(Role role);

    /**
     * 根据用户ID查询角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<Role> selectRolesByUserId(Long userId);

    /**
     * 查询角色列表
     * 
     * @return 角色列表
     */
    public List<Role> selectRolesAll();

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public Role selectRoleById(Long roleId);

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleById(Long roleId);

    /**
     * 批量角色用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] ids);

    /**
     * 修改角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(Role role);

    /**
     * 新增角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(Role role);

    /**
     * 校验角色名称是否唯一
     * 
     * @param roleName 角色名称
     * @return 角色信息
     */
    public Role checkRoleNameUnique(String roleName);
    
    /**
     * 校验角色权限是否唯一
     * 
     * @param roleKey 角色权限
     * @return 角色信息
     */
    public Role checkRoleKeyUnique(String roleKey);

    public List<Role> selectOperationRoleList(Role role);

    public List<Role> selectSupplierRoleList(Role role);

    List<Role> selectOtherRoles(Role role);

    /**
     * 查询平台级用户创建角色名称
     */
    public String selectRoleName(Role role);

	List<Role> selectRolesByProjectId(Long projectId);
}
