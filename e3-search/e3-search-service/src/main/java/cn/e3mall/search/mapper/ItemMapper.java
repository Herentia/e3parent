package cn.e3mall.search.mapper;

import com.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * @author haohan
 * 03/12/2019 - 04:38 下午
 * 商品搜索mapper接口
 */
public interface ItemMapper {

    List<SearchItem> getItemList();

    /**
     * 根据商品ID获取商品索引库信息
     * @param itemId 商品ID
     * @return 商品索引库信息
     */
    SearchItem getItemById(Long itemId);

}
