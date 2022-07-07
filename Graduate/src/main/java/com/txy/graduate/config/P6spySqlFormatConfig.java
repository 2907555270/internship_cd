package com.txy.graduate.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.txy.graduate.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * 自定义 p6spy sql输出格式
 *
 * @author MrBird
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return !StringUtils.isEmpty(sql) ? " " + DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN)
                + " | 执行耗时 " + elapsed + " ms "+ StringUtils.LF +
                " SQL 语句：" + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" +
                StringUtils.LF : StringUtils.EMPTY;
    }
}
