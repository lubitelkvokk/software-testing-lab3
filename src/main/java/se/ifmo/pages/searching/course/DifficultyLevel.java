package se.ifmo.pages.searching.course;

import lombok.Getter;

@Getter
public enum DifficultyLevel {

    START("f-test-link-Nachalnyj","difficulty=1"),

    INTERMEDIATE("f-test-link-Srednij","difficulty=2"),

    PROFESSIONAL("f-test-link-Professionalnyj","difficulty=3");

    final String className;
    final String urlName;

    DifficultyLevel(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
