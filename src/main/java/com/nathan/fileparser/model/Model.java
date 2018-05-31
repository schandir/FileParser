package com.nathan.fileparser.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public abstract class Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -6607819848435489282L;

    protected Model() {
    }

    protected final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Model)) {
            // Nothing or not a model -- can't be equal to us
            return false;
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Model clone() {
        try {
            return (Model) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
