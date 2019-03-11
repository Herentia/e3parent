package cn.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author haohan
 * 03/11/2019 - 06:04 下午
 */
public class JedisTest {

    @Test
    public void testJedisCluster() throws Exception {
        //创建一个jedisCluster对象,有个nodes参数，是一个set类型，set中包含若干个HostAndPort对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("47.107.243.254", 7001));
        nodes.add(new HostAndPort("47.107.243.254", 7002));
        nodes.add(new HostAndPort("47.107.243.254", 7003));
        nodes.add(new HostAndPort("47.107.243.254", 7004));
        nodes.add(new HostAndPort("47.107.243.254", 7005));
        nodes.add(new HostAndPort("47.107.243.254", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        //直接使用jedisCluster操作redis
        jedisCluster.set("key2", "hello world");
        //取出对象
        String key2 = jedisCluster.get("key2");
        System.out.println(key2);
        //关闭连接
        jedisCluster.close();
    }

}
