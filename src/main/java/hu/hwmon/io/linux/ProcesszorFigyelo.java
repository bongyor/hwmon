package hu.hwmon.io.linux;

import hu.hwmon.dto.HwMonException;
import hu.hwmon.dto.ProcesszorAllapot;
import hu.hwmon.io.Figyelo;
import hu.hwmon.io.Shell;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcesszorFigyelo extends Figyelo<ProcesszorAllapot> {
    private final Shell shell = new Shell();
    private final Pattern pattern = Pattern.compile(
        "^\\s*cpu +(?<user>\\d+) +(?<nice>\\d+) +(?<system>\\d+) +" +
            "(?<idle>\\d+) +(?<iowait>\\d+) +(?<irq>\\d+) +(?<softirq>\\d+) +(?<steal>\\d+) +" +
            "(?<guest>\\d+) +(?<guestnice>\\d+)\\s*$",
        Pattern.DOTALL
    );

    private ProcesszorAllapot current = null;
    @Override
    public ProcesszorAllapot getAllapot() {
        return feldolgoz(shell.call("cat /proc/stat"), LocalDateTime.now());
    }

    ProcesszorAllapot feldolgoz(String output, LocalDateTime now) {
        var cpuSummLine = output.lines().filter(line -> line.matches("^cpu .*$")).findFirst();
        if (cpuSummLine.isPresent()) {
            var matcher = pattern.matcher(cpuSummLine.get());
            if (matcher.matches()) {
                return feldolgozCpuSumLine(now, matcher);
            }
        }

        throw new HwMonException("Értelmezhetetlen kimenet: " + output);
    }

    private ProcesszorAllapot feldolgozCpuSumLine(LocalDateTime now, Matcher matcher) {
        var ujAllapot = new ProcesszorAllapot(
            now,
            getGroupAsDouble(matcher, "user"),
            getGroupAsDouble(matcher, "nice"),
            getGroupAsDouble(matcher, "system"),
            getGroupAsDouble(matcher, "idle"),
            getGroupAsDouble(matcher, "iowait"),
            getGroupAsDouble(matcher, "irq"),
            getGroupAsDouble(matcher, "softirq"),
            getGroupAsDouble(matcher, "steal"),
            getGroupAsDouble(matcher, "guest"),
            getGroupAsDouble(matcher, "guestnice")
        );
        if (current == null) {
            current = ujAllapot;
        }
        var diff = ujAllapot.diff(current);
        current = ujAllapot;

        return diff;
    }

    private double getGroupAsDouble(Matcher matcher, String groupName) {
        return Double.parseDouble(matcher.group(groupName).replace(",", "."));
    }
}
