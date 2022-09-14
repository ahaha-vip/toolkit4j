package com.chengwei.toolkit4j.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 分页响应数据
 *
 * @author chengwei
 * @since 2021/12/14
 */
@Getter
@Setter
@Schema(description = "分页响应数据")
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    @Schema(description = "数据列表")
    private List<T> list;

    @Schema(description = "总记录数")
    private long total;
}