package cn.e3mall.register;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;
import cn.e3mall.sso.service.impl.RegisterServiceImpl;
import com.e3mall.common.pojo.E3Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author haohan
 * 03/25/2019 - 11:39 上午
 */
public class RegisterTest {

    @Autowired
//    private RegisterService registerService;
    RegisterService registerService = new RegisterServiceImpl();

    @Test
    public void register() {
        TbUser user = new TbUser();
        user.setUsername("haohan");
        user.setPassword("123");
        user.setPhone("1232321312312");
        E3Result result = registerService.Register(user);
        System.out.println(result);
    }

}
