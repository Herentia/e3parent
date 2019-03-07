package cn.e3mall.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 通过订单ID回显商品信息
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/param/item/query/{itemId}")
    public String getItemById(@PathVariable Long itemId) {
        TbItem item = itemService.getItemById(itemId);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        map.put("status", 200);
        dataMap.put("paramData", item);
        dataMap.put("id", itemId);
        map.put("data", dataMap);
        return JsonUtils.objectToJson(map);
    }

    /**
     * 通过ID回显商品描述信息
     * @param itemId
     * @return
     * /rest/item/query/item/desc/
     */
    @ResponseBody
    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    public Map getDescById(@PathVariable Long itemId) {
        String desc = itemService.getDescById(itemId);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        System.out.println(desc);
        map.put("status", 200);
        dataMap.put("itemDesc", desc);
        map.put("data", dataMap);
        System.out.println(map);
        return map;
    }

    /**
     * 获取商品分页
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @GetMapping("/item/list")
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    /**
     * 新增商品
     */
    @PostMapping("/item/save")
    @ResponseBody
    public E3Result addItem(TbItem item, String desc) {
        E3Result e3Result = itemService.addItem(item, desc);
        return e3Result;
    }

    /**
     * 跳转到后台首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    /**
     * 跳转到对应页面
     */
    @RequestMapping("/{page}")
    public String page(@PathVariable String page) {
        return page;
    }

}
