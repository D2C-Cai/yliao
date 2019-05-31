package com.d2c.store.common.api.exception;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cai
 */
@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

    @Autowired
    public HttpServletRequest request;

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public R handle(RuntimeException e) {
        if (e instanceof ApiException) {
            log.error(e.getMessage());
            ApiException ex = (ApiException) e;
            if (ex.getErrorCode() == null) {
                return Response.failed(ResultCode.FAILED, e.getMessage());
            }
            return Response.failed(ex.getErrorCode());
        } else if (e instanceof MybatisPlusException) {
            MybatisPlusException ex = (MybatisPlusException) e;
            return Response.failed(ResultCode.SERVER_EXCEPTION, e.getMessage());
        }
        e.printStackTrace();
        return Response.failed(ResultCode.SERVER_EXCEPTION, e.getMessage());
    }

}
