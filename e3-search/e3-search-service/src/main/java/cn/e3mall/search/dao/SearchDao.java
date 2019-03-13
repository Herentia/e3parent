package cn.e3mall.search.dao;

import com.e3mall.common.pojo.SearchItem;
import com.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author haohan
 * 03/13/2019 - 03:51 下午
 * 商品搜索dao
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 根据查询条件查询索引库
     * @param query 查询条件
     * @return 索引库查询结果
     */
    public SearchResult search(SolrQuery query) throws Exception {
        //根据条件查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        //获取查询结果
        SolrDocumentList results = queryResponse.getResults();
        //获取查询总记录数
        long numFound = results.getNumFound();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        //获取商品列表，需要取出高亮信息
        List<SearchItem> list = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for(SolrDocument solrDocument : results) {
            SearchItem item = new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            //获取高亮信息
            List<String> titles = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if(titles != null && titles.size() > 0) {
                title = titles.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            //添加到列表
            list.add(item);
        }
        searchResult.setItemList(list);
        //返回结果
        return searchResult;
    }

}
