package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haohan
 * 03/17/2019 - 11:25 下午
 * 商品详细页面展示控制器
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        //获取商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //获取商品描述信息
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //将信息传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        //返回逻辑视图
        return "item";
    }

}
