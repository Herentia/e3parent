package cn.e3mall.sso.service.impl;

import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import com.e3mall.common.pojo.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author haohan
 * 03/20/2019 - 05:36 下午
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result cheakData(String param, int type) {
        //根据不同的type生成不同的查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1、姓名  2、手机号   3、邮箱
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            //返回错误信息
            return E3Result.build(400, "数据类型错误");
        }
        //执行查询
        List<TbUser> users = userMapper.selectByExample(example);
        //判断结果是否包含了数据
        if(users != null && users.size() > 0) {
            //如果有数据返回false
            return E3Result.ok(false);
        }
        //没有数据则返回true
        return E3Result.ok(true);
    }

    @Override
    public E3Result Register(TbUser user) {
        //数据有效性验证
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPhone()) ||
        StringUtils.isBlank(user.getPassword())) {
            E3Result.build(400, "用户数据不完整，注册失败");
        }
        //1、用户名     2、手机号       3、邮箱
        E3Result e3Result = cheakData(user.getUsername(), 1);
        if(!(Boolean) e3Result.getData()) {
            return E3Result.build(400, "用户名已被占用！");
        }
        e3Result = cheakData(user.getPhone(), 2);
        if(!(Boolean) e3Result.getData()) {
            return E3Result.build(400, "手机号已被占用！");
        }
        //补全pojo属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //使用MD5对密码进行加密
        String md5pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5pass);
        //把用户数据插入到用户表中
        userMapper.insert(user);
        //返回处理结果
        return E3Result.ok();
    }
}
