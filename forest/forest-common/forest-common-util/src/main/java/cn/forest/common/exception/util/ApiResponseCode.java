package cn.forest.common.exception.util;


public enum ApiResponseCode {
    
    SUCCESS (0, ""), 
    BUSINESS_EXCEPTION(2000, ""),
    SYS_ERROR (9000, "系统异常，请联系客服人员"),
    API_NOT_FOUND(9001, "API不存在"),
    API_PARAMS_INVALID(9002, "API请求参数非法。"),
    API_REQUEST_METHOD_NOT_SUPPORTED(9003, "API请求方法不支持。");

    private int code;
    
    private String message;
    
    private ApiResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
    
}
