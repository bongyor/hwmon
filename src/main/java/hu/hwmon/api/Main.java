package hu.hwmon.api;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.dto.MemoriaAllapot;
import hu.hwmon.dto.ProcesszorAllapot;
import hu.hwmon.io.AbstractFigyeloFactory;
import hu.hwmon.io.Figyelo;
import hu.hwmon.io.VerifyModularization;

import java.util.concurrent.TimeUnit;

public class Main {
  private static final System.Logger logger = System.getLogger("hwmon");

  private static Figyelo<MemoriaAllapot> memoriaFigyelo;
  private static Figyelo<ProcesszorAllapot> processzorFigyelo;
  private static Figyelo<HalozatAllapot> halozatFigyelo;


  private static boolean run = true;

  public static void main(String[] args) {
    initFigyelok();
    VerifyModularization.verify();
    addShutdownHook();

    while (run) {
      frissites();
      sleepOneSec();
    }
  }

  private static void initFigyelok() {
    var factory = AbstractFigyeloFactory.getInstance();
    memoriaFigyelo = factory.createMemoriaFigyelo();
    processzorFigyelo = factory.createProcesszorFigyelo();
    halozatFigyelo = factory.createHalozatFigyelo();
  }

  private static void sleepOneSec() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static void addShutdownHook() {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> run = false));
  }

  private static void frissites() {
    try {
      memoriaFigyelo.refresh();
      processzorFigyelo.refresh();
      halozatFigyelo.refresh();
    } catch (Exception e) {
      logger.log(System.Logger.Level.ERROR, "Hiba a frissítés közben: " + e.getMessage(), e);
    }
  }

}
