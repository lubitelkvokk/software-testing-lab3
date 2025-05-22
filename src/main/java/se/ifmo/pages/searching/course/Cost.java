package se.ifmo.pages.searching.course;

import lombok.Getter;

@Getter
public enum Cost {
    FREE("f-test-link-Besplatno", "price=0"),
    LESS_THAN_5000_RUB("f-test-link-Do_5_000", "price=1%2C5000");

    final String className;
    final String urlName;
    Cost(String name, String urlName) {
        this.className = name;
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return className;
    }
}
