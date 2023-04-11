package com.jd.resource.service;

import com.jd.common.core.domain.AjaxResult;

import java.util.List;
/**
 * 产品Service接口
 * 
 * @author Quark
 * @date 2023-03-23
 */
public interface ProductService
{


    /**
     * 导入价格
     * @param files
     * @return
     */
    AjaxResult leadingPriceRule(List<String> files);


    /**
     * 导入到shopify
     * @return
     */
    AjaxResult leadingShopifyProduct();

    /**
     * 创建产品系列
     * @return
     */
    AjaxResult createSeries();

}
