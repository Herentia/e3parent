package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.pojo.E3Result;

public interface ItemService {

    //根据单号获取商品信息
    public TbItem getItemById(long itemId);

    //根据ID获取商品描述信息
    String getDescById(long itemId);

    //获取商品分页
    public EasyUIDataGridResult getItemList(int page, int rows);

    //添加商品
    E3Result addItem(TbItem item, String desc);

    //根据商品ID获取商品描述表的信息
    TbItemDesc getItemDescById(long itemId);



}
