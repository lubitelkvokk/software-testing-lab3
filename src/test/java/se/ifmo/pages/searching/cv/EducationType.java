package se.ifmo.pages.searching.cv;

public enum EducationType {
    HIGHER("f-test-link-Vysshee_obrazovanie"),
    SECONDARY_SPECIALIZED("f-test-link-Srednee_specialnoe_obrazovanie"),
    INCOMPLETE_HIGHER("f-test-link-Nepolnoe_vysshee_obrazovanie"),
    SECONDARY_EDUCATION("f-test-link-Srednee_obrazovanie"),
    SCIENCE_CANDIDATE("f-test-link-Kandidat_nauk"),
    SCHOOL_STUDENT("f-test-link-Uchaschijsya_shkoly"),
    DOCTOR_OF_SCIENCE("f-test-link-Doktor_nauk");

    final String className;
    EducationType(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
