package hu.hwmon.logic;

import java.util.ArrayList;
import java.util.List;

public abstract class EventBroadcaster<T> {
  private final List<Listener<T>> listeners = new ArrayList<>();

  public void addListener(Listener<T> listener) {
    listeners.add(listener);
  }

  protected void fireEvent(T data) {
    listeners.forEach(listener -> listener.listenEvent(data));
  }
}
