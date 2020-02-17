package cn.forest.gateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.forest.common.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import cn.forest.commom.jwt.TokenAuthenticationService;
import cn.forest.common.util.ResultMessage;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccessFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getMethod().equals("OPTIONS")) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String pathName = request.getServletPath();
        String[] filter = {"/login",
                "/file/upload",
                "/supplier/register",
                "/supplier/saveStepOneTwo",
                "/supplier/getDictionaryData",
                "/supplier/getEnterType",
                "/supplier/getCompany",
                "/ueditor/upload",
                "/ueditor/listimage",
                "/supplier/getUserInfo",
                "/camilo/downloadTemplate",
                "/camilo/exportExcel"
        };
        if (isFilter(pathName, filter)) {
            String authorization = request.getHeader(Constant.HEADER_TOKEN_STRING);
            if (StringUtils.isEmpty(authorization)) {
                ctx.getResponse().setContentType("text/html;charset=UTF-8");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(ResultMessage.ERROR_CODE_503);
                ctx.setResponseBody(ResultMessage.RESULT_TOKEN_NULL);
            } else {
                try {
                    boolean authenticationUser = TokenAuthenticationService
                            .getAuthenticationUser(request);
                    if (!authenticationUser) {
                        ctx.setSendZuulResponse(false);
                        ctx.getResponse().setContentType(
                                "text/html;charset=UTF-8");
                        ctx.setResponseStatusCode(ResultMessage.ERROR_CODE_503);
                        ctx.setResponseBody(ResultMessage.RESULT_TOKEN);
                    }
                } catch (Exception e) {
                    ctx.setSendZuulResponse(false);
                    ctx.getResponse().setContentType(
                            "text/html;charset=UTF-8");
                    ctx.setResponseStatusCode(ResultMessage.ERROR_CODE_503);
                    ctx.setResponseBody(ResultMessage.RESULT_TOKEN);

                }


            }
        }
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }


    boolean isFilter(String pathName, String ... strs){
        boolean flag = true;
        for (String str : strs){
            if(pathName.indexOf(str) != -1){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
