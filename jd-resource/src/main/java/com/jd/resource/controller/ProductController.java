package com.jd.resource.controller;

import com.alibaba.fastjson.JSON;
import com.jd.common.core.domain.AjaxResult;
import com.jd.resource.domain.TPriceRule;
import com.jd.resource.domain.TProduct;
import com.jd.resource.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenyang
 */
@RequestMapping("/web/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;



    /**
     * 导入价格规则
     * @param files
     * @return
     */
    @PostMapping(value = "/leading/price/rule")
    public AjaxResult leadingPriceRule(@RequestBody List<String> files){
        return productService.leadingPriceRule(files);
    }




    /**
     * 导入到shopify产品
     * @param
     * @return
     */
    @PostMapping(value = "/leading/shopify")
    public AjaxResult leadingShopifyProduct(){
        return productService.leadingShopifyProduct();
    }


    /**
     * 创建产品系列
     * @param
     * @return
     */
    @PostMapping(value = "/create/series")
    public AjaxResult createSeries(){
        return productService.createSeries();
    }
}
