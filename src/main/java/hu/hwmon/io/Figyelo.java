package hu.hwmon.io;

import lombok.NonNull;

public abstract class Figyelo<T> {
    @NonNull protected String shell(@NonNull String command) {
        return command;
    }
    public abstract T getAllapot();
}
