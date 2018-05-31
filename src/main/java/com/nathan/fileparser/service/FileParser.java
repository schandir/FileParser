package com.nathan.fileparser.service;

import com.nathan.fileparser.model.FileParserModel;

//Generic File Parser Interface which can be extended for different types and output models
public interface FileParser<T> {

    /**
     * Returns parsed file from the given location.
     * @param FileParserModel -- Contains file location and delimitter
     * @return FileParserOutputModel
     */
    public T parseFile(FileParserModel fileParserModel);

}
