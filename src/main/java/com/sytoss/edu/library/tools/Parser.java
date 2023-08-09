package com.sytoss.edu.library.tools;

import com.sytoss.edu.library.tools.creator.ParserFileCreator;
import com.sytoss.edu.library.tools.handler.FileStatus;
import com.sytoss.edu.library.tools.reader.ParserFileReader;
import com.sytoss.edu.library.tools.writer.ParserFileWriter;

import java.io.*;

public class Parser {

    private final ParserFileWriter parserFileWriter;

    private final ParserFileCreator parserFileCreator;

    private final ParserFileReader parserFileReader;

    private final String startOfFile = "^```[a-zA-Z].*$" ;

    private final String endOfFile  = "```";

    private FileStatus fileStatus = FileStatus.NOT_COMPLETED;

    public Parser(ParserFileWriter parserFileWriter, ParserFileCreator parserFileCreator, ParserFileReader parserFileReader) {
        this.parserFileWriter = parserFileWriter;
        this.parserFileCreator = parserFileCreator;
        this.parserFileReader = parserFileReader;
    }

    public void parse(String from, String to) {
        String line;
        String fileExtension = null;
        StringBuilder fileValue = new StringBuilder();
        File file;
        String className = null;

        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(from));

            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(startOfFile)) {
                    fileStatus = FileStatus.NOT_COMPLETED;
                    fileExtension = line.substring(3);
                    continue;
                }
                if (fileExtension != null) {
                    if (line.matches(endOfFile)) {
                        file = parseFile(fileExtension, className, to);
                        writeToFile(fileValue,file);
                        clean(fileValue);
                        fileExtension = null;
                        className = null;
                        fileStatus = FileStatus.COMPLETED;

                    } else {
                        fileValue.append(line);
                        if (className == null) {
                            className = parserFileReader.findClassName(line,fileExtension);
                        }
                        fileValue.append("\n");
                    }
                }
            }
            if(fileStatus == FileStatus.NOT_COMPLETED){
                assert fileExtension != null;
                file = parseFile(fileExtension, className, to);
                writeToFile(fileValue,file);
                clean(fileValue);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void clean(StringBuilder text){
        text.delete(0, text.length());
    }


    private File parseFile(String fileExtension, String className, String path){
        return parserFileCreator.create(fileExtension, className, path);
    }

    private void writeToFile(StringBuilder fileValue, File file){
        parserFileWriter.writeToFile(fileValue.toString(), file);
    }

}
