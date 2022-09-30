package com.chengwei.toolkit4j.core.api;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

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

    @Schema(description = "是否还有下一页。任意一个偏移参数不为空，则认为还有下一页。")
    private boolean hasMore;

    @Schema(description = "是否还有下一页")
    public boolean isHasMore() {
        return ObjectUtil.isNotNull(offsetValue)
                || ObjectUtil.isNotNull(offsetTime)
                || ObjectUtil.isNotNull(offsetDate);
    }

    public OffsetResponse<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    /**
     * 设置偏移值
     *
     * @param condition 判断条件，如果还有下一页才设置偏移参数
     * @param supplier  执行回调函数设置偏移参数
     * @return this
     */
    public OffsetResponse<T> setOffsetValue(boolean condition, Supplier<String> supplier) {
        if (condition) {
            this.offsetValue = supplier.get();
        }
        return this;
    }

    /**
     * 设置偏移时间
     *
     * @param condition 判断条件，如果还有下一页才设置偏移参数
     * @param supplier  执行回调函数设置偏移参数
     * @return this
     */
    public OffsetResponse<T> setOffsetTime(boolean condition, Supplier<Date> supplier) {
        if (condition) {
            this.offsetTime = supplier.get();
        }
        return this;
    }

    /**
     * 设置偏移时间
     *
     * @param condition 判断条件，如果还有下一页才设置偏移参数
     * @param supplier  执行回调函数设置偏移参数
     * @return this
     */
    public OffsetResponse<T> setOffsetDate(boolean condition, Supplier<Date> supplier) {
        if (condition) {
            this.offsetDate = supplier.get();
        }
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