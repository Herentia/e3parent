package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;
import com.e3mall.common.EasyUIDataGridResult;

public interface ItemService {

    //根据单号获取订单信息
    public TbItem getItemById(long itemId);

    //获取订单分页
    public EasyUIDataGridResult getItemList(int page, int rows);

}
