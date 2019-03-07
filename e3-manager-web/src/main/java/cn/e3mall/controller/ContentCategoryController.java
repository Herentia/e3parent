package cn.e3mall.controller;

import cn.e3mall.content.service.ContentCategoryService;
import com.e3mall.common.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author haohan
 * 03/07/2019 - 11:15 下午
 */
@Controller
public class ContentCategoryController {

    //contentCategoryService
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 获取内容树目录
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> contentCatList = contentCategoryService.getContentCatList(parentId);
        return contentCatList;
    }


}
