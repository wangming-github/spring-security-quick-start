package com.maizi.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * FeignInterceptor 是一个用于拦截 Feign 请求并添加当前 HTTP 请求头信息的拦截器。
 * <p>
 * 该拦截器通过实现 RequestInterceptor 接口，动态获取当前 HTTP 请求的所有头信息，并将其添加到 Feign 请求中。
 * 这在需要传递认证信息（例如 JWT 令牌）或其他动态头信息时非常有用。
 * </p>
 * <p>
 * 主要功能：
 * - 从当前请求上下文中获取 HttpServletRequest 对象。
 * - 将 HttpServletRequest 对象中的所有头信息传递给 Feign 请求模板。
 * </p>
 * <p>
 * 注意事项：
 * - 如果当前请求上下文不可用，将记录一条警告日志。
 * - 如果当前请求没有头信息，将记录一条警告日志。
 * </p>
 */
@Slf4j
@Component
public class FeignInterceptor implements RequestInterceptor {

    private static final String MODULE_TYPE = "AUTHORIZATION_MODULE";

    @PostConstruct
    public void init() {
        log.info("【公共模块】- 配置FeignInterceptor:一个用于拦截 Feign 请求并添加当前 HTTP 请求头信息的拦截器");
    }


    /**
     * 拦截 Feign 请求并将当前请求的头信息添加到请求模板中。
     *
     * @param template Feign 请求模板
     */
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("【公共模块】- ====> - 无法获取当前请求的上下文属性");
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        transferHeaders(request, template);
    }

    /**
     * 将请求的header信息传递到Feign请求模板
     *
     * @param request  当前HTTP请求对象
     * @param template Feign请求模板
     */
    private void transferHeaders(HttpServletRequest request, RequestTemplate template) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            log.warn("【公共模块】- ====> 当前请求没有header信息");
            return;
        }

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.debug("【公共模块】- ====> 将header向下传递: {} = {}", headerName, headerValue);
            template.header(headerName, headerValue);
        }
    }
}
