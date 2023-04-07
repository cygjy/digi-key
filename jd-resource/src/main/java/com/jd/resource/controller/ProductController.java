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
     * 导入产品
     * @param files
     * @return
     */
    @PostMapping(value = "/leading")
    public AjaxResult leadingProduct(@RequestBody List<String> files){
      return productService.leadingProduct(files);
    }

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
     * 绑定 产品和价格规则
     * @param
     * @return
     */
    @PostMapping(value = "/leading/binding/rule")
    public AjaxResult leadingBindingRule(){
        return productService.leadingBindingRule();
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
}
