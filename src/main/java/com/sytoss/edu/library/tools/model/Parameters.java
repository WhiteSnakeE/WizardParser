package com.sytoss.edu.library.tools.model;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Parameters {

    private File file;

    private String extension;

    private String className;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
