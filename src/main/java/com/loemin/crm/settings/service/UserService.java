package com.loemin.crm.settings.service;

import com.loemin.crm.exception.LoginException;
import com.loemin.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
