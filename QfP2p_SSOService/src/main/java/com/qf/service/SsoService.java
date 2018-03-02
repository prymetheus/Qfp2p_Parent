package com.qf.service;

import javax.servlet.http.HttpServletResponse;

public interface SsoService {
    //登录
    boolean login(String username, String password, HttpServletResponse response);
    //获取登录信息
    String checkLogin(String cookieValue);

}
