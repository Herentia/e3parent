package cn.e3mall.cart.intercepter;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haohan
 * 03/27/2019 - 05:34 下午
 * 用户登录拦截器
 */
public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return true 放行    false 拦截
        //1、从cookie中获取token
        String token = CookieUtils.getCookieValue(request, "token");
        //2、如果没有token，则用户没有登录，直接放行
        if(StringUtils.isBlank(token)) {
            return true;
        }
        //3、取到token，需要调用sso服务，获取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);
        //4、没有取到用户信息，登录时间过期，直接放行
        if(e3Result.getStatus() != 200) {
            return true;
        }
        //5、取到用户信息，登录状态
        TbUser user = (TbUser) e3Result.getData();
        //6、把用户信息存放在request中，controller中需要判断request中是否包含用户信息，放行
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
