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

}
