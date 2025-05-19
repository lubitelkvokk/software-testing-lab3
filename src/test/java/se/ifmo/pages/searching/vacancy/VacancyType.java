package se.ifmo.pages.searching.vacancy;

public enum VacancyType {
    HIDE_AGENCY_VACANCIES("f-test-link-Polnaya"),
    NO_EXPERIENCE_REQUIRED("f-test-link-Opyt_raboty_ne_trebuetsya"),
    NO_HIGHER_EDUCATION_REQUIRED("f-test-link-Ne_trebuyuschie_vysshego_obrazovaniya"),
    VIDEO_VACANCY("f-test-link-Videovakansii");

    final String className;
    VacancyType(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
