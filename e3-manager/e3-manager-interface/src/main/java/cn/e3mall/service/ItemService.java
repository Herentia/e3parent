package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;

public interface ItemService {

    //根据单号获取订单信息
    public TbItem getItemById(long itemId);

}
