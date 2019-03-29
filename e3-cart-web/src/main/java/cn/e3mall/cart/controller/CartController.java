package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.CookieUtils;
import com.e3mall.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haohan
 * 03/27/2019 - 02:14 下午
 * 商品购物车Controller
 */
@Controller
public class CartController {

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    @RequestMapping("cart/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user != null) {
            //如果登录,将购物车信息保存到服务端
            cartService.addCart(user.getId(), itemId, num);
            //返回逻辑视图
            return "cartSuccess";
        }
        //如果没有登录，则将购物车信息保存到cookie
        //1、从cookie中查询商品列表
        List<TbItem> cartList = getCartList(request);
        //2、判断添加商品在商品列表中是否存在
        Boolean hasItem = false;
        for(TbItem item : cartList) {
            //对象比较的是地址，应该是值的比较
            if(item.getId() == itemId.longValue()) {
                //3、如果存在商品数量增加
                item.setNum(item.getNum() + num);
                hasItem = true;
                break;
            }
        }

        if(!hasItem) {
            //4、如果商品不存在购物车则查询商品
            TbItem item = itemService.getItemById(itemId);
            //取一张照片
            String image = item.getImage();
            if(StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //设置商品购买数量
            item.setNum(num);
            //5、将商品加入购物车列表
            cartList.add(item);
        }
        //将购物车商品写入cookie
        CookieUtils.setCookie(request, response, "cart",
                JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        return "cartSuccess";
    }

    /**
     * 从cookie中获取购物车
     * @return 购物车列表
     */
    private List<TbItem> getCartList(HttpServletRequest request) {
        //取购物车列表
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        //判断cart是否为空
        if(StringUtils.isNotBlank(cart)) {
            //把cart转换成商品列表返回
            List<TbItem> list = JsonUtils.jsonToList(cart, TbItem.class);
            return list;
        }
        //为空则返回空列表
        return new ArrayList<>();
    }

    /**
     * 查看购物车
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //未登录状态，从cookie获取购物车信息
        //获取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if(user != null) {
            //如果是登录状态则需要将服务端和cookie中的购物车中商品进行合并
            cartService.mergeCart(user.getId(), cartList);
            //合并完成后，删除cookie中购物车，只取服务端购物车信息
            CookieUtils.deleteCookie(request, response, "cart");
            cartList = cartService.getCartList(user.getId());
        }
        //传递给页面
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * 更新购物车数量
     * @param itemId    商品ID
     * @param num       商品数量
     * @param request
     * @param response
     * @return          E3Result
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    public E3Result updateCartList(@PathVariable Long itemId, @PathVariable Integer num,
                                   HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user != null) {
            //如果登录则从redis中修改商品数量
            E3Result e3Result = cartService.updateCartNum(user.getId(), itemId, num);
            return e3Result;
        }
        //如果没有登录，则从cookie中修改商品数量
        //从cookie中取出购物车中的商品列表
        List<TbItem> cartList = getCartList(request);
        //遍历商品列表找到对应商品
        for(TbItem item : cartList) {
            if(item.getId().longValue() == itemId) {
                //更新商品数量
                item.setNum(num);
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);
        //返回成功
        return E3Result.ok();
    }

    /**
     * 删除购物车中商品
     * @param itemId    商品ID
     * @param request
     * @param response
     * @return          重定向回购物车
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartList(@PathVariable Long itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user != null) {
            //如果登录，则删除redis中指定商品
            cartService.deleteCartItem(user.getId(), itemId);
            //返回逻辑视图
            return "redirect:/cart/cart.html";
        }
        //从cookie中取出购物车中的商品列表
        List<TbItem> cartList = getCartList(request);
        //遍历商品列表找到对应商品
        for(TbItem item : cartList) {
            if(item.getId().longValue() == itemId) {
                //从购物车中删除商品
                cartList.remove(item);
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);
        //返回逻辑视图
        return "redirect:/cart/cart.html";
    }

}
