package cn.forest.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import cn.forest.common.exception.util.ApiResponse;
import cn.forest.common.exception.util.ApiResponseCode;
import cn.forest.common.exception.util.BusinessException;


@ControllerAdvice
@Order
public class ApiExceptionHandler {
  
  private  final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
  @ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public Object handleNoHandlerFoundException(Throwable e) {
	  logger.error("API Url invalid", e);
		return ApiResponse.from(ApiResponseCode.API_NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public Object handleRequestMethodNotSupportedException(Throwable e) {
	  logger.warn("API request method not supported", e);
		return ApiResponse.from(ApiResponseCode.API_REQUEST_METHOD_NOT_SUPPORTED);
	}

	@ExceptionHandler({ BindException.class, MethodArgumentNotValidException.class,MissingServletRequestParameterException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Object handleParameterValidateException(Throwable e) {
	  logger.warn("parameter validate failed", e);
		return ApiResponse.from(ApiResponseCode.API_PARAMS_INVALID);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public Object handleException(BusinessException e) {
	  logger.info("business exception {}", e.toString());
		return ApiResponse.from(e);
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Object handleException(Throwable e) {
	  logger.error("API request exception", e);
		return ApiResponse.from(ApiResponseCode.SYS_ERROR);
	}
}