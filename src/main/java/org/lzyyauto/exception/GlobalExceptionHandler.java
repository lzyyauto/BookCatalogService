package org.lzyyauto.exception;

import org.lzyyauto.dto.ResponseVO;
import org.lzyyauto.dto.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author zingliu
 * @date 2025/5/20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseVO<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ResponseVO<String>().error("路径不存在，请检查路径是否正确: " + ex.getRequestURL());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO<String> handleAllExceptions(Exception ex) {
        //  可以记录更详细的日志 ex.printStackTrace();
        return new ResponseVO<String>().error("服务器内部错误: " + ex.getMessage());
    }
}
