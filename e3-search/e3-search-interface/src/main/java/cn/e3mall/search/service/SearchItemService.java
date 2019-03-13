package cn.e3mall.search.service;

import com.e3mall.common.pojo.E3Result;

/**
 * @author haohan
 * 03/13/2019 - 11:06 上午
 * 搜索引擎服务接口
 */
public interface SearchItemService {

    /**
     * 将商品信息保存带搜索引擎服务器
     * @return
     */
    E3Result importAllItems();

}
