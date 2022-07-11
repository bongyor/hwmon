package hu.hwmon.io;

import hu.hwmon.logic.EventBroadcaster;

public abstract class Figyelo<T> extends EventBroadcaster<T> {
    public abstract T getAllapot();

    public void refresh() {
        fireEvent(getAllapot());
    }
}
