package com.jd.resource.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * 分页工具类
 *
 * @author chenyang
 */
public class PageUtil {

    /**
     * 对完整 List 进行分页
     *
     * @param list 待分页 List 集合
     * @param pageNum 当前页码
     * @param pageSize 页大小
     * @return PageInfo 分页对象
     */
    public static PageInfo startPage4FullList(List list, int pageNum, int pageSize) {
        int total = list.size();
        int startIndex = Math.min((pageNum - 1) * pageSize, total);
        int endIndex = Math.min(startIndex + pageSize, total);
        List pageList = list.subList(startIndex, endIndex);
        PageInfo pageInfo = startPage(pageList, total, pageNum, pageSize);
        return pageInfo;
    }
    /**
     * 将 List 数据拼装分页信息
     * @param pageList 当前页 List 数据
     * param total 总页数
     * @param pageNum 当前页码
     * @param pageSize 页大小
     * @return PageInfo 分页对象
     */
    public static PageInfo startPage(List pageList, int total, int pageNum, int pageSize) {
        Page page = new Page(pageNum, pageSize);
        page.setTotal(total);
        page.addAll(pageList);
        PageInfo pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
}
