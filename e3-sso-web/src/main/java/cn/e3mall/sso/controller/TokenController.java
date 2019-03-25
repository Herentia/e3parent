package cn.e3mall.sso.controller;

import cn.e3mall.sso.service.TokenService;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author haohan
 * 03/25/2019 - 05:04 下午
 * 根据Token查询用户信息Controller
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public E3Result getUserByToken(@PathVariable String token) {

        return null;
    }

}
