package cn.e3mall.search.controller;

import cn.e3mall.search.service.SearchService;
import com.e3mall.common.pojo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * @author haohan
 * 03/13/2019 - 02:48 下午
 * 查询索引库控制器
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    /**
     * 根据keyword在索引库中查看商品
     * @return
     */
    @GetMapping("/search")
    public String SearchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page,
                                 Model model) {
//        try {
            //get请求时，字符串因为tomcat字符编码的原因会产生乱码
//            keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
            //查询商品列表
            SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
            //把结果传递给页面
            model.addAttribute("query", keyword);
            model.addAttribute("totalPages", searchResult.getTotalPage());
            model.addAttribute("page", page);
            model.addAttribute("recourdCount", searchResult.getRecordCount());
            model.addAttribute("itemList", searchResult.getItemList());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        //返回逻辑视图
        return "search";
    }

}
