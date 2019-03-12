package cn.e3mall.controller;

import cn.e3mall.content.service.ContentCategoryService;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 新增内容子节点
     */
    @PostMapping("/content/category/create")
    @ResponseBody
    public E3Result addContentCategory(Long parentId, String name) {
        //调用服务添加子节点
        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
        return e3Result;
    }


}
