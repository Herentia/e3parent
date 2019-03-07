package cn.e3mall.content.service.impl;

import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import com.e3mall.common.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haohan
 * 03/07/2019 - 10:44 下午
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
        Criteria criteria = contentCategoryExample.createCriteria();
        //设置parentId
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(contentCategoryExample);
        //将查询结果转换成EasyUITreeNode
        List<EasyUITreeNode> list = new ArrayList<>();
        for(TbContentCategory contentCategory : contentCategoryList) {
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(contentCategory.getId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getIsParent() ? "closed" : "open");
        }
        return list;
    }
}
