package com.maizi.common.xss;

import com.maizi.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
 * SQL 过滤工具类，用于防止 SQL 注入攻击。
 * <p>
 * 该类提供了方法来过滤 SQL 注入相关的字符和关键字，以增强应用的安全性。
 * </p>
 *
 * @author maizi
 */
public class SQLFilter {

    /**
     * SQL 注入过滤方法。
     * <p>
     * 该方法会去掉字符串中的一些特定字符，并检查字符串中是否包含常见的 SQL 注入关键字。
     * 如果发现非法字符或关键字，则抛出自定义的异常。
     * </p>
     *
     * @param str 待验证的字符串
     * @return 过滤后的字符串。如果原字符串为空，则返回 null。
     * @throws RRException 如果字符串包含非法字符或关键字，则抛出异常
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        // 去掉字符串中的特定字符
        str = StringUtils.replace(str, "'", "");    // 去掉单引号
        str = StringUtils.replace(str, "\"", "");   // 去掉双引号
        str = StringUtils.replace(str, ";", "");     // 去掉分号
        str = StringUtils.replace(str, "\\", "");    // 去掉反斜杠

        // 转换成小写，方便后续检查
        str = str.toLowerCase();

        // 定义常见的 SQL 注入关键字
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        // 检查字符串中是否包含非法关键字
        for (String keyword : keywords) {
            if (str.indexOf(keyword) != -1) {
                throw new RRException("包含非法字符");
            }
        }

        return str;
    }
}
