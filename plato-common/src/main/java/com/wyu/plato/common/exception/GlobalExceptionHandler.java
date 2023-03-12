package com.wyu.plato.common.exception;

import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author novo
 * @date 2023-02-21 13:52
 */
@ControllerAdvice
//@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理未知异常 都是无意义的 不需要提示message 返回模糊信息或记录日志
     *
     * @param request
     * @param e
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Resp exceptionHandler(HttpServletRequest request, Exception e) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        log.error("[系统异常] url:[{}]", requestUrl, e);
        return Resp.buildResult(BizCodeEnum.SERVER_ERROR);
    }

    /**
     * 处理已知异常
     * 这里要灵活地处理响应的http状态码 不能写死500 而是返回e里的httpStatus
     * 所以不用@ResponseBody注解 自己设置返回的响应体，所以要设置比较多的类型
     *
     * @param request
     * @param e
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Resp> httpExceptionHandler(HttpServletRequest request, BizException e) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        Resp unifyResponse = new Resp(e.getCode(), null, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        log.error("[业务异常] url:[{}],msg:[{}]", requestUrl, e.getMessage());
        return new ResponseEntity<>(unifyResponse, headers, httpStatus);
    }


    /**
     * @param request
     * @param e
     * @return
     * @RequestBody注解、Java bean中参数错误产生的异常、表单(Content-Type: application/json、Content-Type: application/xml)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // 参数错误 固定返回400
    public Resp methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMsg = this.formatAllErrorsMessages(errors);
        log.error("[参数异常] url:[{}],msg:[{}]", requestUrl, errorMsg);
        return Resp.error(errorMsg);
    }

    /**
     * 非Java bean(如路径参数等)参数错误产生的异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // 参数错误 固定返回400
    public Resp constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException e) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();

        // ConstraintViolationException自带的getMessage()也是可以用的，如果对错误信息没有严格的格式要求可以不用通过这种循环来自定义拼接
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        log.error("[参数异常] url:[{}]", requestUrl, e);
        return Resp.error(message);
    }

    /**
     * Java bean 、表单(Content-Type: multipart/form-data)参数错误产生的异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // 参数错误 固定返回400
    public Resp bindExceptionExceptionHandler(HttpServletRequest request, BindException e) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMsg = this.formatAllErrorsMessages(errors);
        log.error("[参数异常] url:[{}]", requestUrl, e);
        return Resp.error(errorMsg);
    }


    /**
     * 拼接所有参数错误信息
     *
     * @param errors
     * @return
     */
    private String formatAllErrorsMessages(List<ObjectError> errors) {
        String message = errors.stream()
                .map(objectError -> {
                    if (objectError instanceof FieldError) {
                        FieldError fieldError = (FieldError) objectError;
                        return fieldError.getField() + fieldError.getDefaultMessage();
                    } else {
                        return objectError.getDefaultMessage();
                    }
                })
                .collect(Collectors.joining(";"));
        return message;
    }
}
