package se.ifmo.pages.searching.vacancy;

import lombok.Getter;

public enum PublicationPeriod {

    LAST_24H("f-test-link-Za_24_chasa", "segodnya"),
    LAST_3_DAY("f-test-link-Za_3_dnya", "period=3"),
    LAST_WEEK("f-test-link-Za_nedelyu", "period=7");

    final String className;
    @Getter
    final String urlName;

    PublicationPeriod(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }

}
