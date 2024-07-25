package com.maizi.common.exception;

import lombok.Getter;

/**
 * 自定义异常类，用于封装应用程序中发生的运行时异常。
 * 该类继承自 {@link RuntimeException}，并包含自定义的错误信息和错误码。
 * 提供多个构造方法用于创建带有不同信息和异常的对象。
 *
 * @author maizi
 */
@Getter
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;  // 序列化ID，用于对象序列化和反序列化

    /**
     * -- GETTER --
     * 获取错误信息。
     *
     * @return 错误信息
     */
    private String msg;  // 错误信息
    /**
     * -- GETTER --
     * 获取错误码。
     *
     * @return 错误码
     */
    private int code = 500;  // 错误码，默认值为500

    /**
     * 使用指定的错误信息创建一个新的 {@code RRException} 实例。
     *
     * @param msg 错误信息
     */
    public RRException(String msg) {
        super(msg);  // 调用父类的构造方法
        this.msg = msg;  // 设置错误信息
    }

    /**
     * 使用指定的错误信息和根本原因创建一个新的 {@code RRException} 实例。
     *
     * @param msg 错误信息
     * @param e   根本原因
     */
    public RRException(String msg, Throwable e) {
        super(msg, e);  // 调用父类的构造方法，传递错误信息和根本原因
        this.msg = msg;  // 设置错误信息
    }

    /**
     * 使用指定的错误信息和错误码创建一个新的 {@code RRException} 实例。
     *
     * @param msg  错误信息
     * @param code 错误码
     */
    public RRException(String msg, int code) {
        super(msg);  // 调用父类的构造方法
        this.msg = msg;  // 设置错误信息
        this.code = code;  // 设置错误码
    }

    /**
     * 使用指定的错误信息、错误码和根本原因创建一个新的 {@code RRException} 实例。
     *
     * @param msg  错误信息
     * @param code 错误码
     * @param e    根本原因
     */
    public RRException(String msg, int code, Throwable e) {
        super(msg, e);  // 调用父类的构造方法，传递错误信息和根本原因
        this.msg = msg;  // 设置错误信息
        this.code = code;  // 设置错误码
    }

    /**
     * 设置错误信息。
     *
     * @param msg 错误信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 设置错误码。
     *
     * @param code 错误码
     */
    public void setCode(int code) {
        this.code = code;
    }
}
