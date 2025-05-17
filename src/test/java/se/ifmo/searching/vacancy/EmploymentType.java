package se.ifmo.searching.vacancy;

public enum EmploymentType {
    FULL("f-test-link-Polnaya"),
    NON_FULL("f-test-link-Nepolnaya"),
    SHIFT("f-test-link-Smennaya"),
    WATCH("f-test-link-Vahtovaya"),
    PART_TIME("f-test-link-Chastichnaya_zanyatost_sovmestitelstvo");

    final String className;
    EmploymentType(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
