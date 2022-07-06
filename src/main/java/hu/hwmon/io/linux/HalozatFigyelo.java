package hu.hwmon.io.linux;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.io.Figyelo;
import hu.hwmon.io.Shell;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class HalozatFigyelo extends Figyelo<HalozatAllapot> {
    private final Shell shell = new Shell();
    private final Pattern pattern = Pattern.compile(
        "^\\s*(?<interface>.+:)\\s+(?<rbytes>\\d+)\\s+(?<rpackets>\\d+)\\s+(?<rerrs>\\d+)\\s+" +
            "(?<rdrop>\\d+)\\s+(?<rfifo>\\d+)\\s+(?<rframe>\\d+)\\s+(?<rcompressed>\\d+)\\s+" +
            "(?<rmulticast>\\d+)\\s+(?<tbytes>\\d+)\\s+(?<tpackets>\\d+)\\s+(?<terrs>\\d+)\\s+" +
            "(?<tdrop>\\d+)\\s+(?<tfifo>\\d+)\\s+(?<tcolls>\\d+)\\s+(?<tcarrier>\\d+)\\s+" +
            "(?<tcompressed>\\d+)$");
    @Override
    public HalozatAllapot getAllapot() {
        return feldolgoz(shell.call("cat /proc/net/dev"), LocalDateTime.now());
    }

    HalozatAllapot feldolgoz(String output, LocalDateTime now) {
      return output
          .lines()
          .filter(line -> line.startsWith("wlp") || line.startsWith("enp"))
          .map(
              line -> {
                  var matcher = pattern.matcher(line);
                  if (matcher.matches()) {
                      return new HalozatAllapot(
                          now,
                          Long.parseLong(matcher.group("rbytes")),
                          Long.parseLong(matcher.group("rpackets")),
                          Long.parseLong(matcher.group("tbytes")),
                          Long.parseLong(matcher.group("tpackets"))
                      );
                  }
                  return new HalozatAllapot(now, 0, 0, 0, 0);
              }
          ).reduce(
              new HalozatAllapot(now, 0, 0, 0, 0),
              HalozatAllapot::add
          );
    }
}
