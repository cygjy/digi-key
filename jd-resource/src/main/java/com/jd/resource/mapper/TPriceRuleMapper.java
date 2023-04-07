package com.jd.resource.mapper;

import com.jd.resource.domain.TPriceRule;

import java.util.List;

/**
 * 产品价格规则Mapper接口
 * 
 * @author chenyang
 * @date 2023-03-23
 */
public interface TPriceRuleMapper 
{
    /**
     * 查询产品价格规则
     * 
     * @param id 产品价格规则主键
     * @return 产品价格规则
     */
    public TPriceRule selectTPriceRuleById(Long id);

    /**
     * 查询产品价格规则列表
     * 
     * @param tPriceRule 产品价格规则
     * @return 产品价格规则集合
     */
    public List<TPriceRule> selectTPriceRuleList(TPriceRule tPriceRule);

    /**
     * 新增产品价格规则
     * 
     * @param tPriceRule 产品价格规则
     * @return 结果
     */
    public int insertTPriceRule(TPriceRule tPriceRule);

    /**
     * 修改产品价格规则
     * 
     * @param tPriceRule 产品价格规则
     * @return 结果
     */
    public int updateTPriceRule(TPriceRule tPriceRule);

    /**
     * 删除产品价格规则
     * 
     * @param id 产品价格规则主键
     * @return 结果
     */
    public int deleteTPriceRuleById(Long id);

    /**
     * 批量删除产品价格规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTPriceRuleByIds(Long[] ids);
}
