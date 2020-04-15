package cn.forest.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import cn.forest.common.util.JsonUtil;


public class RequestLogInterceptor extends HandlerInterceptorAdapter {
  
    private  final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
  
  
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        logger.info("request ip is {}", request.getHeader("X-Real-IP"));
        logger.info("request url is " + request.getRequestURL().toString());
        logger.info("request method is " + request.getMethod());
        logger.info("request params is " + JsonUtil.GSON.toJson(request.getParameterMap()));
        return super.preHandle(request, response, handler);
    }
}
