package com.chengwei.toolkit4j.core.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 偏移量参数，可以解决深度分页的问题，但不能提供总条数。
 *
 * @author chengwei
 * @since 2022/3/22
 */
@Getter
@Setter
@Schema(description = "偏移量参数")
public class OffsetRequest {

    @Schema(description = "偏移值")
    private String offsetValue;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "偏移时间，格式：yyyy-MM-dd HH:mm:ss")
    private Date offsetTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(description = "偏移日期，格式：yyyy-MM-dd")
    private Date offsetDate;

    @Min(value = 1, message = "每页大小必须为正数")
    @Max(value = 50, message = "每页大小不能超过50条")
    @Schema(description = "每页大小", defaultValue = "10")
    private int pageSize = 10;
}