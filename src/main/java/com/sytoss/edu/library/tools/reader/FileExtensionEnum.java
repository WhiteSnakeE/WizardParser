package com.sytoss.edu.library.tools.reader;

public enum FileExtensionEnum {
    JAVA("java"),
    XML("xml"),
    YAML("yaml"),
    YML("yml"),
    PROPERTIES("properties"),
    FEATURE("feature");

    public final String extension;

    FileExtensionEnum(String extension) {
        this.extension = extension;
    }

}
