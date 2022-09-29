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

    public OffsetResponse<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    /**
     * 设置偏移参数
     *
     * @param condition 判断条件，如果还有下一页才设置偏移参数
     * @param function  执行回调函数设置偏移参数
     * @return this
     */
    public OffsetResponse<T> setOffset(boolean condition, Runnable function) {
        if (condition) {
            function.run();
        }
        return this;
    }
}