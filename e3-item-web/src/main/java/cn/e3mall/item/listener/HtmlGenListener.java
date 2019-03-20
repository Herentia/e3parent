package cn.e3mall.item.listener;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haohan
 * 03/19/2019 - 05:18 下午
 * 监听商品添加消息，当有商品添加时就生成对应的静态html商品详情页面
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("HTML_GEN_PATH")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        Writer writer = null;
        try {
            //创建模板
            //从消息中间件中获取商品ID
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = Long.parseLong(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据ID获取商品基本信息
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            //ID获取商品描述信息
            TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
            //创建一个数据集。封装数据
            Map<String, Object> data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", tbItemDesc);
            //加载模板对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //创建一个输出流，指定输出的目录路径及文件名
            writer = new FileWriter(HTML_GEN_PATH + itemId + ".html");
            //生成静态页面
            template.process(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            //关闭流
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
