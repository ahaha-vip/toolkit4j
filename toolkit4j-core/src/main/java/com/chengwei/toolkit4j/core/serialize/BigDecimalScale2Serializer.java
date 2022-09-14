package com.chengwei.toolkit4j.core.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 负责{@link BigDecimal}的序列化，默认保留2位小数。
 *
 * @author chengwei
 * @since 2022/4/14
 */
public class BigDecimalScale2Serializer extends JsonSerializer<BigDecimal> {

    /**
     * 固定保留2位小数
     */
    private static final int DEFAULT_SCALE = 2;

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeNull();
        } else {
            gen.writeString(value.setScale(DEFAULT_SCALE, RoundingMode.FLOOR).toString());
        }
    }
}