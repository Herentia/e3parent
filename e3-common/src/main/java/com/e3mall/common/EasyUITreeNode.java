package com.e3mall.common;

import java.io.Serializable;

/**
 * @author haohan
 * 03/04/2019 - 04:40 下午
 */
public class EasyUITreeNode implements Serializable {

    private Long id;
    private String text;
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
