package cn.e3mall.contentCat;

import cn.e3mall.content.service.ContentCategoryService;
import com.e3mall.common.pojo.EasyUITreeNode;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author haohan
 * 03/08/2019 - 10:51 上午
 */
public class ContentCatTest {

    static {

    }

//    @Autowired
//    ContentCategoryService categoryService;

    @Test
    public void contentCatList() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        ContentCategoryService categoryService = (ContentCategoryService) applicationContext.getBean("contentCategoryServiceImpl");
        System.out.println(categoryService);
        List<EasyUITreeNode> contentCatList = categoryService.getContentCatList(0);
        System.out.println(contentCatList);
    }

}
