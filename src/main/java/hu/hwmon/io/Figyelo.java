package hu.hwmon.io;

public abstract class Figyelo<T> {
    protected String shell(String command) {
        return command;
    }
    public abstract T getAllapot();
}
