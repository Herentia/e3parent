package cn.e3mall.service.impl;

import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.e3mall.common.EasyUIDataGridResult;
import com.e3mall.common.E3Result;
import com.e3mall.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    /**
     * 通过商品ID获取商品信息
     * @param itemId
     * @return
     */
    @Override
    public TbItem getItemById(long itemId) {
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }

    @Override
    public String getDescById(long itemId) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        String desc = itemDesc.getItemDesc();
        return desc;
    }

    /**
     * 获取订单分页信息
     * @param page                      当前页数
     * @param rows                      每页显示记录数
     * @return EasyUIDataGridResult     分页每页信息
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(tbItemExample);
        //创建一个返回值对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //获取并设置总记录数
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        return easyUIDataGridResult;
    }

    /**
     * 添加商品信息
     * @param item
     * @param desc
     * @return
     */
    @Override
    public E3Result addItem(TbItem item, String desc) {
        //获取商品ID
        long itemId = IDUtils.genItemId();
        //补全商品属性
        item.setId(itemId);
        //1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表中插入数据
        itemMapper.insert(item);
        //创建一个商品描述对应的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //将描述插入商品描述表中
        itemDescMapper.insert(itemDesc);
        //返回插入结果
        return E3Result.ok();
    }
}
