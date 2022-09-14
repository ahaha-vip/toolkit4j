package com.chengwei.toolkit4j.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * 分页参数
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Getter
@Setter
@Schema(description = "分页参数")
public class PageRequest {

    @Min(value = 1, message = "页码必须为正数")
    @Schema(description = "页码", defaultValue = "1")
    private int pageNum = 1;

    @Min(value = 1, message = "每页大小必须为正数")
    @Schema(description = "每页大小", defaultValue = "10")
    private int pageSize = 10;
}