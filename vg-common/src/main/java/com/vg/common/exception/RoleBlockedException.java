package com.vg.common.exception;

/**
 * 角色锁定异常类
 * 
 * @author peter
 */
public class RoleBlockedException extends UserException
{

    private static final long serialVersionUID = 1L;

    public RoleBlockedException(String reason)
    {
        super("role.blocked", new Object[] { reason });
    }

}
