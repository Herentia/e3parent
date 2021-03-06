package cn.e3mall.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author haohan
 * 03/15/2019 - 12:22 下午
 */
public class CloudSolrTest {

    /**
     * zkHost逗号隔开的时候一定不能用空格！！！
     */
    private static String zkHost = "47.107.243.254:2181,47.107.243.254:2182,47.107.243.254:2183";

    @Test
    public void testAddDocument() throws Exception {
        //创建一个集群的连接，应该使用CloudSolrServer创建。
        CloudSolrServer solrServer = new CloudSolrServer(zkHost);
        //zkHost：zookeeper的地址列表
        //设置一个defaultCollection属性。
        solrServer.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.setField("id", "solrcloud01");
        document.setField("item_title", "测试商品01");
        document.setField("item_price", 123);
        //把文件写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();

    }

    @Test
    public void testQueryDocument() throws Exception {
        //创建一个CloudSolrServer对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("47.107.243.254:2181,47.107.243.254:2182,47.107.243.254:2183");
        //设置默认的Collection
        cloudSolrServer.setDefaultCollection("collection2");
        //创建一个查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("*:*");
        //执行查询
        QueryResponse queryResponse = cloudSolrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("总记录数：" + solrDocumentList.getNumFound());
        //打印
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("title"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_price"));
        }
    }

}
