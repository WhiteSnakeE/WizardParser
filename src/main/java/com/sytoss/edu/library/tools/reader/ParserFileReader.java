package com.sytoss.edu.library.tools.reader;

import com.sytoss.edu.library.tools.creator.DefoltClassName;
import org.springframework.stereotype.Component;

@Component
public class ParserFileReader {

    private final String classNameRegex = "^(?:public\\s+)?(?:class|interface|enum|record)\\s+(\\w+).*$";

    private final String setClassnameRegex = "^(?:public\\s+)?(\\w+\\s+)*(?:class|interface|enum|record)\\s";

    public String findClassName(String line, String fileExtension) {
        String fileName = setFileName(fileExtension, line);
        if (fileName == null) {
            if (line.matches(classNameRegex)) {
               String className = line.replaceFirst(setClassnameRegex, "");
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
        else if(fileExtension.equals(FileExtensionEnum.JAVA.extension)){
            return null;
        }
        return DefoltClassName.FILE_NAME;
    }

    private boolean ifThisIsPomFile(String fileText){
        return fileText.contains("maven.apache.org/POM");
    }


}
