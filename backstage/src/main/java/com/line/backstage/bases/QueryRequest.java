package com.line.backstage.bases;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author pc
 */
@Data
@ApiModel("base类")
public class QueryRequest implements Serializable {

    @Transient
    private static final long serialVersionUID = -32472298260769753L;

    @Transient
    @ApiModelProperty("页码数")
    private int pageSize = 10;

    @Transient
    @ApiModelProperty("页码号")
    private int pageNum = 1;
}