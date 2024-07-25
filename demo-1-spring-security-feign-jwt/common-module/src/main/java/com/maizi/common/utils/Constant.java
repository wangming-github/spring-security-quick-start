package com.maizi.common.utils;

/**
 * 常量类，用于定义一些全局通用的常量。
 * <p>
 * 该类包含了一些常用的常量，例如分页参数、排序字段及排序方式等，用于简化代码中的硬编码，提供统一的常量引用。
 * </p>
 * <p>
 * 常量的命名遵循大写字母加下划线的命名规则，以便于在代码中识别和使用。
 * </p>
 */
public class Constant {
    /**
     * 当前页码的常量
     */
    public static final String PAGE = "page";

    /**
     * 每页显示记录数的常量
     */
    public static final String LIMIT = "limit";

    /**
     * 排序字段的常量
     */
    public static final String ORDER_FIELD = "sidx";

    /**
     * 排序方式的常量
     */
    public static final String ORDER = "order";

    /**
     * 升序排序的常量
     */
    public static final String ASC = "asc";
}
