package cn.e3mall.sso.service.impl;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author haohan
 * 03/25/2019 - 04:57 下午
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public E3Result getUserByToken(String token) {
        //根据token从redis中取出用户信息
        String json = jedisClient.get("SESSION:" + token);
        //如果取不到用户信息，则session已经过去
        if(!StringUtils.isBlank(json)) {
            return E3Result.build(201, "用户登录已经过期！");
        }
        //取到用户信息更新token过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //返回用户信息
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return E3Result.ok(user);
    }
}
