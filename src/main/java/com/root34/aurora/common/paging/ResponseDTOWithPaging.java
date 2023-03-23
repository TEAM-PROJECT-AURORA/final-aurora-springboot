package com.root34.aurora.common.paging;

public class ResponseDTOWithPaging {

    private Object data;
    private SelectCriteria pageInfo;

    public ResponseDTOWithPaging() {
    }

    public ResponseDTOWithPaging(Object data, SelectCriteria pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SelectCriteria getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(SelectCriteria pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return "ResponseDtoWithPaging{" +
                "data=" + data +
                ", pageInfo=" + pageInfo +
                '}';
    }
}
