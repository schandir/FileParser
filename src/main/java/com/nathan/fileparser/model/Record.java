package com.nathan.fileparser.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Record {
    public Record(String rowDate, String value, String description) {
        this.rowKey = (new Key(rowDate, description));
        this.value = value;
    }

    private Key rowKey;
    private String value;
}