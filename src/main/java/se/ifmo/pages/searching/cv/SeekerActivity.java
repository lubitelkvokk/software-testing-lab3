package se.ifmo.pages.searching.cv;

public enum SeekerActivity {

    LAST_24H("f-test-link-24_chasa"),
    LAST_3_DAY("f-test-link-3_dnya"),
    LAST_WEEK("f-test-link-Nedelyu"),
    LAST_MONTH("f-test-link-1_mesyac");

    final String className;
    SeekerActivity(String name) {
        this.className = name;
    }

    @Override
    public String toString() {
        return className;
    }
}
