package se.ifmo.pages.searching.course;

import lombok.Getter;

@Getter
public enum Specialization {
    ADMINISTRATIVE_WORK("f-test-link-Administrativnaya_rabota_sekretariat_AHO", "catalogues=1"),
    BANKS("f-test-link-Banki_investicii_lizing", "catalogues=381"),
    SHIFT("f-test-link-Bezopasnost_sluzhby_ohrany", "catalogues=182"),
    FINANCE("f-test-link-Bezopasnost_sluzhby_ohrany", "catalogues=11");

    final String className;
    final String urlName;
    Specialization(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
