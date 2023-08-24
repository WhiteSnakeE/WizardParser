package com.sytoss.edu.library.tools.creator;

import com.sytoss.edu.library.tools.reader.FileExtensionEnum;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ParserFileCreator {

    public File create(String fileExtension, String fileName, String to) {
        File file;
        if (fileExtension.equals("java")) {
            if (fileName == null) {
                fileName = DefoltClassName.FILE_NAME;
            }

            to = findPackageToThisClass(to, fileName);

        } else if (fileName.equals("pom")) {
            to = createPomFile(to);

        } else {
            Random random = new Random();
            fileName += (long) (random.nextDouble(0,999999999) * (Long.MAX_VALUE - Long.MIN_VALUE));
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
            System.err.println("В строке нет 'src'.");
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

    public String findPackage(String className) {
        List<String> packages = Arrays.stream(PackageEnum.values())
                .map(Enum::name)
                .toList();
        for (String s : packages) {
            s = s.toLowerCase();
            if (className.toLowerCase().contains(s.toLowerCase())) {
                return s.toLowerCase();
            }

        }
        return "";
    }

    private String createFileInResources(String to, String fileExtension) {
        String regex;
        if (fileExtension.equals(FileExtensionEnum.FEATURE.extension)) {
            to = to.replace("main", "test");
            to = to.replaceFirst("java\\b.*", "");
            to += "\\" + "resources" + "\\" + "features";

            File file = new File(to);
            file.mkdirs();
        } else {
            regex = "(.*java).*";
            to = to.replaceAll(regex, "$1").replace("java", "resources");
        }
        return to;
    }


}
