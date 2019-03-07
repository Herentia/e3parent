package cn.e3mall.content.service;

import com.e3mall.common.EasyUITreeNode;

import java.util.List;

/**
 * @author haohan
 * 03/07/2019 - 10:41 下午
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCatList(long parentId);

}
