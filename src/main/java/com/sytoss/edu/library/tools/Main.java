package com.sytoss.edu.library.tools;

import com.sytoss.edu.library.tools.convertor.Convertor;
import com.sytoss.edu.library.tools.creator.ParserFileCreator;
import com.sytoss.edu.library.tools.model.Parameters;
import com.sytoss.edu.library.tools.reader.ParserFileReader;
import com.sytoss.edu.library.tools.writer.ParserFileWriter;

import java.util.Random;

public class Main {
    public static void main(String[] args)  {
        Parser parser = new Parser(new ParserFileWriter(), new ParserFileCreator(), new ParserFileReader(), new Parameters());
        String output = "C:\\Users\\vladyslavk\\IdeaProjects\\WizardParser\\src\\main\\resources\\WizardCoder_project_2";
//        Convertor convertor = new Convertor();
//        convertor.convert(output);
        parser.parse(output, "C:\\Users\\vladyslavk\\IdeaProjects\\ParserTest\\src\\main\\java\\org\\example");




    }
}