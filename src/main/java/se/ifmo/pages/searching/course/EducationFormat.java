package se.ifmo.pages.searching.course;

import lombok.Getter;

@Getter
public enum EducationFormat {
    ONLINE("f-test-link-Onlajn", "format=1"),
    OFFLINE("f-test-link-Oflajn", "format=2"),
    MIXED("f-test-link-Smeshannyj", "format=3");

    final String className;
    final String urlName;
    EducationFormat(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
