package com.nathan.fileparser.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nathan.fileparser.exception.ResultException;
import com.nathan.fileparser.model.FileParserModel;
import com.nathan.fileparser.model.FileParserOutputModel;
import com.nathan.fileparser.model.Key;
import com.nathan.fileparser.model.Record;
import com.nathan.fileparser.result.ResultCode;
import com.nathan.fileparser.service.FileParser;
import com.nathan.fileparser.type.DataType;

@Service
public class FileParserServiceImpl implements FileParser<FileParserOutputModel> {
    private final static Logger logger = LoggerFactory.getLogger(FileParserServiceImpl.class);
    private final static SimpleDateFormat OUTPUIT_DATE_FORMAT = new SimpleDateFormat("d/M/yy h:m:ss a z");
    private final static NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();

    @Value("${file.parser.columns : 4}")
    private int numberOfColumns;

    @Override
    public FileParserOutputModel parseFile(FileParserModel fileParserModel) {
        logger.info("Processing file parsing for the file '{}' with delimitter '{}'", fileParserModel.getFileLocation(), fileParserModel.getDelimiter());
        FileParserOutputModel fileParserOutputModel = new FileParserOutputModel();
        List<String> rejectedLines = new ArrayList<>();
        Map<Key, String> resultMap = parseFileWithDelimitter(fileParserModel, rejectedLines);
        if (MapUtils.isNotEmpty(resultMap)) {
            resultMap.forEach((k, v) -> {
                fileParserOutputModel.addToOutputLines(k.getRowDate(), k.getDescription(), v);
            });
        }
        fileParserOutputModel.setRejectedLines(rejectedLines);
        return fileParserOutputModel;
    }

    private Map<Key, String> parseFileWithDelimitter(final FileParserModel fileParserModel, List<String> rejectedLines) {
        Map<Key, String> recordMap = new LinkedHashMap<>();
        FileInputStream inputStream = null;
        Scanner sc = null;
        final String fileLocation = fileParserModel.getFileLocation();

        try {
            inputStream = new FileInputStream(fileLocation);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                try {
                    String[] splitLine = line.split(fileParserModel.getDelimiter());
                    if (CollectionUtils.isNotEmpty(fileParserModel.getFileFormat())) {
                        Record record = parseLineToRecord(splitLine, fileParserModel.getFileFormat());
                        recordMap.put(record.getRowKey(), record.getValue());
                    } else if (splitLine.length == numberOfColumns) {
                        Record record = parseLineToRecord(splitLine);
                        recordMap.put(record.getRowKey(), record.getValue());
                    } else {
                        rejectedLines.add(line);
                        logger.error("The record in the file doesnt adhere the foramt. Line :{}", line);
                        logger.info("Continuing with rest of the file");
                    }
                } catch (Exception e) {
                    logger.error("Exception occured whilw processing line", e);
                    rejectedLines.add(line);
                    logger.info("Ccontinuing with next line");
                }
                if (sc.ioException() != null) {
                    throw new ResultException(ResultCode.GENERAL_ERROR, sc.ioException());
                }
            }

        } catch (FileNotFoundException e) {
            logger.error("The file '{}' provided for parsing is not found", fileLocation, e);
            throw new ResultException(ResultCode.NOT_FOUND, "The file '" + fileLocation + "' not found");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
        return recordMap;
    }

    //this is default if no order of data types are passed in
    private Record parseLineToRecord(String[] line) {
        String rowDate = null;
        Double value = 0.0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss'Z'", Locale.ENGLISH);
        try {
            Date date = sdf.parse(line[0]);
            rowDate = OUTPUIT_DATE_FORMAT.format(date);
        } catch (Exception e) {

        }
        String description = line[2];
        if (line[3] != null && StringUtils.isNotBlank(line[3])) {
            value = Double.parseDouble(line[3]);
        }
        return new Record(rowDate, CURRENCY_FORMAT.format(value), description);
    }

    //To keep generic we can pass in a order of datatypes which can be used
    private Record parseLineToRecord(String[] line, List<DataType> dataTypes) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss'Z'", Locale.ENGLISH);

        String rowDate = null;
        String description = null;
        Double value = 0.0;
        for (int i = 0; i < dataTypes.size(); i++) {
            if (dataTypes.get(i) == DataType.DATE) {

                try {
                    Date date = sdf.parse(line[i]);
                    rowDate = OUTPUIT_DATE_FORMAT.format(date);
                } catch (ParseException e) {

                }
            } else if (dataTypes.get(i) == DataType.INTEGER) {
                Integer.parseInt(line[i]);
            } else if (dataTypes.get(i) == DataType.STRING) {
                description = line[i];
            } else if (dataTypes.get(i) == DataType.DOUBLE) {
                if (line[i] != null && !line[i].isEmpty()) {
                    value = Double.parseDouble(line[3]);
                }
            } else {
                throw new ResultException(ResultCode.UNIMPLEMENTED, "The given data type is not implemented or wrong data type is passed");
            }
        }

        return new Record(rowDate, CURRENCY_FORMAT.format(value), description);

    }

    public void setNumberOfColumns(int num) {
        numberOfColumns = num;
    }
}