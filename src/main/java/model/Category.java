package model;

import java.util.regex.Pattern;

public record Category(String name, int categoryID) {

    public Category {
        validateName(name);
    }

    private void validateName(String name) {
        if (name == null || !Pattern.matches("^[a-zA-Z]{1,50}+$", name)) {
            throw new IllegalArgumentException("Nieprawidłowa nazwa kategorii.");
        }
    }
}