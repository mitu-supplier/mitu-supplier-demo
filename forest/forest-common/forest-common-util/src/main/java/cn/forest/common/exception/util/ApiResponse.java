package cn.forest.common.exception.util;

public class ApiResponse {

	private int code = 0;

	private String message = "";

	private Object data;

	private String sign;

	public static ApiResponse from(ApiResponseCode code) {
		ApiResponse response = new ApiResponse();
		response.code = code.getCode();
		response.message = code.getMessage();
		return response;
	}

	public static ApiResponse from(Object data) {
		if (data instanceof ApiResponseCode) {
			return from((ApiResponseCode) data);
		}
		if (data instanceof BusinessException) {
			return from((BusinessException) data);
		}

		ApiResponse response = new ApiResponse();
		response.data = data;
		return response;
	}

	public static ApiResponse from(BusinessException ex) {
		ApiResponse response = new ApiResponse();
		response.code = ex.getCode();
		response.message = ex.getMessage();
		return response;
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

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  @Override
  public String toString() {
    return "ApiResponse [code=" + code + ", message=" + message + ", data=" + data + ", sign=" + sign + "]";
  }

	
}
