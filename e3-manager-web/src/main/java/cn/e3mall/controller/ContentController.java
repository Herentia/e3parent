package cn.e3mall.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理控制器
 * @author haohan
 * 03/08/2019 - 01:53 下午
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent content) {
        //调用服务将数据保存到数据库
        E3Result e3Result = contentService.addContent(content);
        return e3Result;
    }

}
