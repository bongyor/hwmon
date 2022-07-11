package hu.hwmon.io.linux;

import hu.hwmon.gui.HalozatIkon;
import hu.hwmon.gui.ProcesszorIkon;
import hu.hwmon.gui.linux.MemoriaAktivitasIkon;
import hu.hwmon.gui.linux.MemoriaIkon;
import hu.hwmon.gui.linux.SwapIkon;
import hu.hwmon.io.AbstractFigyeloFactory;
import hu.hwmon.io.Figyelo;
import hu.hwmon.logic.HalozatMapper;
import hu.hwmon.logic.ProcesszorMapper;

public class LinuxFigyeloFactory implements AbstractFigyeloFactory {
  @Override
  public Figyelo<?> createMemoriaFigyelo() {
    MemoriaFigyelo memoriaFigyelo = new MemoriaFigyelo();
    memoriaFigyelo.addListener(new MemoriaIkon());
    memoriaFigyelo.addListener(new MemoriaAktivitasIkon());
    memoriaFigyelo.addListener(new SwapIkon());
    return memoriaFigyelo;
  }

  @Override
  public Figyelo<?> createProcesszorFigyelo() {
    ProcesszorFigyelo processzorFigyelo = new ProcesszorFigyelo();
    ProcesszorMapper mapper = new ProcesszorMapper();
    mapper.addListener(new ProcesszorIkon());
    processzorFigyelo.addListener(mapper);
    return processzorFigyelo;
  }

  @Override
  public Figyelo<?> createHalozatFigyelo() {
    HalozatFigyelo halozatFigyelo = new HalozatFigyelo();
    HalozatMapper mapper = new HalozatMapper();
    mapper.addListener(new HalozatIkon());
    halozatFigyelo.addListener(mapper);
    return halozatFigyelo;
  }
}
