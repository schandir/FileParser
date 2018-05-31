package com.nathan.fileparser.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Provides the results of an API method call.
 */
public class ListResult<T> extends DataResult<List<T>> {
    public ListResult() {
        super();
        this.setData(new ArrayList<T>());
    }

    public ListResult(Result r) {
        super(r);
        this.setData(new ArrayList<T>());
    }

    public ListResult(ListResult<T> r) {
        super(r);
        this.setData(new ArrayList<T>(r.getData()));
    }

    public ListResult(Collection<T> list) {
        super();
        this.setData(new ArrayList<T>(list));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("total", getTotal()).toString();
    }

    /**
     * Total size of this list (if pagination were not applied)
     */
    public int getTotal() {
        return getData().size();
    }
}