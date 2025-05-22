package se.ifmo.pages.searching.vacancy;

import lombok.Getter;

@Getter
public enum AdditionalParams {

    OVER_14_YEARS_OLD("f-test-link-Dostupno_dlya_soiskatelej_ot_14+_let", "tag%5B0%5D=5"),
    OVER_45_YEARS_OLD("f-test-link-Dostupno_dlya_soiskatelej_ot_45+_let", "tag%5B0%5D=6"),
    AVAILABLE_FOR_APPLICANTS_WITH_DISABILITIES("f-test-link-Dostupno_dlya_soiskatelej_s_ogranichennymi_vozmozhnostyami", "tag%5B0%5D=7"),
    AVAILABLE_FOR_STUDENTS("f-test-link-Dostupno_studentam", "tag%5B0%5D=8");

    final String className;
    final String urlName;
    AdditionalParams(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
