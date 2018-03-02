//登录
function login(formid,url) {
    //跨域请求
    $.ajax(
        {
            "method":"get",
            "data":$("#"+formid).serialize(),
            //"url":"http://localhost:8080/sw/login.do",
            "url":url,
            "dataType":"jsonp",
            "success":function (data) {

                var code=eval("("+data+")");

                if(code.code==200){
                    window.location.href="index.html";
                }else {
                    alert("登录失败");
                }
            }});
}
//根据Cookie校验是否已经登录
function checkCookie(cookieKey,url) {
    //javaScript操作Cookie
    //document.cookie
    //jQuery操作Cookie
   // var ck=$.cookie("usertoken");
    var ck=$.cookie(cookieKey);
    alert(ck==undefined);
    if(ck==undefined){//如果不存在Cookie,说明没有登录过
        $("#hmsg").html("<a href=\"login.html\">快点登录</a>");

    }else {//存在Cookie的令牌
        //如果存在Cookie,那么需要跨域请求校验登录的信息，获取登录信息
        //跨域请求
        $.ajax(
            {"method":"get",
                "data":"ck="+ck,
                //"url":"http://localhost:8080/sw/checklogin.do",
                "url":url,
                "dataType":"jsonp",
                "success":function (data) {
                    var code=eval("("+data+")");
                    console.log(code);
                    if(code.code==200){
                        $("#hmsg").html("欢迎："+code.user.username+"<br/><a href='http://localhost:8080/sw/loginout.do?ck="+ck+"'>退出登录</a>");
                    }else {//登录失败

                        $("#hmsg").html("<a href=\"login.html\">快点登录</a>");
                    }
                }});
    }
}
