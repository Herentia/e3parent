package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author haohan
 * 04/01/2019 - 04:30 下午
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(@RequestAttribute TbUser user, Model model) {
        //根据用户ID获取购物车列表
        List<TbItem> cartList = cartService.getCartList(user.getId());
        //将购物车列表传给前端jsp
        model.addAttribute("cartList", cartList);
        return "order-cart";
    }

}
