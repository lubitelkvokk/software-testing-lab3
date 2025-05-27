package se.ifmo.util;

import java.sql.Driver;

public enum DriverRealisation {
    FIREFOX("firefox"),
    CHROME("chrome");

    private String name;

    DriverRealisation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
