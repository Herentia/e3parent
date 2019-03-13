package cn.e3mall.controller;

import cn.e3mall.search.service.SearchItemService;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author haohan
 * 03/13/2019 - 02:10 下午
 * 将商品信息导入索引库
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @PostMapping("/index/item/import")
    @ResponseBody
    public E3Result importAllItem() {
        E3Result e3Result = searchItemService.importAllItems();
        return e3Result;
    }

}
