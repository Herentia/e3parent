package cn.e3mall.cart.service;

import cn.e3mall.pojo.TbItem;
import com.e3mall.common.pojo.E3Result;

import java.util.List;

/**
 * @author haohan
 * 03/29/2019 - 02:18 下午
 * 购物车服务
 */
public interface CartService {

    /**
     * 将购物车信息添加到服务端
     * @param userId    用户ID
     * @param itemId    商品ID
     * @param num       商品数量
     * @return          E3Result
     */
    public E3Result addCart(long userId, long itemId, int num);

    /**
     * 合并cookie和redis中的购物车商品信息
     * @param userId    用户ID
     * @param itemList  购物车商品列表
     * @return          E3Result
     */
    public E3Result mergeCart(long userId, List<TbItem> itemList);

    /**
     * 根据用户ID获取该用户购物车中的商品详情
     * @param userId    用户ID
     * @return          List<TbItem>
     */
    public List<TbItem> getCartList(long userId);

    /**
     * 更新购物车商品数量
     * @param userId    用户ID
     * @param itemId    商品ID
     * @param num       商品数量
     * @return          E3Result
     */
    public E3Result updateCartNum(long userId, long itemId, int num);

    /**
     * 删除购物车中指定商品
     * @param userId    用户ID
     * @param itemId    商品ID
     * @return          E3Result
     */
    public E3Result deleteCartItem(long userId, long itemId);

}
