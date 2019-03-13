package com.e3mall.pageHelper;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author haohan
 * 03/04/2019 - 11:55 上午
 */
public class PageHelperTest {

    @Test
    public void testPageHelper() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
        //设置每页显示记录数
        PageHelper.startPage(1, 10);
        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        //获取tbItem页面信息
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);
        System.out.println(tbItemPageInfo.getTotal());
        System.out.println(tbItemPageInfo.getPages());
    }


}
