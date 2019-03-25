package cn.e3mall.sso.service.impl;

import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author haohan
 * 03/20/2019 - 03:19 下午
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
        //* 1、判断用户名和密码是否正确
        //根据用户名查询用户信息
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        //执行查询
        List<TbUser> users = userMapper.selectByExample(userExample);
        if(users == null || users.size() == 0) {
            //返回登录失败
            return E3Result.build(400, "用户名或密码错误！");
        }
        //取出用户信息
        TbUser user = users.get(0);
        //* 2、如果不正确返回登录失败
        //判断密码是否正确
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return E3Result.build(400, "用户名或密码错误！");
        }
        //* 3、如果正生成token
        String token = UUID.randomUUID().toString();
        //* 4、把信息写入redis；key为token   value为用户信息
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
        //* 5、设置session的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //* 6、将token返回
        return E3Result.ok(token);
    }

}
