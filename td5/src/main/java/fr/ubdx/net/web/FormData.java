package fr.ubdx.net.web;

public class FormData {
    private String name;
    private String email;
    private String message;

    public FormData() {
        this.name = "";
        this.email = "";
        this.message = "";
    }

    public FormData(String name, String email, String message) {
        this.name = name == null ? "" : name;
        this.email = email == null ? "" : email;
        this.message = message == null ? "" : message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
