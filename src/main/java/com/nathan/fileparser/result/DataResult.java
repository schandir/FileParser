package com.nathan.fileparser.result;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Provides the results of an API method call.
 */
public class DataResult<T> extends Result {
    /**
     * The object(s) that were affected by the API method call.
     */
    private T data;

    public DataResult() {
        super();
    }

    public DataResult(Result r) {
        super(r);
    }

    public DataResult(DataResult<T> r) {
        super(r);
        this.data = r.data;
    }

    public DataResult(T data) {
        super();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("data", data).toString();
    }
}
