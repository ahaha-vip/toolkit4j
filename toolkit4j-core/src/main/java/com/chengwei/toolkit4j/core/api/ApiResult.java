package com.chengwei.toolkit4j.core.api;

import com.chengwei.toolkit4j.core.resolver.SuccessApiCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 响应体
 *
 * @param <DATA> 响应数据
 * @author chengwei
 * @since 2021/12/10
 */
@Getter
@Setter
@Schema(description = "接口响应体")
public class ApiResult<DATA> {

    public static final String SUCCESS_MSG = "成功";

    public static final SuccessApiCode SUCCESS_API_CODE = new SuccessApiCode();

    @Schema(description = "响应码，成功：00000，失败：其他")
    private String code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "响应数据")
    private DATA data;

    @Schema(description = "true-成功；false-失败")
    private boolean success;

    /**
     * 是否成功响应
     *
     * @return true-成功；false-失败
     */
    public boolean isSuccess() {
        return Objects.equals(SUCCESS_API_CODE.getCode(), this.getCode());
    }

    public ApiResult() {
    }

    public ApiResult(String code, String msg, DATA data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应
     *
     * @return 响应体
     */
    public static ApiResult<Void> success() {
        return ApiResult.success(null);
    }

    /**
     * 成功响应
     *
     * @param data 相应数据
     * @param <T>  响应数据类型泛型
     * @return 响应体
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(SUCCESS_API_CODE.getCode(), SUCCESS_MSG, data);
    }
}