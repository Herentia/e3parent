package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haohan
 * 03/29/2019 - 02:21 下午
 * 购物车处理服务
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public E3Result addCart(long userId, long itemId, int num) {
        //向redis中添加购物车中商品信息
        //判断redis中是否已经存在购物车中的商品
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        if(hexists) {
            //如果redis中存在商品，则在原商品数量上增加购物车中该商品的新增数量
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            item.setNum(item.getNum() + num);
            //将新商品信息写回redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        //如果redis中不存在该购物车中的商品，则查询商品，将商品信息存入redis
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        item.setNum(num);
        String images = item.getImage();
        //取一张图片地址存入商品信息
        if(StringUtils.isNotBlank(images)) {
            item.setImage(images.split(",")[0]);
        }
        //将购物车商品信息存入redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车
        //判断购物车中是否有此商品
        //如果有，数量相加
        //如果没有添加新的商品
        for(TbItem item : itemList) {
            addCart(userId, item.getId(), item.getNum());
        }
        //返回成功
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //获取redis中该用户购物车中的所有商品
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for(String itemStr : jsonList) {
            itemList.add(JsonUtils.jsonToPojo(itemStr, TbItem.class));
        }
        return itemList;
    }

    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {
        //从redis中获取购物车中商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        //修改item中num值
        item.setNum(num);
        //将修改后的商品信息写回redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(long userId, long itemId) {
        //从redis中删除指定key的商品
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();
    }
}
