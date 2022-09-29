package com.chengwei.toolkit4j.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 偏移量响应数据，增加扩展字段。
 *
 * @author chengwei
 * @since 2022/9/29
 */
@Getter
@Setter
@Schema(description = "偏移量响应数据")
public class OffsetExtResponse<T> extends OffsetResponse<T> {

    @Schema(description = "总记录数")
    private long total;
}