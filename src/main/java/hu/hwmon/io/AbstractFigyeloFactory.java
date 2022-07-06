package hu.hwmon.io;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.dto.HwMonException;
import hu.hwmon.dto.MemoriaAllapot;
import hu.hwmon.dto.ProcesszorAllapot;
import hu.hwmon.io.linux.LinuxFigyeloFactory;

public interface AbstractFigyeloFactory {
  Figyelo<MemoriaAllapot> createMemoriaFigyelo();
  Figyelo<ProcesszorAllapot> createProcesszorFigyelo();
  Figyelo<HalozatAllapot> createHalozatFigyelo();

  static AbstractFigyeloFactory getInstance() {
    return switch (OsDetect.detect()) {
      case LINUX -> new LinuxFigyeloFactory();
      case WINDOWS, MACOS -> throw new HwMonException("Not implemented!");
    };
  }
}
