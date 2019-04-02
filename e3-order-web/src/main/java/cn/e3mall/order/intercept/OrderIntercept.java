package cn.e3mall.order.intercept;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.CookieUtils;
import com.e3mall.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haohan
 * 04/01/2019 - 05:28 下午
 * 订单用户登录拦截器
 */
public class OrderIntercept implements HandlerInterceptor {

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取token
        String token = CookieUtils.getCookieValue(request, "token");
        //判断token是否存在
        if(StringUtils.isBlank(token)) {
            //token不存在，则没有登录，跳转到sso登录页面，登录成功后跳转回当前请求url
            response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURI());
            //拦截
            return false;
        }
        // 若存在则到redis中查询登录是否过期
        E3Result e3Result = tokenService.getUserByToken(token);
        if(e3Result.getStatus() != 200) {
            //用户登录过期
            response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURI());
            //拦截
            return false;
        }
        //如果用户已登录，则将用户信息存入request
        TbUser user = (TbUser) e3Result.getData();
        request.setAttribute("user", user);
        //判断cookie中是否有购物车数据，有则和服务端数据进行合并
        String jsonCart = CookieUtils.getCookieValue(request, "cart", true);
        if(StringUtils.isNotBlank(jsonCart)) {
            //合并购物车
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCart, TbItem.class));
        }
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
