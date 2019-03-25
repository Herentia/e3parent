package cn.e3mall.sso.service;

import cn.e3mall.pojo.TbUser;
import com.e3mall.common.pojo.E3Result;

/**
 * @author haohan
 * 03/20/2019 - 05:35 下午
 */
public interface RegisterService {

    /**
     * 用户数据有效性验证
     * @param param 用户数据
     * @param type 类型
     * @return E3Result
     */
    E3Result cheakData(String param, int type);

    /**
     * 注册用户
     * @param user
     * @return
     */
    E3Result Register(TbUser user);

}
