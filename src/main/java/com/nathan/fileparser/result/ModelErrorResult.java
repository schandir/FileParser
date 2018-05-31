package com.nathan.fileparser.result;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.nathan.fileparser.request.ServiceContext;

/**
 * API method result for use when an error occurs. Contains information about the error.
 *
 *
 */
public class ModelErrorResult extends ErrorResult {

    public ModelErrorResult() {
        super();
    }

    public ModelErrorResult(ModelErrorResult r) {
        super(r);
        setContextIds();
    }

    public <T> ModelErrorResult(List<Serializable> data) {
        this.getData().addAll(data);
        setContextIds();
    }

    public ModelErrorResult(ResultCode code, String resultString, Serializable... data) {
        super(code, resultString, data);
        setContextIds();

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).toString();
    }

    private void setContextIds() {
        setServiceTransactionId(ServiceContext.getServiceContext().getServiceTransactionId());
        setClientRequestId(ServiceContext.getServiceContext().getClientRequestId());
    }
}