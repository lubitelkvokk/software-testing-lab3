package se.ifmo.pages.registration.company;

public enum CompanyType {

    ORGANIZATION("f-test-switcher-button-Organizaciya"),
    PRIVATE_PERSON("f-test-switcher-button-Chastnoe_lico");

    private String className;

    CompanyType(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }
}
