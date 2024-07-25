package com.maizi.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizi.common.xss.SQLFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 查询参数工具类，用于构建分页和排序的 MyBatis-Plus {@link IPage} 对象。
 * <p>
 * 该类根据提供的查询参数生成分页对象，并且根据排序字段和排序方式对分页对象进行排序。
 * </p>
 *
 * @param <T> 分页对象的类型参数
 * @author maizi
 */
public class QueryUtils<T> {

    /**
     * 根据查询参数构建分页对象，使用默认排序字段和升序方式。
     *
     * @param params 查询参数，包括当前页码、每页记录数、排序字段和排序方式
     * @return 配置好的分页对象 {@link IPage}
     */
    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    /**
     * 根据查询参数构建分页对象，并根据提供的排序字段和排序方式进行排序。
     *
     * @param params            查询参数，包括当前页码、每页记录数、排序字段和排序方式
     * @param defaultOrderField 默认排序字段，如果没有提供排序字段，则使用此字段排序
     * @param isAsc             是否升序排序，若为 true，则升序，否则降序
     * @return 配置好的分页对象 {@link IPage}
     */
    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        // 初始化当前页码和每页记录数
        long curPage = 1;
        long limit = 10;

        // 从查询参数中获取当前页码
        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }

        // 从查询参数中获取每页记录数
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        // 创建分页对象
        Page<T> page = new Page<>(curPage, limit);

        // 将分页对象放入查询参数中
        params.put(Constant.PAGE, page);

        // 获取排序字段和排序方式
        String orderField = SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));
        String order = (String) params.get(Constant.ORDER);

        // 如果排序字段和排序方式都不为空，则进行排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        // 如果没有提供排序字段，则不进行排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        // 默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}
