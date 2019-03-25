package cn.e3mall.sso.service;

import com.e3mall.common.pojo.E3Result;

/**
 * @author haohan
 * 03/25/2019 - 04:56 下午
 * 根据token获取用户信息
 */
public interface TokenService {

    E3Result getUserByToken(String token);

}
