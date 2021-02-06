package com.loemin.crm.settings.service.impl;

import com.loemin.crm.exception.LoginException;
import com.loemin.crm.settings.dao.UserDao;
import com.loemin.crm.settings.domain.User;
import com.loemin.crm.settings.service.UserService;
import com.loemin.crm.utils.DateTimeUtil;
import com.loemin.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = dao.login(map);
        if (user == null) {
            throw new LoginException("账号或密码错误");
        }
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0) {
            throw new LoginException("账号已失效");
        }
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("账号已锁定");
        }
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)) {
            throw new LoginException("IP地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return dao.getUserList();
    }
}
