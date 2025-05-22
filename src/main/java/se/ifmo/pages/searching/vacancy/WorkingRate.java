package se.ifmo.pages.searching.vacancy;

public enum WorkingRate {
    MONTH("f-test-link-Mesyac"), DAY("f-test-link-Den"), WATCH("f-test-link-Vahta");

    final String className;
    WorkingRate(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
