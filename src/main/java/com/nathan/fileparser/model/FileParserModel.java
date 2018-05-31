package com.nathan.fileparser.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.nathan.fileparser.type.DataType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileParserModel extends Model {
    private static final long serialVersionUID = -6659115367547215930L;
    @NotNull
    private String fileLocation;
    @NotNull
    private String delimiter;
    //@NotNull
    private List<DataType> fileFormat;

}
