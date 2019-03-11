package cn.e3mall.portal.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author haohan
 * 03/07/2019 - 04:24 下午
 * 首页请求控制器
 */
@Controller
public class IndexController {

    @Value("${CONTENT_LUNBO_ID}")
    private long CONTENT_LUNBO_ID;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String goToIndex(Model model) {
        //查询内容列表
        List<TbContent> list = contentService.getContentListByCid(CONTENT_LUNBO_ID);
        //将结果传递给页面
        model.addAttribute("ad1List", list);
        return "index";
    }

}
