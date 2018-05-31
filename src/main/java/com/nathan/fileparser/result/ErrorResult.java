package com.nathan.fileparser.result;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API method result for use when an error occurs. Contains information about the error.
 */
public class ErrorResult extends ListResult<Serializable> {
    private String resultString;

    @JsonInclude(Include.NON_NULL)
    private String stackTrace;

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public ErrorResult() {
        super();
    }

    public ErrorResult(ResultCode code) {
        super();
        setResultCode(code);
    }

    public ErrorResult(ResultCode code, String resultString, Serializable... data) {
        this(code);
        this.resultString = resultString;
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                this.getData().add(data[i]);
            }
        }
    }

    public ErrorResult(ResultCode code, Serializable[] data) {
        this(code, null, data);

        // Convert data array to string message
        if (data != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                sb.append(data[i].toString());
                sb.append(";");
            }
            this.resultString = sb.toString();
        }
    }

    public ErrorResult(Exception e) {
        this(ResultCode.GENERAL_ERROR, e);
    }

    public ErrorResult(ResultCode code, Exception e) {
        this(code, "", e);
    }

    public ErrorResult(ResultCode code, String resultString, Exception e) {
        this(code);
        if (e != null) {
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            this.stackTrace = stackTrace.toString();
        }
        this.resultString = resultString;
    }

    public ErrorResult(ErrorResult r) {
        super(r);
        this.resultString = r.resultString;
        this.getData().addAll(r.getData());
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("resultString", resultString).toString();
    }
}