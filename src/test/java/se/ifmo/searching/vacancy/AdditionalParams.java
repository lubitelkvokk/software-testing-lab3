package se.ifmo.searching.vacancy;

public enum AdditionalParams {

    OVER_14_YEARS_OLD("f-test-link-Dostupno_dlya_soiskatelej_ot_14+_let"),
    OVER_45_YEARS_OLD("f-test-link-Dostupno_dlya_soiskatelej_ot_45+_let"),
    AVAILABLE_FOR_APPLICANTS_WITH_DISABILITIES("f-test-link-Dostupno_dlya_soiskatelej_s_ogranichennymi_vozmozhnostyami"),
    AVAILABLE_FOR_STUDENTS("f-test-link-Dostupno_studentam");

    final String className;
    AdditionalParams(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
