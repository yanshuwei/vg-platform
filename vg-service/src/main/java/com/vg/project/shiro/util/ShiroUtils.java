package com.vg.project.shiro.util;

import com.vg.common.utils.StringUtils;
import com.vg.common.utils.bean.BeanUtils;
import com.vg.project.shiro.realm.UserRealm;
import com.vg.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;


/**
 * shiro 工具类
 *
 * @author ruoyi
 */
public class ShiroUtils {

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubjct().logout();
    }

    public static User getUser() {
        User user = null;
        Object obj = getSubjct().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new User();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    public static void setUser(User user) {
        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        com.vg.project.shiro.realm.UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    public static Long getUserId() {
        return getUser().getUserId().longValue();
    }

    public static String getLoginName() {
        return getUser().getLoginName();
    }

    public static String getIp() {
        return getSubjct().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
