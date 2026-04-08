package me.bitsandbites.backend.dtos;

public enum Role {
    User("User"),
    Trainer("Trainer");

    public final String label;
    private Role(String label) {
        this.label = label;
    }
}
