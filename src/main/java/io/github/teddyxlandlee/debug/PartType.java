package io.github.teddyxlandlee.debug;

@Deprecated
public enum PartType {
    CLASS("Class"),
    METHOD("Method"),
    FIELD("Field");

    private final String capitalized;

    PartType(String string) {
        this.capitalized = string;
    }

    protected String asString() {
        return capitalized;
    }
}
