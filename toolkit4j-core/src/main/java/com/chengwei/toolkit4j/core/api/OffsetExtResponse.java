package com.chengwei.toolkit4j.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

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

    @Schema(description = "总记录数。第一次查询时返回，后续使用偏移量翻页查询时不再返回。")
    private Long total;

    public OffsetExtResponse<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    /**
     * 设置总记录数，符合条件才回调计算总记录数。
     *
     * @param condition 条件
     * @param supplier  查询总记录数的回调函数
     * @return this
     */
    public OffsetExtResponse<T> setTotal(boolean condition, Supplier<Long> supplier) {
        if (condition) {
            this.total = supplier.get();
        }
        return this;
    }

    @Override
    public OffsetExtResponse<T> setList(List<T> list) {
        return (OffsetExtResponse<T>) super.setList(list);
    }

    @Override
    public OffsetExtResponse<T> setOffsetValue(boolean condition, Supplier<String> supplier) {
        return (OffsetExtResponse<T>) super.setOffsetValue(condition, supplier);
    }

    @Override
    public OffsetExtResponse<T> setOffsetTime(boolean condition, Supplier<Date> supplier) {
        return (OffsetExtResponse<T>) super.setOffsetTime(condition, supplier);
    }

    @Override
    public OffsetExtResponse<T> setOffsetDate(boolean condition, Supplier<Date> supplier) {
        return (OffsetExtResponse<T>) super.setOffsetDate(condition, supplier);
    }

    @Override
    public OffsetExtResponse<T> setOffset(boolean condition, Runnable function) {
        return (OffsetExtResponse<T>) super.setOffset(condition, function);
    }
}