package com.sytoss.edu.library.tools;

import com.sytoss.edu.library.tools.creator.ParserFileCreator;
import com.sytoss.edu.library.tools.handler.FileStatus;
import com.sytoss.edu.library.tools.model.Parameters;
import com.sytoss.edu.library.tools.reader.ParserFileReader;
import com.sytoss.edu.library.tools.writer.ParserFileWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private final ParserFileWriter parserFileWriter;

    private final ParserFileCreator parserFileCreator;

    private final ParserFileReader parserFileReader;

    private final Parameters parameters;

    private static final String START_OF_FILE = "^```[a-zA-Z].*$";

    private static final String END_OF_FILE = "```";

    private FileStatus fileStatus;

    public Parser(ParserFileWriter parserFileWriter, ParserFileCreator parserFileCreator, ParserFileReader parserFileReader, Parameters parameters) {
        this.parserFileWriter = parserFileWriter;
        this.parserFileCreator = parserFileCreator;
        this.parserFileReader = parserFileReader;
        this.parameters = parameters;
    }

    public void parse(String from, String to) {
        String line;
        StringBuilder fileValue = new StringBuilder();
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(from));

            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(START_OF_FILE)) {
                    checkStartOfFIle(line, to, fileValue);
                    continue;
                }
                checkEndOfLine(line, to, fileValue);
            }
            if (fileStatus == FileStatus.NOT_COMPLETED) {
                writeToFile(fileValue, to);
                clean(fileValue);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void clean(StringBuilder text) {
        text.delete(0, text.length());
    }


    private File parseFile(String fileExtension, String className, String path) {
        return parserFileCreator.create(fileExtension, className, path);
    }

    private void writeToFile(StringBuilder fileValue, String path) {
        File file = parseFile(parameters.getExtension(), parameters.getClassName(), path);
        parserFileWriter.writeToFile(fileValue.toString(), file);
        parameters.setClassName(null);
    }

    private void checkStartOfFIle(String line, String path, StringBuilder fileValue) {
        if (fileStatus == FileStatus.NOT_COMPLETED) {
            writeToFile(fileValue, path);
            parameters.setClassName(null);
        }
        clean(fileValue);
        fileStatus = FileStatus.NOT_COMPLETED;
        String fileExtension = line.substring(3);
        parameters.setExtension(fileExtension);
    }


    private void checkEndOfLine(String line, String path, StringBuilder fileValue) {
        if (parameters.getExtension() != null) {
            if (line.matches(END_OF_FILE)) {
                writeToFile(fileValue, path);
                parameters.setExtension(null);
                fileStatus = FileStatus.COMPLETED;
            } else {
                fileValue.append(line);
                if (parameters.getClassName() == null) {
                    parameters.setClassName(parserFileReader.findClassName(line, parameters.getExtension()));
                }
                fileValue.append("\n");
            }
        }
    }


}
