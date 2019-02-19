package cn.e3mall.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemServiceImpl;

    @Autowired
    private DataSource dataSource;

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
//        System.out.println(dataSource);
        try {
            //加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //1.getConnection()方法，连接MySQL数据库！！
            Connection coon = DriverManager.getConnection("jdbc:mysql://localhost:3306/e3mall?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            System.out.println(coon);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/item/{itemId}")
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem item = itemServiceImpl.getItemById(itemId);
        return item;
    }

}
