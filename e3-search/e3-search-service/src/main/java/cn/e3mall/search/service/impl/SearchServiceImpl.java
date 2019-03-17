package cn.e3mall.search.service.impl;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import com.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author haohan
 * 03/13/2019 - 04:49 下午
 * 获取索引库商品信息
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) {
        SearchResult result = null;
        try {
            //创建一个SolrQuery对象
            SolrQuery query = new SolrQuery();
            //设置查询条件
            query.setQuery(keyword);
            //设置分页信息
            if(page <= 0)
                page = 1;
            query.setStart((page - 1) * rows);
            query.setRows(rows);
            //设置默认搜索域
            query.set("df", "item_title");
            //开启高亮显示
            query.setHighlight(true);
            query.addHighlightField("item_title");
            query.setHighlightSimplePre("<em style=\"color:red\">");
            query.setHighlightSimplePost("</em>");
            //执行查询
            result = searchDao.search(query);
            //计算总页数
            Long recordCount = result.getRecordCount();
            int totalPage = (int) (recordCount / rows);
            if(recordCount % rows > 0)
                totalPage ++;
            //添加返回结果
            result.setTotalPage(totalPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
