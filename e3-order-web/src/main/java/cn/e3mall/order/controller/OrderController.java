package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import com.e3mall.common.pojo.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author haohan
 * 04/01/2019 - 04:30 下午
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(@RequestAttribute TbUser user, Model model) {
        //根据用户ID获取购物车列表
        List<TbItem> cartList = cartService.getCartList(user.getId());
        //将购物车列表传给前端jsp
        model.addAttribute("cartList", cartList);
        return "order-cart";
    }

    /**
     * 提交订单
     * @param orderInfo 订单详情
     * @param request
     * @param user  用户信息
     * @return
     */
    @PostMapping("/order/create")
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request,
                              @RequestAttribute TbUser user) {
        //将用户信息添加到orderInfo
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //调用服务生成订单
        E3Result e3Result = orderService.createOrder(orderInfo);
        //判断生成订单是否成功
        if(e3Result.getStatus() == 200) {
            //生成订单成功则删除购物车中信息
            cartService.clearCartItem(user.getId());
        }
        //将订单号和付款信息传递给页面
        request.setAttribute("orderId", e3Result.getData());
        request.setAttribute("payment", orderInfo.getPayment());
        return "success";
    }

}
