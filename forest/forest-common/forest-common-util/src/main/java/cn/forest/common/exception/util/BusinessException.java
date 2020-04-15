package cn.forest.common.exception.util;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -1842303282090743463L;
    
    private int code = 2000;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(BusinessErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
