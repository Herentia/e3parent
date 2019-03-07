package cn.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haohan
 * 03/07/2019 - 04:24 下午
 * 首页请求控制器
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String goToIndex() {
        return "index";
    }

}
