package com.sytoss.edu.library.tools;

import com.sytoss.edu.library.tools.creator.ParserFileCreator;
import com.sytoss.edu.library.tools.reader.ParserFileReader;
import com.sytoss.edu.library.tools.writer.ParserFileWriter;

import java.io.*;

public class Parser {

    private final ParserFileWriter parserFileWriter;

    private final ParserFileCreator parserFileCreator;

    private final ParserFileReader parserFileReader;

    public Parser(ParserFileWriter parserFileWriter, ParserFileCreator parserFileCreator, ParserFileReader parserFileReader) {
        this.parserFileWriter = parserFileWriter;
        this.parserFileCreator = parserFileCreator;
        this.parserFileReader = parserFileReader;
    }

    public void parse(String from, String to) {
        String line;
        String fileExtension = null;
        StringBuilder fileValue = new StringBuilder();
        File file = null;
        String className = null;

        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(from));

            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches("^```[a-zA-Z].*$")) {
                    fileExtension = line.substring(3);
                    if (!fileExtension.equals("java")) {
                        className = "pom";
                    }
                    continue;
                }
                if (fileExtension != null) {
                    if (line.matches("```")) {
                        file = parserFileCreator.create(fileExtension, className, to);
                        parserFileWriter.writeToFile(fileValue.toString(), file);
                        fileValue.delete(0, fileValue.length());
                        fileExtension = null;
                        className = null;
                    } else {
                        fileValue.append(line);
                        if (className == null) {
                            className = parserFileReader.findClassName(line);
                        }
                        fileValue.append("\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


//    private String findFileExtension(String line,String fileExtension, String className){
//        if (line.matches("^```[a-zA-Z].*$")) {
//            fileExtension = line.substring(3);
//            if (!fileExtension.equals("java")){
//                className = "pom";
//            }
//        }
//    }

}
