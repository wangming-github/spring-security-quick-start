package com.maizi.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义响应对象，用于封装 API 响应的数据。
 * <p>
 * 该类继承自 HashMap，提供了构建标准化响应的便捷方法，包括成功响应和错误响应。
 * </p>
 *
 * @author maizi
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 默认构造函数，初始化响应对象，设置默认的状态码和消息。
     */
    public R() {
        put("code", 0);
        put("msg", "success");
    }

    /**
     * 创建一个错误响应对象，默认状态码为 500（内部服务器错误），消息为“未知异常，请联系管理员”。
     *
     * @return 错误响应对象
     */
    public static R error() {
        return error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    /**
     * 创建一个错误响应对象，状态码为 500（内部服务器错误），消息为指定的 msg。
     *
     * @param msg 错误消息
     * @return 错误响应对象
     */
    public static R error(String msg) {
        return error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
    }

    /**
     * 创建一个错误响应对象，状态码和消息由参数指定。
     *
     * @param code 状态码
     * @param msg  错误消息
     * @return 错误响应对象
     */
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 创建一个成功响应对象，消息由参数指定。
     *
     * @param msg 成功消息
     * @return 成功响应对象
     */
    public static R ok(String msg) {
        R r = new R();
        r.put("code", 0);
        r.put("msg", msg);
        return r;
    }

    /**
     * 创建一个成功响应对象，包含指定的额外数据。
     *
     * @param map 额外数据
     * @return 成功响应对象
     */
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     * 创建一个成功响应对象，使用默认的状态码和消息。
     *
     * @return 成功响应对象
     */
    public static R ok() {
        return new R();
    }

    /**
     * 重写 put 方法，以支持链式调用。
     *
     * @param key   键
     * @param value 值
     * @return 当前响应对象
     */
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 获取响应中的状态码。
     *
     * @return 状态码
     */
    public Integer getCode() {
        return (Integer) this.get("code");
    }
}
