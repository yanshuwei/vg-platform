package com.vg.project.system.role.mapper;

import com.vg.project.system.role.domain.RoleOrganization;

import java.util.List;

public interface RoleOrganizationMapper {
    public int insertRoleOrganization(RoleOrganization roleOrganization);

    public List<RoleOrganization> selectByUserId(Long userId);

    Long [] selectOperationIdByRoleId(Long roleId);

    String selectSupplierCodeByRoleId(Long roleId);

    /**
     * 通过运营商ID删除运营商和角色关联
     *
     * @param operatorId 运营商ID
     * @return 结果
     */
    public int deleteOperatorRoleByOperatorId(Long operatorId);

    /**
     * 批量删除运营商和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOperatorRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countOperatorRoleByRoleId(Long roleId);

    /**
     * 批量新增运营商角色信息
     *
     * @param roleOrganizations 运营商角色列表
     * @return 结果
     */
    public int batchOperatorRole(List<RoleOrganization> roleOrganizations);


    public List<RoleOrganization> selectRolesByOperatorId(Long operatorId);

    public int deleteByRoleId (Long roleId);
}
