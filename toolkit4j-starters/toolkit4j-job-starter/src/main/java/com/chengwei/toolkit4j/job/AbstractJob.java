package com.chengwei.toolkit4j.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xxl.job.core.context.XxlJobHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务基类，提供一些模板方法。
 *
 * @author chengwei
 * @since 2021/12/10
 */
public abstract class AbstractJob {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 任务描述
     *
     * @return 任务描述
     */
    public abstract String jobDescription();

    /**
     * 日志记录任务执行过程
     *
     * @param funcDesc    函数描述
     * @param jobFunction 数据处理函数
     */
    protected void loggingJobProcess(String funcDesc, Runnable jobFunction) {
        log.info("{}-{}-执行开始", jobDescription(), funcDesc);
        DateTime beginTime = DateUtil.date();
        try {
            jobFunction.run();
        } catch (Exception e) {
            log.error("{}-{}-执行失败", jobDescription(), funcDesc, e);
            throw new IllegalStateException(e);
        } finally {
            DateTime endTime = DateUtil.date();
            long spendTime = DateUtil.between(beginTime, endTime, DateUnit.MINUTE);
            log.info("{}-{}-执行完成，耗费时间：{}分钟", jobDescription(), funcDesc, spendTime);
        }
    }

    /**
     * 解析任务参数，将任务参数json反序列化为指定对象，json为空时默认new一个对象。
     *
     * @param clazz 反序列化的参数类
     * @param <T>   反序列化的参数类型
     * @return 将任务参数解析后的对象
     */
    @SuppressWarnings("all")
    protected <T> T resolveJobParam(Class<T> clazz) {
        T paramInstance;

        String jobParam = XxlJobHelper.getJobParam();
        if (StrUtil.isEmpty(jobParam)) {
            paramInstance = ReflectUtil.newInstanceIfPossible(clazz);
        } else {
            paramInstance = JSONUtil.toBean(jobParam, clazz);
        }

        Assert.notNull(paramInstance, "解析任务参数失败，class：{}，参数：{}", clazz.getName(), jobParam);
        return paramInstance;
    }
}