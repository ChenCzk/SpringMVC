package com.SSM.POJO;
/*
* 分页
* */
public class PageParams {
    private int start;
    private int limit;

    @Override
    public String toString() {
        return "PageParams{" +
                "start=" + start +
                ", limit=" + limit +
                '}';
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
