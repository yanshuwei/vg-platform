package com.vg.project.monitor.job.factory;

import com.vg.common.constant.Constants;
import com.vg.common.utils.LogUtils;
import com.vg.common.utils.ServletUtils;
import com.vg.common.utils.spring.SpringUtils;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.framework.util.AddressUtils;
import com.vg.project.monitor.logininfo.domain.Logininfo;
import com.vg.project.monitor.logininfo.service.LogininfoServiceImpl;
import com.vg.project.monitor.online.OnlineSession;
import com.vg.project.monitor.online.UserOnline;
import com.vg.project.monitor.online.service.IUserOnlineService;
import com.vg.project.monitor.operlog.domain.OperLog;
import com.vg.project.monitor.operlog.service.IOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author liuhulu
 *
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     * 
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                UserOnline online = new UserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLonginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                online.setSession(session);
                SpringUtils.getBean(IUserOnlineService.class).saveOnline(online);

            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfo(final String username, final String status, final String message, final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask()
        {
            @Override
            public void run()
            {
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(AddressUtils.getRealAddressByIP(ip));
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                Logininfo logininfo = new Logininfo();
                logininfo.setLoginName(username);
                logininfo.setIpaddr(ip);
                logininfo.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
                logininfo.setBrowser(browser);
                logininfo.setOs(os);
                logininfo.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status))
                {
                    logininfo.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfo.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(LogininfoServiceImpl.class).insertLogininfo(logininfo);
            }
        };
    }
}
