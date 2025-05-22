package se.ifmo.pages.searching.vacancy;

import lombok.Getter;

@Getter
public enum EmploymentType {
    FULL("f-test-link-Polnaya", "polnyj-den"),
    NON_FULL("f-test-link-Nepolnaya", "work_type[0]=10"),
    SHIFT("f-test-link-Smennaya", "smennyj-grafik"),
    WATCH("f-test-link-Vahtovaya", "vahta"),
    PART_TIME("f-test-link-Chastichnaya_zanyatost_sovmestitelstvo", "work_type[0]=13");

    final String className;
    final String urlName;
    EmploymentType(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
