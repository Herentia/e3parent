package com.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author haohan
 * 03/13/2019 - 03:33 下午
 * 封装索引库查询数据信息
 */
public class SearchResult implements Serializable {

    private Long recordCount;
    private Integer totalPage;
    private List<SearchItem> itemList;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
