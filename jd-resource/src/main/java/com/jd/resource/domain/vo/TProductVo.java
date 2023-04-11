package com.jd.resource.domain.vo;

import com.jd.common.annotation.Excel;
import com.jd.resource.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品对象 t_product
 * 
 * @author chenyang
 * @date 2023-03-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TProductVo extends BaseDomain
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 产品id */
    @Excel(name = "产品id")
    private Long productId;

    /** 价格规则id */
    @Excel(name = "价格规则id")
    private Long priceRuleId;

    /** 制造商 */
    @Excel(name = "制造商")
    private String vendor;

    /** 描述 */
    @Excel(name = "描述")
    private String bodyHtml;

    /** 大描述 */
    @Excel(name = "大描述")
    private String bigDescription;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 独特 */
    @Excel(name = "独特")
    private String handle;

    /** 图片 */
    @Excel(name = "图片")
    private String images;

    /** 标签 */
    @Excel(name = "标签")
    private String tags;

    /** 产品类别 */
    @Excel(name = "产品类别")
    private String productType;

    /** 产品参数（json格式） */
    @Excel(name = "产品参数", readConverterExp = "j=son格式")
    private String productParameter;

    /** 制造商 */
    @Excel(name = "制造商")
    private String sku;

    /** 系列 */
    @Excel(name = "系列")
    private String series;

    /** 数据表 */
    @Excel(name = "数据表")
    private String dataSheet;

    /** 价格规则（json格式） */
    @Excel(name = "价格规则", readConverterExp = "j=son格式")
    private String priceRule;

    /** 产品状态 */
    @Excel(name = "产品状态")
    private String status;

    /** 是否删除 1否 */
    @Excel(name = "是否删除 1否")
    private Long isDelete;

}
