package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;
import hu.hwmon.dto.MemoriaAllapot;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MemoriaFigyelo extends Figyelo<MemoriaAllapot> {
  private final Shell shell = new Shell();
  private final Pattern pattern = Pattern.compile(
      "^(?<kulcs>[a-zA-Z\\d]+):\\s+(?<ertek>\\d+)\\s+kB\\s*$"
  );

  @Override
  public MemoriaAllapot getAllapot() {
    return feldolgoz(shell.call("cat /proc/meminfo", null), LocalDateTime.now());
  }

  MemoriaAllapot feldolgoz(String output, LocalDateTime now) {
    Map<String, Double> meminfo = parseMeminfoMap(output);
    double memTotal = meminfo.get("MemTotal") / 1024;
    double swapTotal = meminfo.get("SwapTotal") / 1024;
    double swapCache = meminfo.get("SwapCached") / 1024;
    double swapFree = meminfo.get("SwapFree") / 1024;
    double shmem = meminfo.get("Shmem") / 1024;
    double buffers = meminfo.get("Buffers") / 1024;
    double cached = meminfo.get("Cached") / 1024;
    double memFree = meminfo.get("MemFree") / 1024;
    double memAvailable = meminfo.get("MemAvailable") / 1024;
    return new MemoriaAllapot(
        now,
        memTotal,
        swapTotal,
        memTotal - memFree - buffers - cached,
        swapTotal - swapFree - swapCache,
        swapCache,
        shmem,
        buffers,
        cached,
        memAvailable,
        memFree
    );
  }

  private Map<String, Double> parseMeminfoMap(String output) {
    return Arrays.stream(output.split("\n"))
        .map(pattern::matcher)
        .filter(Matcher::matches)
        .collect(
            Collectors.toMap(
                matcher -> matcher.group("kulcs"),
                matcher -> Double.parseDouble(matcher.group("ertek"))
            )
        );
  }
}
