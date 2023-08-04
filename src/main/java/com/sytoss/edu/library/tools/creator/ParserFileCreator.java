package com.sytoss.edu.library.tools.creator;

import com.sytoss.edu.library.tools.reader.FileExtensionEnum;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserFileCreator {
    private int a = 1;

    public File create(String fileExtension, String fileName, String to) {
        File file = null;
        if (fileExtension.equals("java")) {
            if(fileName==null) {
                fileName = DefoltClassName.FILE_NAME;
            }

            to = findPackageToThisClass(to, fileName);

        }
        else if (fileName.equals("pom")) {
            to = createPomFile(to);

        } else {
            fileName += a;
            a++;
            to = createFileInResources(to, fileExtension);
        }
        file = new File(to + "\\" + fileName + "." + fileExtension);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    private String createPomFile(String to) {
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

    private String findPackageToThisClass(String to, String className) {
        List<String> packages = Arrays.stream(PackageEnum.values())
                .map(Enum::name)
                .toList();
        for (String s : packages) {
            s = s.toLowerCase();
            if (className.toLowerCase().contains("test")) {
                to = to.replace("main", "test");
                File file = new File(to);
                if (className.toLowerCase().contains(s)) {
                    to = to + "\\" + s;
                }
                file.mkdirs();
            } else if (className.toLowerCase().contains(s)) {
                to = to + "\\" + s;
                File file = new File(to);
                file.mkdir();
            }
        }

        return to;
    }

    private String createFileInResources(String to, String fileExtension) {
        String regex;
        if (fileExtension.equals(FileExtensionEnum.FEATURE.extension)) {
            to = to.replace("main", "test");
            to = to.replaceFirst("java\\b.*", "");
            to += "\\" + "resources" + "\\" + "features";

            File file = new File(to);
            file.mkdirs();
            return to;
        } else {
            regex = "(.*java).*";
            to = to.replaceAll(regex, "$1").replaceAll("java", "resources");
            System.out.println(to);
            return to;
        }
    }


}
