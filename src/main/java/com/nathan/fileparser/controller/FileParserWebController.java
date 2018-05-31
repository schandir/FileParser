package com.nathan.fileparser.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nathan.fileparser.model.FileParserModel;
import com.nathan.fileparser.model.FileParserOutputModel;
import com.nathan.fileparser.service.FileParser;

@Controller
public class FileParserWebController {
    @Autowired
    private FileParser<FileParserOutputModel> fileParserService;

    @RequestMapping("/")
    public String index() {
        return "parse";
    }

    @RequestMapping("/parseFile")
    public String parseFile(Model model, @Valid FileParserModel fileParserModel) throws JsonProcessingException {
        FileParserOutputModel result = fileParserService.parseFile(fileParserModel);
        model.addAttribute("result", result.getOutputLines());
        //model.addAllAttributes(result.getOutputLines());
        return "parseResult";
    }
}
