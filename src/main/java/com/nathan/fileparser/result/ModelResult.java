package com.nathan.fileparser.result;

import com.nathan.fileparser.request.ServiceContext;

public class ModelResult<T> extends DataResult<T> {

    public ModelResult() {
    }

    public ModelResult(ModelResult<T> r) {
        super(r);
    }

    public ModelResult(T data) {
        super(data);
        setServiceTransactionId(ServiceContext.getServiceContext().getServiceTransactionId());
        setClientRequestId(ServiceContext.getServiceContext().getClientRequestId());
    }

}
