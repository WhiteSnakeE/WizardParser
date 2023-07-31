package com.sytoss.edu.library.tools.creator;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ParserFileCreator {
    public File create(String fileExtension, String fileName, String to) {
        File file = null;
        if(fileName.equals("pom")){
            to =  createPomFile(to);
        }
        else {
            to += "\\" +  findPackageToThisClass(fileName);
        }
        file = new File(to + "\\" + fileName + "." + fileExtension);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    private String createPomFile(String to){
        String regex = "src.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(to);
        if (matcher.find()) {
            to = to.replaceAll(regex, "");
        } else {
            System.out.println("В строке нет 'src'.");
        }
        return to;
    }

    private void createClassInPackage(String packageName){
        File file = new File("C:\\Users\\vlad\\IdeaProjects\\ParserTest\\src\\main\\java\\com\\sytoss\\edu\\library");
        file.mkdir();
    }

    private String findPackageToThisClass(String className){
        String packageName = "";
        List<String> packages = Arrays.stream(PackageEnum.values())
                .map(Enum::name)
                .toList();
        for (String s: packages){
            s = s.toLowerCase();
            if(className.toLowerCase().contains(s)){
                File file = new File("C:\\Users\\vlad\\IdeaProjects\\ParserTest\\src\\main\\java\\com\\sytoss\\edu\\library\\" + s);
                file.mkdir();
                packageName = s;
            }
        }
        return packageName;
    }
}
