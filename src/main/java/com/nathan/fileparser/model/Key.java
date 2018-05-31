package com.nathan.fileparser.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Key {
    String rowDate;
    String description;

    public Key(String date, String description) {
        this.rowDate = date;
        this.description = description;
    }

}
