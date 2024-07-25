package com.maizi.common.o.constants;

/**
 * 定义系统模块的枚举类型。
 */
public enum ModuleType {

    AUTHORIZATION_MODULE("【authorization-module】"),
    COMMON_MODULE("【common-module】"),
    SERVICE_MODULE("【service-module】");

    private final String moduleName;

    // 构造函数
    ModuleType(String moduleName) {
        this.moduleName = moduleName;
    }

    // 获取模块名称
    public String getModuleName() {
        return moduleName;
    }
}