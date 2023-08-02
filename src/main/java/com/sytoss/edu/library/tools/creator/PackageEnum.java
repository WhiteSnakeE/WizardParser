package com.sytoss.edu.library.tools.creator;

public enum PackageEnum {
    CONVERTER("converter"),
    DTO("dto"),
    CONTROLLER("controller"),
    SERVICE("service"),
    REPOSITORY("repository"),
    CONFIGURATION("configuration");

    public final String packageName;

    PackageEnum(String packageName) {
        this.packageName = packageName;
    }
}
