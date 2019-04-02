package cn.e3mall.order.service;

import cn.e3mall.order.pojo.OrderInfo;
import com.e3mall.common.pojo.E3Result;

/**
 * @author haohan
 * 04/02/2019 - 03:29 下午
 */
public interface OrderService {

    /**
     * 新增订单详情
     * @param orderInfo 订单详情pojo
     * @return E3Result
     */
    E3Result createOrder(OrderInfo orderInfo);

}
