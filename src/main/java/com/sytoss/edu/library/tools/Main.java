package com.sytoss.edu.library.tools;

import com.sytoss.edu.library.tools.creator.ParserFileCreator;
import com.sytoss.edu.library.tools.model.Parameters;
import com.sytoss.edu.library.tools.reader.ParserFileReader;
import com.sytoss.edu.library.tools.writer.ParserFileWriter;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser(new ParserFileWriter(), new ParserFileCreator(), new ParserFileReader(), new Parameters());
        parser.parse("C:\\Users\\vladyslavk\\IdeaProjects\\WizardParser\\src\\main\\resources\\WizardCoder_project_1",
                "C:\\Users\\vladyslavk\\IdeaProjects\\ParserTest\\src\\main\\java\\org\\example");
    }
}