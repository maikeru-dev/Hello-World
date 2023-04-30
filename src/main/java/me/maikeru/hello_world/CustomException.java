package me.maikeru.hello_world;

public class CustomException extends Exception {
    public CustomException(String e) {
        super(e);
    }
    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
