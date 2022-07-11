package hu.hwmon.io.linux;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.dto.MemoriaAllapot;
import hu.hwmon.dto.ProcesszorAllapot;
import hu.hwmon.gui.*;
import hu.hwmon.io.AbstractFigyeloFactory;
import hu.hwmon.io.Figyelo;
import hu.hwmon.logic.HalozatMapper;
import hu.hwmon.logic.ProcesszorMapper;

public class LinuxFigyeloFactory implements AbstractFigyeloFactory {
  @Override
  public Figyelo<MemoriaAllapot> createMemoriaFigyelo() {
    MemoriaFigyelo memoriaFigyelo = new MemoriaFigyelo();
    memoriaFigyelo.addListener(new MemoriaIkon());
    memoriaFigyelo.addListener(new MemoriaAktivitasIkon());
    memoriaFigyelo.addListener(new SwapIkon());
    return memoriaFigyelo;
  }

  @Override
  public Figyelo<ProcesszorAllapot> createProcesszorFigyelo() {
    ProcesszorFigyelo processzorFigyelo = new ProcesszorFigyelo();
    ProcesszorMapper mapper = new ProcesszorMapper();
    mapper.addListener(new ProcesszorIkon());
    processzorFigyelo.addListener(mapper);
    return processzorFigyelo;
  }

  @Override
  public Figyelo<HalozatAllapot> createHalozatFigyelo() {
    HalozatFigyelo halozatFigyelo = new HalozatFigyelo();
    HalozatMapper mapper = new HalozatMapper();
    mapper.addListener(new HalozatIkon());
    halozatFigyelo.addListener(mapper);
    return halozatFigyelo;
  }
}
