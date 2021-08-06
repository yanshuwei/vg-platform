package com.vg.project.system.role.service;

import com.github.pagehelper.PageHelper;
import com.vg.common.constant.UserConstants;
import com.vg.common.support.Convert;
import com.vg.common.utils.StringUtils;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.project.system.role.domain.Role;
import com.vg.project.system.role.domain.RoleMenu;
import com.vg.project.system.role.domain.RoleOrganization;
import com.vg.project.system.role.mapper.RoleMapper;
import com.vg.project.system.role.mapper.RoleMenuMapper;
import com.vg.project.system.role.mapper.RoleOrganizationMapper;
import com.vg.project.system.user.domain.User;
import com.vg.project.system.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 角色 业务层处理
 *
 * @author ruoyi
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

//    @Autowired
//    private OperatorRoleMapper operatorRoleMapper;

    @Autowired
    private RoleOrganizationMapper roleOrganizationMapper;


    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<Role> selectRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId) {
        PageHelper.clearPage();
        List<Role> roles = roleMapper.selectRolesByUserId(userId);
        Set<String> roleSet = new HashSet<>();
        for (Role role : roles) {
            roleSet.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
        }
        return roleSet;
    }

    /**
     * ??? NG ???
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        List<Role> userRoles = roleMapper.selectRolesByUserId(userId);
        List<Role> roles = roleMapper.selectRolesAll();
        for (Role role : roles) {
            for (Role userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }


    @Override
    public List<Role> selectCheckedRolesByUserId(Long userId) {
        List<Role> userRoles = roleMapper.selectRolesByUserId(userId);
        User user = ShiroUtils.getUser();
        String roleType = user.getUserType();
        Role role = new Role();
        if (roleType.equals("Platform")) {
            role.setCreateType(1);
            List<Role> roles = roleMapper.selectRoleList(role);
            for (Role ro : roles) {
                for (Role userRole : userRoles) {
                    if (ro.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                        ro.setFlag(true);
                        break;
                    }
                }
            }
            return roles;
        }
        role.setUserId(user.getUserId());
        if (roleType.equals("Operation")) {
            List<Role> roles = roleMapper.selectOperationRoleList(role);
            for (Role ro : roles) {
                for (Role userRole : userRoles) {
                    if (ro.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                        ro.setFlag(true);
                        break;
                    }
                }
            }
            return roles;
        }
        List<Role> roles = roleMapper.selectSupplierRoleList(role);
        for (Role ro : roles) {
            for (Role userRole : userRoles) {
                if (ro.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    ro.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectRolesAll();
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public Role selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public boolean deleteRoleById(Long roleId) {
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteRoleByIds(String ids) throws Exception {
        Long[] roleIds = Convert.toLongArray(ids);
        String userType = ShiroUtils.getUser().getUserType();
        for (Long roleId : roleIds) {
            Role role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new Exception(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
            if (userType.equals("Operation") || userType.equals("Supplier")) {
                if (roleMapper.selectRoleById(roleId).getCreateType()==1) {
                    throw new Exception(String.format("%1$s无权限删除", role.getRoleName()));
                }
            }
            roleOrganizationMapper.deleteByRoleId(roleId);
            roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int insertRole(Role role) {
        role.setCreateBy(ShiroUtils.getLoginName());
        // 新增角色信息
        roleMapper.insertRole(role);
        RoleOrganization roleOrganization = new RoleOrganization();
        if (role.getRoleType().equals("Platform")) {
            for (int i = 0; i < role.getOperatorIds().length; i++) {
                roleOrganization.setOperatorId(role.getOperatorIds()[i]);
                roleOrganization.setRoleId(role.getRoleId());
                roleOrganizationMapper.insertRoleOrganization(roleOrganization);
            }
        }
        if (role.getRoleType().equals("Operation")) {
            roleOrganization.setOperatorId(role.getOperatorId());
            roleOrganization.setRoleId(role.getRoleId());
            roleOrganizationMapper.insertRoleOrganization(roleOrganization);
        }
        if (role.getRoleType().equals("Supplier")) {
            roleOrganization.setSupplierCode(role.getSupplierCode());
            roleOrganization.setRoleId(role.getRoleId());
            roleOrganizationMapper.insertRoleOrganization(roleOrganization);
        }
        ShiroUtils.clearCachedAuthorizationInfo();
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRole(Role role) {
       /* User user = ShiroUtils.getUser();
        String userType = user.getUserType();
        if (userType.equals("Platform")) {
            if(role.getRoleType().equals("Operation")){
                //删除已解除关联运营商的相关项目成员表记录
            }
            if (role.getRoleType().equals("Operation") || role.getRoleType().equals("Supplier")) {
                if(role.getRoleType().equals("Supplier")&&role.getSupplierCode().equals("")){
                    return -1;
                }
                if (role.getRoleType().equals("Operation")&&(role.getOperatorId()==0||role.getOperatorId()==null)){
                    return -1;
                }
                role.setUpdateBy(ShiroUtils.getLoginName());
                // 修改角色信息
                roleMapper.updateRole(role);
                List<Role> otherRolesList = roleMapper.selectOtherRoles(role);
                if (otherRolesList.size() > 0) {
                    for (Role ro : otherRolesList) {
                        Long[] otherRolesMIds = roleMenuMapper.selectMenuIdByRoleId(ro.getRoleId());
                        Long[] menuIds = role.getMenuIds();
                        for (Long otherMenuId : otherRolesMIds) {
                            boolean contain = false;
                            for (int i = 0; i < menuIds.length; i++) {
                                if (otherMenuId.longValue() == menuIds[i].longValue()) {
                                    contain = true;
                                    break;
                                }
                            }
                            if (!contain) {
                                roleMenuMapper.deleteByRoleIdAndMenuId(ro.getRoleId(), otherMenuId);
                            }
                        }
                    }
                }
            }

            // 删除角色运营商/供应商关联
            roleOrganizationMapper.deleteByRoleId(role.getRoleId());
            //新增角色与 运营商或供应商关联
            RoleOrganization roleOrganization = new RoleOrganization();
            if (role.getRoleType().equals("Platform")) {
                for (int i = 0; i < role.getOperatorIds().length; i++) {
                    roleOrganization.setOperatorId(role.getOperatorIds()[i]);
                    roleOrganization.setRoleId(role.getRoleId());
                    roleOrganizationMapper.insertRoleOrganization(roleOrganization);
                }
            }
            if (role.getRoleType().equals("Operation")) {
                roleOrganization.setOperatorId(role.getOperatorId());
                roleOrganization.setRoleId(role.getRoleId());
                roleOrganizationMapper.insertRoleOrganization(roleOrganization);
            }
            if (role.getRoleType().equals("Supplier")) {
                roleOrganization.setSupplierCode(role.getSupplierCode());
                roleOrganization.setRoleId(role.getRoleId());
                roleOrganizationMapper.insertRoleOrganization(roleOrganization);
            }

            role.setUpdateBy(ShiroUtils.getLoginName());
            // 修改角色信息
            roleMapper.updateRole(role);
            // 删除角色与菜单关联
            roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
            ShiroUtils.clearCachedAuthorizationInfo();
            return insertRoleMenu(role);
        }*/

        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        ShiroUtils.clearCachedAuthorizationInfo();
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role) {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(Role role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.ROLE_NAME_NOT_UNIQUE;
        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(Role role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.ROLE_KEY_NOT_UNIQUE;
        }
        return UserConstants.ROLE_KEY_UNIQUE;
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 根据运营商ID查询角色
     *
     * @param operatorID 运营商ID
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesByOperatorId(Long operatorID) {
//        List<OperatorRole> operatorRoles = operatorRoleMapper.selectRolesByOperatorId(operatorID);
        List<RoleOrganization> operatorRoles = roleOrganizationMapper.selectRolesByOperatorId(operatorID);
        List<Role> roles = roleMapper.selectRolesAll();
        for (Role role : roles) {
            for (RoleOrganization or : operatorRoles) {
                if (role.getRoleId().longValue() == or.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }
    @Override
    public List<Role> selectRolesByProjectId(Long projectId) {
        return roleMapper.selectRolesByProjectId(projectId);
    }


    @Override
    public int selectCreateType(Long userId) {
        PageHelper.clearPage();
        List<Role> roles = roleMapper.selectRolesByUserId(userId);
        return roles.get(0).getCreateType();
    }

    @Override
    public String selectRoleType(Long userId) {
        PageHelper.clearPage();
        List<Role> roles = roleMapper.selectRolesByUserId(userId);
        return roles.get(0).getRoleType();
    }

    @Override
    public List<Role> selectSupplierRoleList(Role role) {
        return roleMapper.selectSupplierRoleList(role);
    }

    @Override
    public List<Role> selectOperationRoleList(Role role) {
        return roleMapper.selectOperationRoleList(role);
    }
}
