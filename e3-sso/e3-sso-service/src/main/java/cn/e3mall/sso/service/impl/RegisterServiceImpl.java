package cn.e3mall.sso.service.impl;

import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
