package com.nathan.fileparser.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nathan.fileparser.model.FileParserModel;
import com.nathan.fileparser.model.FileParserOutputModel;
import com.nathan.fileparser.result.DataResult;
import com.nathan.fileparser.result.ModelResult;
import com.nathan.fileparser.service.FileParser;

@RestController
@RequestMapping("/v1/file-parser")
public class FileParserController {
    @Autowired
    FileParser<FileParserOutputModel> fileParserService;

    /**
     * Use this API to process a file of format defined
     * fileFormat is optional to keep the parsing generic if we wantt to switch the column order
     * Sample JSON Request
     * Request payload example 1:
     * {
          "fileLocation": "C:\\Personal\\boomi\\test\\sample.txt",
          "delimiter": ",",
          "fileFormat":["DATE","INTEGER","STRING", "FLOAT"]
        }
        
      *Request payload example 2
      *{
          "fileLocation": "C:\\Personal\\boomi\\sample.txt",
          "delimiter": ","
        }
        
        * Sample JSON Response
        * Response payload example:
        {
            "resultCode": "OK",
            "resultSubCode": "",
            "serviceTransactionId": "453c41b4a0a1409aad30d6572ed2d9e0",
            "clientRequestId": "453c41b4a0a1409aad30d6572ed2d9e0",
            "data": {
            "outputLines": [
              {
                "rowDate": "31/3/07 3:32:12 AM EDT",
                "description": "Walget's Widgets",
                "value": "$50.00"
                },
              {
                "rowDate": null,
                "description": "Walget's Widgets",
                "value": "$0.00"
                },
              {
                "rowDate": "2/4/06 2:23:47 PM EDT",
                "description": "BMart Bubbles",
                "value": "$5.50"
                },
              {
                "rowDate": "18/7/06 1:53:23 PM EDT",
                "description": "Kgreen Kandies",
                "value": "$0.23"
                },
              {
                "rowDate": "24/12/06 10:0:08 PM EST",
                "description": "Santa Snacks",
                "value": "$2,000,065.61"
                }
            ],
                "rejectedLines": [],
            }
        }
     
         *
         * Sample Error Response
         {
            "resultCode": "NOT_FOUND",
            "resultSubCode": "",
            "serviceTransactionId": "0b27e905db0643ce99bdae712b47085a",
            "clientRequestId": "0b27e905db0643ce99bdae712b47085a",
            "data": [],
            "resultString": "NOT_FOUND: The file 'C:\Personal\boomi\test\sample.txt' not found",
            "total": 0
        }
        
        
         *
         * Sample Error Response
         {
            "resultCode": "GENERAL_ERROR",
            "resultSubCode": "",
            "serviceTransactionId": "3320973979fc49e38559ec33e4f6c53e",
            "clientRequestId": "3320973979fc49e38559ec33e4f6c53e",
            "data": [],
            "resultString": "Validation failed for argument at index 0 in method: 
                public com.nathan.fileparser.result.DataResult<com.nathan.fileparser.model.FileParserOutputModel> 
                com.nathan.fileparser.controller.FileParserController.parseFile(com.nathan.fileparser.model.FileParserModel), with 1 error(s): 
                [Field error in object 'fileParserModel' on field 'delimiter': rejected value [null]; codes [NotNull.fileParserModel.delimiter,NotNull.delimiter,NotNull.java.lang.String,NotNull]; 
                arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [fileParserModel.delimiter,delimiter]; arguments []; default message [delimiter]]; default message [may not be null]] ",
            "total": 0
        }
     * @param fileParserModel - FileParserModel
     * @return DataResult of FileParserOutputModel
     */
    @PostMapping
    public @ResponseBody DataResult<FileParserOutputModel> parseFile(@RequestBody @Valid FileParserModel fileParserModel) {
        return new ModelResult<FileParserOutputModel>(fileParserService.parseFile(fileParserModel));
    }

}
