package com.jd.resource.mapper;

import com.jd.resource.domain.TProduct;

import java.util.List;

/**
 * 产品Mapper接口
 * 
 * @author Quark
 * @date 2023-03-23
 */
public interface TProductMapper 
{
    /**
     * 查询产品
     * 
     * @param id 产品主键
     * @return 产品
     */
    public TProduct selectTProductById(Long id);

    /**
     * 查询产品列表
     * 
     * @param tProduct 产品
     * @return 产品集合
     */
    public List<TProduct> selectTProductList(TProduct tProduct);

    /**
     * 新增产品
     * 
     * @param tProduct 产品
     * @return 结果
     */
    public int insertTProduct(TProduct tProduct);

    /**
     * 修改产品
     * 
     * @param tProduct 产品
     * @return 结果
     */
    public int updateTProduct(TProduct tProduct);

    /**
     * 删除产品
     * 
     * @param id 产品主键
     * @return 结果
     */
    public int deleteTProductById(Long id);

    /**
     * 批量删除产品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTProductByIds(Long[] ids);

    /**
     *
     * @param product
     * @return
     */
    List<TProduct> selectProductList(TProduct product);

    void updateProductBatch(List<Object> list);
}
