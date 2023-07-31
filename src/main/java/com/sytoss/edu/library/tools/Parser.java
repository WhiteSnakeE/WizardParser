package com.sytoss.edu.library.tools;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

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
                    if (!fileExtension.equals("java")){
                        className = "pom";
                    }

                    continue;
                }
                if (fileExtension != null) {
                    if (line.matches("```")) {
                        file = create(fileExtension, className);
                        writeToFile(fileValue.toString(), file);
                        fileValue.delete(0, fileValue.length());
                        fileExtension = null;
                        className = null;
                    } else {
                        fileValue.append(line);
                        if(className==null) {
                            className = findClassName(line);
                        }
                        fileValue.append("\n");
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String findClassName(String line) {
        String className = null;
        String regex = "^public\\s+(?:class|interface)\\s+(\\w+).*$";
        if(line.matches(regex)){
            System.out.println(line);
            className = line.replaceFirst("^public\\s+(\\w+\\s+)*(?:class|interface)\\s","");
            System.out.println(className);
            className = className.split("\\s+")[0];
        }
        System.out.println(className);
        return className;
    }

    private File create(String fileExtension, String fileName) {
        File file = new File("C:\\Users\\vlad\\IdeaProjects\\WizardParser\\src\\main\\java\\com\\sytoss\\edu\\library" + "\\" + fileName + "." + fileExtension);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    private void writeToFile(String text, File file) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
