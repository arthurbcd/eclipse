package fr.ubdx.net.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FormDataRepository {
    private static final List<FormData> FORM_DATA_LIST = new ArrayList<>();

    private FormDataRepository() {
    }

    public static void add(FormData formData) {
        FORM_DATA_LIST.add(formData);
    }

    public static List<FormData> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(FORM_DATA_LIST));
    }
}