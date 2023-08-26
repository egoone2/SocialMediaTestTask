package ru.osokin.tasklist.web.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse implements Response{

    private static final String STATUS = "ERROR";

    private String description;
    private String errorMessage;

    @Override
    public String getStatus() {
        return STATUS;
    }
}
