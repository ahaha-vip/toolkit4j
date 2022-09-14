package com.chengwei.toolkit4j.cache.codec;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.codec.JsonJacksonCodec;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 基于jackson的序列化
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class CacheJsonJacksonCodec extends JsonJacksonCodec {

    @Override
    protected void init(ObjectMapper objectMapper) {
        super.init(objectMapper);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
    }

    @Override
    protected void initTypeInclusion(ObjectMapper mapObjectMapper) {
        // TODO 默认实现时间类型可能存在反序列化问题，因此暂时覆盖并不重写，后续再针对性优化。
    }
}