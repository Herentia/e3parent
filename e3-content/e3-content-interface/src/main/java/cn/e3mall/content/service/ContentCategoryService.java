package cn.e3mall.content.service;

import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author haohan
 * 03/07/2019 - 10:41 下午
 */
public interface ContentCategoryService {

    /**
     * 查询所有树节点
     *
     * @param parentId 父节点ID
     * @return
     */
    List<EasyUITreeNode> getContentCatList(long parentId);

    /**
     * 增加子节点
     *
     * @param parentId 子节点ID
     * @param name     子节点名称
     * @return
     */
    E3Result addContentCategory(long parentId, String name);

}
