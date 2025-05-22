package se.ifmo.pages.searching.vacancy;

import lombok.Getter;

@Getter
public enum VacancyType {
    HIDE_AGENCY_VACANCIES("f-test-link-Polnaya", "pryamoj-rabotodatel"),
    NO_EXPERIENCE_REQUIRED("f-test-link-Opyt_raboty_ne_trebuetsya", "bez-opyta"),
    NO_HIGHER_EDUCATION_REQUIRED("f-test-link-Ne_trebuyuschie_vysshego_obrazovaniya", "srednee"),
    VIDEO_VACANCY("f-test-link-Videovakansii", "video=1");

    final String className;
    final String urlName;
    VacancyType(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
