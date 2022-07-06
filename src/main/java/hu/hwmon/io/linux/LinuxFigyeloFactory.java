package hu.hwmon.io.linux;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.dto.MemoriaAllapot;
import hu.hwmon.dto.ProcesszorAllapot;
import hu.hwmon.io.AbstractFigyeloFactory;
import hu.hwmon.io.Figyelo;

public class LinuxFigyeloFactory implements AbstractFigyeloFactory {
  @Override
  public Figyelo<MemoriaAllapot> createMemoriaFigyelo() {
    return new MemoriaFigyelo();
  }

  @Override
  public Figyelo<ProcesszorAllapot> createProcesszorFigyelo() {
    return new ProcesszorFigyelo();
  }

  @Override
  public Figyelo<HalozatAllapot> createHalozatFigyelo() {
    return new HalozatFigyelo();
  }
}
