package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;
import hu.hwmon.io.linux.LinuxFigyeloFactory;

public interface AbstractFigyeloFactory {
  Figyelo<?> createMemoriaFigyelo();
  Figyelo<?> createProcesszorFigyelo();
  Figyelo<?> createHalozatFigyelo();

  static AbstractFigyeloFactory getInstance() {
    return switch (OsDetect.detect()) {
      case LINUX -> new LinuxFigyeloFactory();
      case WINDOWS, MACOS -> throw new HwMonException("Not implemented!");
    };
  }
}
