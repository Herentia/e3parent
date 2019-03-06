package com.e3mall.fastDFS;

import com.e3mall.utils.FastDFSClient;
import org.junit.Test;

/**
 * @author haohan
 * 03/05/2019 - 04:59 下午
 */
public class fastdfsTest {

    //使用全局对象加载配置文件

    //创建一个TrackerClient对象

    //通过TrackerClient对象回去TrackerServer对象

    //创建一个storageServer的引用，可以是null

    //创建一个storageClient，参数需要TrackerServer和storageServer

    //使用storageClient上传文件

    @Test
    public void test1() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("F:/IdeaMaven/e3parent/e3-manager-web/src/main/resources/conf/client.conf");
        String str = fastDFSClient.uploadFile("D:/Pictures/chengbao.jpg");
        System.out.println(str);
    }

}
