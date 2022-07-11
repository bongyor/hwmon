package hu.hwmon.logic;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.dto.HalozatDiff;

import java.time.temporal.ChronoUnit;

public class HalozatMapper extends EventBroadcaster<HalozatDiff> implements Listener<HalozatAllapot> {
  private HalozatAllapot utolsoAllapot = null;
  private double maxSavszelesseg = 1;

  @Override
  public void listenEvent(HalozatAllapot data) {
    fireEvent(map(data));
  }

  private HalozatDiff map(HalozatAllapot adat) {
    if (utolsoAllapot == null) {
      utolsoAllapot = adat;
    }

    var diff = utolsoAllapot.diff(adat);
    long miliseconds = ChronoUnit.MILLIS.between(utolsoAllapot.idopont(), adat.idopont());
    double fogadottSavszelesseg = getMbPerSec(miliseconds, diff.receiveBytes());
    double kuldottSavszelesseg = getMbPerSec(miliseconds, diff.transmitBytes());
    maxSavszelessegFrissitese(fogadottSavszelesseg, kuldottSavszelesseg);
    utolsoAllapot = adat;

    return new HalozatDiff(
        fogadottSavszelesseg,
        kuldottSavszelesseg,
        maxSavszelesseg - fogadottSavszelesseg - kuldottSavszelesseg
    );
  }

  private Double getMbPerSec(long miliseconds, long bytes) {
    double megaByte = (double) bytes * 8 / (1024 * 1024);
    double second = (double) miliseconds / 1000;

    return megaByte / second;
  }


  private void maxSavszelessegFrissitese(double fogadott, double kuldott) {
    var aktualis = fogadott + kuldott;
    if (maxSavszelesseg < aktualis) {
      maxSavszelesseg = aktualis;
    }
  }
}
