package cn.e3mall.order.service.impl;

import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author haohan
 * 04/02/2019 - 03:31 下午
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;
    @Value("${ORDER_DETAIL_ID_START}")
    private String ORDER_DETAIL_ID_START;

    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        //生成订单单号，使用redis的incr生成
        if(jedisClient.exists(ORDER_ID_GEN_KEY)) {
            //如果没有订单单号则新增一个默认单号
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //补全orderInfo属性
        orderInfo.setOrderId(orderId);
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //插入订单表
        orderMapper.insert(orderInfo);

        //向订单明细表插入数据
        for(TbOrderItem orderItem : orderInfo.getOrderItems()) {
            //生成明细ID
            if(jedisClient.exists(ORDER_DETAIL_ID_GEN_KEY)) {
                jedisClient.set(ORDER_DETAIL_ID_GEN_KEY, ORDER_DETAIL_ID_START);
            }
            String orderDetailId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
            //补全pojo
            orderItem.setId(orderDetailId);
            orderItem.setOrderId(orderId);
            //向明细表插入数据
            orderItemMapper.insert(orderItem);
        }

        //向订单地址表中插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        //补全pojo属性
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        //返回结果，包含订单号
        return E3Result.ok(orderId);
    }
}
