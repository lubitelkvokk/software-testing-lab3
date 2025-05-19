package se.ifmo.pages.searching;

public enum SearchOption {
    VACANCY("searchByHintSelect-item-0"),
    CV("searchByHintSelect-item-1"),
    COMPANY("searchByHintSelect-item-2"),
    COURSE("searchByHintSelect-item-3");

    final String id;

    SearchOption(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
