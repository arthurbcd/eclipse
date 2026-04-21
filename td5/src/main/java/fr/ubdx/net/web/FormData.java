package fr.ubdx.net.web;

public class FormData {
    private final String name;
    private final String email;
    private final String message;

    public FormData(String name, String email, String message) {
        this.name = name == null ? "" : name;
        this.email = email == null ? "" : email;
        this.message = message == null ? "" : message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
