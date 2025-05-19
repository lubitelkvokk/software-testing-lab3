package se.ifmo.pages.searching.vacancy;

public enum PublicationPeriod {

    LAST_24H("f-test-link-Za_24_chasa"),
    LAST_3_DAY("f-test-link-Za_3_dnya"),
    LAST_WEEK("f-test-link-Za_nedelyu");

    final String className;
    PublicationPeriod(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
