package hu.hwmon.logic;

import hu.hwmon.dto.ProcesszorAllapot;

public class ProcesszorMapper extends EventBroadcaster<ProcesszorAllapot.Szazalekos>
    implements Listener<ProcesszorAllapot> {
  @Override
  public void listenEvent(ProcesszorAllapot data) {
    fireEvent(data.getSzazalekos());
  }
}
