package hu.hwmon.logic;

public interface Listener<T> {
  void listenEvent(T data);
}
