package com.vg.project.system.user.service;

import com.vg.common.constant.UserConstants;
import com.vg.common.support.Convert;
import com.vg.common.utils.EmailUtil;
import com.vg.common.utils.StringUtils;
import com.vg.project.shiro.service.PasswordService;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.project.system.post.domain.Post;
import com.vg.project.system.post.mapper.PostMapper;
import com.vg.project.system.role.domain.Role;
import com.vg.project.system.role.mapper.RoleMapper;
import com.vg.project.system.user.domain.User;
import com.vg.project.system.user.domain.UserDept;
import com.vg.project.system.user.domain.UserPost;
import com.vg.project.system.user.domain.UserRole;
import com.vg.project.system.user.mapper.UserDeptMapper;
import com.vg.project.system.user.mapper.UserMapper;
import com.vg.project.system.user.mapper.UserPostMapper;
import com.vg.project.system.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserDeptMapper userDeptMapper;
    @Autowired
    private EmailUtil emailUtil;
    @Value("${spring.mail.username}")
    private String sender;


    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user) {

        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws Exception {
        User loginUser = ShiroUtils.getUser();
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {

            if ((!loginUser.getUserType().equals("Platform")) && (userMapper.selectUserById(userId).getIsDefault().intValue() != 0)) {
                throw new Exception("不允许删除此用户");
            }
            if (User.isAdmin(userId)) {
                throw new Exception("不允许删除超级管理员用户");
            }
            // 删除用户与角色关联
            userRoleMapper.deleteUserRoleByUserId(userId);
            // 删除用户与岗位表
            userPostMapper.deleteUserPostByUserId(userId);
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        String content = "<html><body>" + user.getUserName() + ":您好!<p style=\"text-indent:2em;\">您的金峰云账号已开通!</p><p style=\"text-indent:2em;\">登录账号为：" + user.getLoginName() + "</p><p style=\"text-indent:2em;\">初始密码为：" + user.getPassword() + "</p><p style=\"text-indent:2em;\">角色：";
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        // 新增用户部门关联
        insertUserDept(user);
        if (rows > 0) {
            for (Long roleId : user.getRoleIds()) {
                content = content + roleMapper.selectRoleById(roleId).getRoleName() + ",";
            }
            content = content + "</p><p style=\"text-indent:2em;\">为了您的账号安全，请登录后重置密码！</p>" + "<p style=\"text-indent:2em;\">点击链接:<a href=\"https://yun.ginfon.cn\">https://yun.ginfon.cn</a>访问金峰云</p>"
                    + "<hr style=\"height:1px;border:none;border-top:1px solid #555555;\" /> \n" +
                    "<b>此邮件为系统邮件，请勿直接回复！</b>\n" +
                    "</body>\n" +
                    "</html>";
            emailUtil.sendHtmlMail(sender, user.getEmail(), "金峰云账号开通通知", content);
        }

        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        Long userId = user.getUserId();
        user.setUpdateBy(ShiroUtils.getLoginName());
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与部门关联
        userDeptMapper.deleteUserDeptByUserId(userId);
        // 新增用户与部门关联
        insertUserDept(user);
        //查询用户关联项目信息

        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        int rows = userMapper.updateUser(user);
        if (rows > 0) {
            String content = "<html><body>" + user.getUserName() + "，您好!<p style=\"text-indent:2em;\">您的金峰云账号角色发生改变，当前角色为：";
            for (Long roleId : user.getRoleIds()) {
                content = content + roleMapper.selectRoleById(roleId).getRoleName() + "&nbsp;&nbsp;&nbsp;&nbsp;";
            }
            content = content + "</p>" + "<hr style=\"height:1px;border:none;border-top:1px solid #555555;\" /> \n" +
                    "<b>此邮件为系统邮件，请勿直接回复！</b>\n" +
                    "</body>\n" +
                    "</html>";
            emailUtil.sendHtmlMail(sender, user.getEmail(), "金峰云账号变动通知", content);
        }
        return rows;
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user) {
        String content = "<html><body><div style=\"font-size:14px;\">" +
                user.getUserName() + "，您好！</div><div style=\"font-size:14px;margin-top:10px;text-indent:2em;\">您的金峰云登录账号为：" + user.getLoginName() + "  登录密码已重置为：" + user.getPassword() + "   为了您的账号安全，请登录后重新设置密码。</div>";
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        int rows = updateUserInfo(user);
        if (rows > 0) {
            content += "<div style=\"font-size:14px;margin-top:20px;text-indent:2em;\">点击链接 <a href=\"https://yun.ginfon.cn\" title=\"金峰云\">https://yun.ginfon.cn</a> 进入金峰云 ......</div>";
            content += "<hr/>";
            content += "<div>此邮件为系统邮件，请勿直接回复！</div>";
            content += "</body></html>";
            emailUtil.sendHtmlMail(sender, user.getEmail(), "金峰云账号密码变更提醒", content);
        }
        return rows;
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user) {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long roleId : user.getRoleIds()) {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 新增用户部门信息
     *
     * @param user 用户对象
     */
    public void insertUserDept(User user) {
        // 新增用户与角色管理
        List<UserDept> list = new ArrayList<>();
        for (Long deptId : user.getDeptIds()) {
            UserDept ud = new UserDept();
            ud.setUserId(user.getUserId());
            ud.setDeptId(deptId);
            list.add(ud);
        }
        if (list.size() > 0) {
            userDeptMapper.batchUserDept(list);
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(User user) {
        // 新增用户与岗位管理
        List<UserPost> list = new ArrayList<UserPost>();
        for (Long postId : user.getPostIds()) {
            UserPost up = new UserPost();
            up.setUserId(user.getUserId());
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0) {
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param phonenumber 用户名
     * @return
     */
    @Override
    public String checkPhoneUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     * @return
     */
    @Override
    public String checkEmailUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId) {
        List<Post> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public User selectUserByWxOpenId(String wxOpenId) {
        return userMapper.selectUserByWxOpenId(wxOpenId);
    }
}
