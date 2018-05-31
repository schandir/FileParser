package com.nathan.fileparser.controller.hook;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nathan.fileparser.exception.ResultException;
import com.nathan.fileparser.request.ServiceContext;
import com.nathan.fileparser.result.ErrorResult;
import com.nathan.fileparser.result.ModelErrorResult;
import com.nathan.fileparser.result.ResultCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = ResultException.class)
    public ErrorResult handleException(HttpServletResponse response, ResultException exception) {
        log.error("ResultException Caught: {}", ExceptionUtils.getStackTrace(exception));
        setHttpStatusCode(response, exception.getResult().getResultCode());
        return buildModelErrorResult(exception.getResult(), exception);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public ErrorResult handleException(Exception exception) {
        return getErrorResult(ResultCode.GENERAL_ERROR, exception);
    }

    private HttpServletResponse setHttpStatusCode(HttpServletResponse response, ResultCode resultCode) {
        response.setStatus(resultCode.getHttpStatusCode());
        return response;
    }

    private ModelErrorResult buildModelErrorResult(ErrorResult errorResult, ResultException resultException) {
        ModelErrorResult modelErrorResult = new ModelErrorResult();
        modelErrorResult.setResultString(resultException.getMessage());
        modelErrorResult.setResultCode(resultException.getResult().getResultCode());
        modelErrorResult.setResultSubCode(resultException.getResult().getResultSubCode());
        return addServiceContextIdsToModel(modelErrorResult);
    }

    private ErrorResult getErrorResult(ResultCode resultCode, Exception exception) {
        log.error(exception.getClass().getName() + " Caught: {}", ExceptionUtils.getStackTrace(exception));
        return new ModelErrorResult(resultCode, exception.getMessage());
    }

    private ModelErrorResult addServiceContextIdsToModel(ModelErrorResult errorResult) {
        if (errorResult == null) {
            errorResult = new ModelErrorResult();
        }
        ServiceContext serviceContext = ServiceContext.getServiceContext();
        errorResult.setServiceTransactionId(serviceContext.getServiceTransactionId());
        errorResult.setClientRequestId(serviceContext.getClientRequestId());

        return errorResult;
    }
}
