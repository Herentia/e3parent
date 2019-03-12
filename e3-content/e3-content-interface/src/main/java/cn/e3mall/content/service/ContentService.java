package cn.e3mall.content.service;

import cn.e3mall.pojo.TbContent;
import com.e3mall.common.pojo.E3Result;

import java.util.List;

/**
 * 内容管理Service
 * @author haohan
 * 03/08/2019 - 01:46 下午
 */
public interface ContentService {

    /**
     * 新增内容
     * @param content 内容pojo
     * @return
     */
    E3Result addContent(TbContent content);

    /**
     * 根据类目录ID查询分类内容信息
     * @param cid 类目录id
     * @return
     */
    List<TbContent> getContentListByCid(long cid);

}
