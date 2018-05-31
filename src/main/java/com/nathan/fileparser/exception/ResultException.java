package com.nathan.fileparser.exception;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.nathan.fileparser.result.ErrorResult;
import com.nathan.fileparser.result.ResultCode;

public class ResultException extends RuntimeException {
    private static final long serialVersionUID = 4866426386541623427L;
    private static final String NEWLINE = System.getProperty("line.separator").toString();
    private static final String INDENT = NEWLINE + "\t";
    private static final String RESULT_CODE_PREFIX = "resultCode.";
    private final ErrorResult result;

    public ResultException(ResultException e) {
        super(e);
        this.result = e.result;
    }

    public ResultException(ResultCode resultCode) {
        super();
        this.result = new ErrorResult(resultCode);
    }

    public ResultException(ResultCode resultCode, Exception e) {
        super(e);
        this.result = new ErrorResult(resultCode, "", e);
    }

    public ResultException(ResultCode resultCode, String resultString, Exception e) {
        super(e);
        this.result = new ErrorResult(resultCode, resultString, e);
    }

    public ResultException(ResultCode resultCode, String resultString, Serializable... data) {
        super();
        this.result = new ErrorResult(resultCode, resultString, data);
    }

    public ResultException(ResultCode resultCode, Serializable[] data) {
        super();
        this.result = new ErrorResult(resultCode, data);
    }

    public ErrorResult getResult() {
        return result;
    }

    @Override
    public String getMessage() {
        List<Serializable> resultData = result.getData();
        StringBuilder builder = new StringBuilder(result.getResultCode().toString());
        if (!StringUtils.isBlank(result.getResultSubCode())) {
            builder.append('.').append(result.getResultSubCode());
        }
        if (!StringUtils.isBlank(super.getMessage())) {
            builder.append(": ").append(super.getMessage());
        }
        if (!StringUtils.isBlank(result.getResultString())) {
            builder.append(": ").append(result.getResultString());
        }
        if (resultData == null) {
            return builder.toString();
        }

        boolean once = false;
        for (Serializable item : resultData) {
            String str = item.toString();
            if (str.contains("Ljava.lang.Object")) {
                continue;
            } else if (!once) {
                builder.append(NEWLINE).append("Data:");
                once = true;
            }
            builder.append(INDENT).append(str);
        }
        return builder.toString();
    }

    public String getMessageKey() {
        if (null == getResult() || null == getResult().getResultCode()) {
            return RESULT_CODE_PREFIX + "GENERAL_ERROR";
        }
        return RESULT_CODE_PREFIX + (StringUtils.isBlank(getResult().getResultSubCode()) ? getResult().getResultCode().toString() : getResult().getResultCode().toString() + '.' + getResult().getResultSubCode());
    }

    public String getErrorString(ReloadableResourceBundleMessageSource messageSource, Locale locale) {
        String messageKey = getMessageKey();

        String errorString = messageSource.getMessage(messageKey, ArrayUtils.EMPTY_STRING_ARRAY, locale);

        return errorString;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("resultCode", result.getResultCode()).append("resultSubCode", result.getResultSubCode()).toString();
    }

    public ResultException stackTrace(Throwable ex) {
        getResult().setStackTrace(ExceptionUtils.getStackTrace(ex));
        return this;
    }

    public ResultException subcode(String subcode) {
        getResult().setResultSubCode(subcode);
        return this;
    }

    public ResultException subcode(Enum<?> subcode) {
        getResult().setResultSubCode(String.valueOf(subcode));
        return this;
    }
}