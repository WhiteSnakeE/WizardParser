package com.sytoss.edu.library.tools;

import com.sytoss.edu.library.tools.creator.ParserFileCreator;
import com.sytoss.edu.library.tools.reader.ParserFileReader;
import com.sytoss.edu.library.tools.writer.ParserFileWriter;

import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser(new ParserFileWriter(),new ParserFileCreator(),new ParserFileReader());
        parser.parse("C:\\Users\\vlad\\IdeaProjects\\WizardParser\\src\\main\\resources\\WizardCoder_project_1",
                "C:\\Users\\vlad\\IdeaProjects\\ParserTest\\src\\main\\java\\com\\sytoss\\edu\\library");
    }
}