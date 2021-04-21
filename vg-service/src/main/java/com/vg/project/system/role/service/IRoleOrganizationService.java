package com.vg.project.system.role.service;

import com.vg.project.system.role.domain.RoleOrganization;

public interface IRoleOrganizationService {
    public RoleOrganization selectByUserId (Long userId);

    Long[] selectOperationIdByRoleId(Long roleId);

    String selectSupplierCodeByRoleId(Long roleId);
}
