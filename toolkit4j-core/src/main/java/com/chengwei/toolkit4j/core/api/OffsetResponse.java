package com.chengwei.toolkit4j.core.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 偏移量响应数据
 *
 * @author chengwei
 * @since 2022/4/13
 */
@Getter
@Setter
@Schema(description = "偏移量响应数据")
public class OffsetResponse<T> {

    @Schema(description = "数据列表")
    private List<T> list;

    @Schema(description = "查询下一页数据时的偏移值")
    private String offsetValue;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "查询下一页数据时的偏移时间，格式：yyyy-MM-dd HH:mm:ss")
    private Date offsetTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(description = "查询下一页数据时的偏移日期，格式：yyyy-MM-dd")
    private Date offsetDate;
}