package com.vg.project.system.role.domain;

public class RoleOrganization {
    private Long roleId;
    private Long operatorId;
    private String supplierCode;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        return "RoleOrganization{" +
                "roleId=" + roleId +
                ", operatorId=" + operatorId +
                ", supplierCode='" + supplierCode + '\'' +
                '}';
    }
}
