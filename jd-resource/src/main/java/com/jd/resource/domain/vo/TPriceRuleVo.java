package com.jd.resource.domain.vo;

import com.jd.common.annotation.Excel;
import com.jd.resource.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品价格规则对象 t_price_rule
 * 
 * @author chenyang
 * @date 2023-03-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TPriceRuleVo extends BaseDomain
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 数量1 */
    @Excel(name = "数量1")
    private String qty1;

    /** 单价1 */
    @Excel(name = "单价1")
    private String unitprice1;

    /** 额外价格1 */
    @Excel(name = "额外价格1")
    private String extprice1;

    /** 数量2 */
    @Excel(name = "数量2")
    private String qty2;

    /** 单价2 */
    @Excel(name = "单价2")
    private String unitprice2;

    /** 额外价格2 */
    @Excel(name = "额外价格2")
    private String extprice2;

    /** 数量3 */
    @Excel(name = "数量3")
    private String qty3;

    /** 单价3 */
    @Excel(name = "单价3")
    private String unitprice3;

    /** 额外价格3 */
    @Excel(name = "额外价格3")
    private String extprice3;

    /** 数量4 */
    @Excel(name = "数量4")
    private String qty4;

    /** 单价4 */
    @Excel(name = "单价4")
    private String unitprice4;

    /** 额外价格4 */
    @Excel(name = "额外价格4")
    private String extprice4;

    /** 数量5 */
    @Excel(name = "数量5")
    private String qty5;

    /** 单价5 */
    @Excel(name = "单价5")
    private String unitprice5;

    /** 额外价格5 */
    @Excel(name = "额外价格5")
    private String extprice5;

    /** 数量6 */
    @Excel(name = "数量6")
    private String qty6;

    /** 单价6 */
    @Excel(name = "单价6")
    private String unitprice6;

    /** 额外价格6 */
    @Excel(name = "额外价格6")
    private String extprice6;

    /** 数量7 */
    @Excel(name = "数量7")
    private String qty7;

    /** 单价7 */
    @Excel(name = "单价7")
    private String unitprice7;

    /** 额外价格7 */
    @Excel(name = "额外价格7")
    private String extprice7;

    /** 产品状态 */
    @Excel(name = "产品状态")
    private String status;

    /** 是否删除 1否 */
    @Excel(name = "是否删除 1否")
    private Long isDelete;
    
}
