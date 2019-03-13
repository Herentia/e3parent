package cn.e3mall.search.service;

import com.e3mall.common.pojo.SearchResult;

/**
 * @author haohan
 * 03/13/2019 - 04:46 下午
 */
public interface SearchService {

    /**
     * 通过搜索关键字查询索引库结果
     * @param keyword
     * @param page
     * @param rows
     * @return
     */
    SearchResult search(String keyword, int page, int rows);

}
