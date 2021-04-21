package com.vg.project.system.role.service;

import com.vg.project.system.role.domain.RoleOrganization;
import com.vg.project.system.role.mapper.RoleOrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleOrganizationServiceImpl implements IRoleOrganizationService{
    @Autowired
    private RoleOrganizationMapper roleOrganizationMapper;
    @Override
    public RoleOrganization selectByUserId(Long userId) {
        List <RoleOrganization> roleOrgList = roleOrganizationMapper.selectByUserId(userId);
        return roleOrgList.size()>0?roleOrgList.get(0):null;
    }

    @Override
    public Long[] selectOperationIdByRoleId(Long roleId) {

        return roleOrganizationMapper.selectOperationIdByRoleId(roleId);
    }

    @Override
    public String selectSupplierCodeByRoleId(Long roleId) {
        return roleOrganizationMapper.selectSupplierCodeByRoleId(roleId);
    }
}
