package cn.e3mall.sso.controller;

import cn.e3mall.sso.service.RegisterService;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author haohan
 * 03/20/2019 - 05:37 下午
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkDate(@PathVariable String param, @PathVariable Integer type) {
        E3Result e3Result = registerService.cheakData(param, type);
        return e3Result;
    }

}
