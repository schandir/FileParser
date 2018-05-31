package com.nathan.fileparser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileParserOutputModel extends Model {
    private static final long serialVersionUID = 828912847774265857L;
    private List<OutputLine> outputLines = new ArrayList<>();
    private List<String> rejectedLines = new ArrayList<>();

    public List<String> addToTRejectedLines(String line) {
        rejectedLines.add(line);
        return rejectedLines;
    }

    public List<OutputLine> addToOutputLines(String rowDate, String description, String value) {
        outputLines.add(new OutputLine(rowDate, description, value));
        return outputLines;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public class OutputLine {
        OutputLine(String date, String desc, String val) {
            this.setRowDate(date);
            this.setDescription(desc);
            this.setValue(val);

        }

        private String rowDate;
        private String description;
        private String value;
    }
}
