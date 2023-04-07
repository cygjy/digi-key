package com.jd.resource.service.impl;

import java.util.List;
import com.jd.common.utils.DateUtils;
import com.jd.resource.domain.TPriceRule;
import com.jd.resource.mapper.TPriceRuleMapper;
import com.jd.resource.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品价格规则Service业务层处理
 * 
 * @author chenyang
 * @date 2023-03-23
 */
@Service
public class PriceRuleServiceImpl implements PriceRuleService
{
    @Autowired
    private TPriceRuleMapper tPriceRuleMapper;

}
