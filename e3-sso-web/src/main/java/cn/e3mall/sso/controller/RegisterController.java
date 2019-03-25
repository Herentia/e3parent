package cn.e3mall.sso.controller;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;
import com.e3mall.common.pojo.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author haohan
 * 03/20/2019 - 05:37 下午
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/{value}")
    public String showRegister(@PathVariable String value) {
        return value;
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkDate(@PathVariable String param, @PathVariable Integer type) {
        E3Result e3Result = registerService.cheakData(param, type);
        return e3Result;
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/user/register")
    @ResponseBody
    public E3Result register(TbUser user) {
        E3Result result = registerService.Register(user);
        return result;
    }

}
