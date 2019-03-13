package cn.e3mall.content.service.impl;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author haohan
 * 03/08/2019 - 01:48 下午
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public E3Result addContent(TbContent content) {
        //补全内容属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //执行新增操作
        contentMapper.insert(content);
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {
        //查询缓存中是否有数据
        //如果有数据，则直接返回结果
        try {
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            //若json不为空则返回
            if (StringUtils.isNoneBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //没有则查询数据库
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        //设置查询条件
        Criteria criteria1 = criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> contents = contentMapper.selectByExample(contentExample);
        //将查询结果保存到缓存
        try {
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(contents));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
}
