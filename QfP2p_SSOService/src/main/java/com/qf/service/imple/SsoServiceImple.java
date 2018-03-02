package com.qf.service.imple;

import com.qf.service.CookieUtil;
import com.qf.service.JedisUtiils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class SsoServiceImple implements com.qf.service.SsoService {
    @Override
    public boolean login(String username, String password, HttpServletResponse response)  {
        System.out.println("正在登录："+username+"--->"+password);
        if(username.length()>5 && password.length()==6){//表示登录成功---真实数据库需要改成查询校验
            //第一次登录，产生令牌
            String key="usertoken:"+ UUID.randomUUID().toString();
            //将登录信息存储到Redis中
            JedisUtiils.set(key,"{'username':'"+username+"'}");
            CookieUtil.set(key,response);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String checkLogin(String cookieValue) {
        if(cookieValue.length()>0){
            return  JedisUtiils.get("usertoken:"+cookieValue);
        }
        return null;
    }
}
