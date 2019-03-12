package com.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author haohan
 * 03/04/2019 - 02:38 下午
 */
public class EasyUIDataGridResult implements Serializable {

    private Long total;
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
