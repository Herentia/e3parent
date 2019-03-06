package cn.e3mall.service;

import com.e3mall.common.EasyUITreeNode;

import java.util.List;

/**
 * @author haohan
 * 03/04/2019 - 05:01 下午
 * 商品分类管理
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);

}
