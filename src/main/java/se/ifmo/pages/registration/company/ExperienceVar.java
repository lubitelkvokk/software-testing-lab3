package se.ifmo.pages.registration.company;

public enum ExperienceVar {

    NO_EXPIRIENCE("f-test-button-bez_opyta"),
    FROM_1YEAR("f-test-button-ot_1_goda"),
    FROM_3YEAR("f-test-button-ot_3_let"),
    FROM_6YEAR("f-test-button-ot_6_let");

    private String className;

    ExperienceVar(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }
}
