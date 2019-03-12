package cn.e3mall.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author haohan
 * 03/12/2019 - 04:54 下午
 */
public class SolrTest {

    @Test
    public void addDocument() throws Exception {
        //创建一个SolrServer对象，创建一个连接
        SolrServer solrServer = new HttpSolrServer("http://47.107.243.254:8080/solr-4.10.3/collection1");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //像文档对象中添加域
        document.addField("id", "docTest");
        document.addField("item_title", "测试");
        document.addField("item_price", 99999);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

}
