package cn.e3mall.search.service.impl;

import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haohan
 * 03/13/2019 - 11:07 上午
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public E3Result importAllItems() {
        try {
            //获取要上传到搜索引擎服务器的商品信息
            List<SearchItem> itemList = itemMapper.getItemList();
            //循环遍历将商品信息上传到搜索引擎
            for(SearchItem item : itemList) {
                //创建文档
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_price", item.getPrice());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                //将文档写入搜索引擎
                solrServer.add(document);
            }
            //提交
            solrServer.commit();
            //返回结果
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "导入数据到搜索引擎服务器失败");
        }
    }
}
