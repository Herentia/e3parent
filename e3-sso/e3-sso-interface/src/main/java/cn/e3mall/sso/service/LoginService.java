package cn.e3mall.sso.service;

import com.e3mall.common.pojo.E3Result;

/**
 * @author haohan
 * 03/20/2019 - 03:20 下午
 */
public interface LoginService {

    /**
     * 参数：用户名和密码
     * 业务逻辑：
     * 1、判断用户名和密码是否正确
     * 2、如果不正确返回登录失败
     * 3、如果正生成token
     * 4、把信息写入redis；key为token   value为用户信息
     * 5、设置session的过期时间
     * 6、将token返回
     */
    E3Result userLogin(String username, String password);

}
