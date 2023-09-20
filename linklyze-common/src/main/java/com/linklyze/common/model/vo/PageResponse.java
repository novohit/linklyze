package com.linklyze.common.model.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 自定义Page对象
 *
 * @author novo
 * @since 2023-03-25
 */
@Data
public class PageResponse<T> {

    /**
     * 页码
     */
    private Long page;

    /**
     * 条数
     */
    private Long size;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 数据
     */
    private List<T> items;

    public PageResponse(Page<T> page) {
        this.page = page.getCurrent();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.totalPage = page.getPages();
        this.items = page.getRecords();
    }

    public PageResponse(Long page, Long size, Long total, Long totalPage, List<T> data) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPage = totalPage;
        this.items = data;
    }
}
