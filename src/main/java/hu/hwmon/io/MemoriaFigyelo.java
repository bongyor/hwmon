package hu.hwmon.io;

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
      "^(?<kulcs>[a-zA-Z\\d()]+):\\s+(?<ertek>\\d+)\\s+kB\\s*$"
  );

  @Override
  public MemoriaAllapot getAllapot() {
    return feldolgoz(shell.call("cat /proc/meminfo", null), LocalDateTime.now());
  }

  MemoriaAllapot feldolgoz(String output, LocalDateTime now) {
    Map<String, Double> meminfo = parseMeminfoMap(output);
    double memTotal = meminfo.get("MemTotal");
    double swapTotal = meminfo.get("SwapTotal");
    double swapCache = meminfo.get("SwapCached");
    double swapFree = meminfo.get("SwapFree");
    double buffers = meminfo.get("Buffers");
    double cache = meminfo.get("Cached");
    double inactive = meminfo.get("Inactive");
    double dirty = meminfo.get("Dirty");
    double writeback = meminfo.get("Writeback");
    double memFree = meminfo.get("MemFree");
    double memAvailable = meminfo.get("MemAvailable");
    double swapFelhasznalt = swapTotal - swapFree - swapCache;
    double swapActive = Math.max(swapFelhasznalt - inactive, 0);
    double activeAnon = meminfo.get("Active(anon)");
    double inactiveAnon = meminfo.get("Inactive(anon)");
    double activeFile = meminfo.get("Active(file)");
    double inactiveFile = meminfo.get("Inactive(file)");

    return new MemoriaAllapot(
        now,
        memTotal,
        swapTotal,
        memTotal - memFree - buffers - cache,
        swapFelhasznalt - swapActive,
        swapCache,
        swapActive,
        swapFree,
        buffers,
        cache - dirty - writeback,
        dirty,
        writeback,
        memAvailable,
        memFree,
        activeAnon,
        inactiveAnon,
        activeFile,
        inactiveFile
    );
  }

  private Map<String, Double> parseMeminfoMap(String output) {
    return Arrays.stream(output.split("\n"))
        .map(pattern::matcher)
        .filter(Matcher::matches)
        .collect(
            Collectors.toMap(
                matcher -> matcher.group("kulcs"),
                matcher -> Double.parseDouble(matcher.group("ertek")) / 1024
            )
        );
  }
}
