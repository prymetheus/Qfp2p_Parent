package com.qf.sso;

import com.qf.service.JedisUtiils;
import com.qf.service.SsoService;
import com.qf.service.imple.SsoServiceImple;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SsoController {
    @RequestMapping("/login.do")
    public void login(String name, String pass, HttpServletResponse response,String callback) throws IOException {
        System.out.println("SSO系统登录："+name+"--->"+pass);
        SsoService ssoService=new SsoServiceImple();
        String result="{'code':404}";
        if(ssoService.login(name,pass,response)){
          result="{'code':200}";
        }
        response.getWriter().print(callback+"(\""+result+"\")");
    }
    //根据传递的Cookie的令牌校验登录信息的合法性和返回登录信息
    @RequestMapping("/checklogin.do")
    public void check(String ck,String callback,HttpServletResponse response) throws IOException {
       String result= JedisUtiils.get(ck);
       if(result!=null && result.length()>0){
           //更新Redis的超时时间
           JedisUtiils.setTime(ck);
           result="{'code':200,'user':"+result+"}";
       }else {
           result="{'code':404,'user':{}}";
       }
       response.getWriter().print(callback+"(\""+result+"\")");
    }
    //退出登录
    @RequestMapping("/loginout.do")
    public void loginout(String ck, HttpServletRequest request, HttpServletResponse response){
        //销毁Redis
        JedisUtiils.remove(ck);
        Cookie[] arrC=request.getCookies();
        Cookie cookie=null;
        for(Cookie c:arrC){
            if(c.getName().equals("usertoken")){
                cookie=c;
                break;
            }
        }
        cookie.setMaxAge(0);
        response.addCookie(cookie);


    }

}
