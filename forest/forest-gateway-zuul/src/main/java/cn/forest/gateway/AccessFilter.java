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
    String pathName = request.getServletPath ();
    if(pathName.indexOf("/login")==-1 && pathName.indexOf("/file/upload")==-1 && pathName.indexOf("/supplier/register")==-1) {
      String authorization = request.getHeader(Constant.HEADER_TOKEN_STRING);
      if(StringUtils.isEmpty(authorization)) {
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(ResultMessage.ERROR_CODE_503);
        ctx.setResponseBody(ResultMessage.RESULT_TOKEN_NULL);
      }else {
        try {
          boolean authenticationUser = TokenAuthenticationService
              .getAuthenticationUser(request);
          if(!authenticationUser) {
            ctx.setSendZuulResponse(false);
            ctx.getResponse().setContentType(
                "text/html;charset=UTF-8");
            ctx.setResponseStatusCode(ResultMessage.ERROR_CODE_503);
            ctx.setResponseBody(ResultMessage.RESULT_TOKEN);
          }
        }catch (Exception e) {
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

}
