package com.sytoss.edu.library.tools.reader;

import org.springframework.stereotype.Component;

@Component
public class ParserFileReader {

    public String findClassName(String line, String fileExtension) {
        String fileName = setFileName(fileExtension, line);
        if (fileName == null) {
            String regex = "^public\\s+(?:class|interface|enum|record)\\s+(\\w+).*$";
            if (line.matches(regex)) {
               String className = line.replaceFirst("^public\\s+(\\w+\\s+)*(?:class|interface|enum|record)\\s", "");
                return className.split("\\s+")[0];
            }
        }
        return fileName;
    }

    public String setFileName(String fileExtension, String fileText){
        if(fileExtension.equals(FileExtensionEnum.YML.extension)
                || fileExtension.equals(FileExtensionEnum.YAML.extension)
                || fileExtension.equals(FileExtensionEnum.PROPERTIES.extension)){
            return "application";
        }
        else if(fileExtension.equals(FileExtensionEnum.XML.extension)){
            return ifThisIsPomFile(fileText) ? "pom" : null;
        }
        else if(fileExtension.equals(FileExtensionEnum.FEATURE.extension)){
            return findFeatureName(fileText);
        }
        return null;
    }

    private boolean ifThisIsPomFile(String fileText){
        return fileText.contains("maven.apache.org/POM");
    }

    private String findFeatureName(String fileText){
        if(fileText.contains("Feature")){
            return fileText.replaceAll("^Feature: ","");
        }
        return null;
    }


}
