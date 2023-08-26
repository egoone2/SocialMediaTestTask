package ru.osokin.tasklist.web.response;

public class SuccessResponse implements Response{
    private static final String STATUS = "OK";

    @Override
    public String getStatus() {
        return STATUS;
    }
}
