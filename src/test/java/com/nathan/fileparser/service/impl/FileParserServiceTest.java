package com.nathan.fileparser.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.nathan.fileparser.model.FileParserModel;
import com.nathan.fileparser.model.FileParserOutputModel;

public class FileParserServiceTest {
    @InjectMocks
    FileParserServiceImpl fileParserService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fileParserService.setNumberOfColumns(4);
    }

    //Many more tests should be added but having a simple case
    //More validations should be added as well. Keeping it simple here

    @Test
    public void test_parseFileValid() throws Exception {
        FileParserModel fileParserModel = new FileParserModel();
        fileParserModel.setFileLocation("src/test/resources/sample.txt");
        fileParserModel.setDelimiter(",");
        FileParserOutputModel fileParserOutputModel = fileParserService.parseFile(fileParserModel);
        assertNotNull(fileParserOutputModel);
        assertEquals(fileParserOutputModel.getOutputLines().size(), 5);
    }

    @Test
    public void test_parseFileInValidLine() throws Exception {
        FileParserModel fileParserModel = new FileParserModel();
        fileParserModel.setFileLocation("src/test/resources/sample-invalid.txt");
        fileParserModel.setDelimiter(",");
        FileParserOutputModel fileParserOutputModel = null;
        fileParserOutputModel = fileParserService.parseFile(fileParserModel);

        assertNotNull(fileParserOutputModel);
        assertEquals(fileParserOutputModel.getOutputLines().size(), 4);
        assertEquals(fileParserOutputModel.getRejectedLines().size(), 1);
    }

}
