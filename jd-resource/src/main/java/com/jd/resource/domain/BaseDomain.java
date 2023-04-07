package com.jd.resource.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jd.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenyang
 */
@Data
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


    /**
     * 删除标志 1未删除 0删除
     */
    @Excel(name = "删除标志")
    private Long isDelete;
}
