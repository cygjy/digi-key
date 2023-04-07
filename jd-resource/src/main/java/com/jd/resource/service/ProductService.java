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
     * 导入产品
     * @param files
     * @return
     */
    AjaxResult leadingProduct(List<String> files);

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
     * 绑定 产品和价格规则
     * @return
     */
    AjaxResult leadingBindingRule();

}
