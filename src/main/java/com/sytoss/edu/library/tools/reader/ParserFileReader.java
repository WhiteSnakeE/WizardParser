package com.sytoss.edu.library.tools.reader;

import org.springframework.stereotype.Component;

import java.io.File;
@Component
public class ParserFileReader {

    public String findClassName(String line) {
        String className = null;
        String regex = "^public\\s+(?:class|interface)\\s+(\\w+).*$";
        if(line.matches(regex)){
            className = line.replaceFirst("^public\\s+(\\w+\\s+)*(?:class|interface)\\s","");
            className = className.split("\\s+")[0];
        }
        return className;
    }



}
