package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;
import hu.hwmon.dto.MemoriaAllapot;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class MemoriaFigyelo extends Figyelo<MemoriaAllapot> {
    private final Shell shell = new Shell();
    private final Pattern pattern = Pattern.compile(
        "^.*Mem: +(?<memOsszes>[0-9]+) +(?<memFelhasznalt>[0-9]+) +(?<free>[0-9]+) +(?<shared>[0-9]+) +" +
            "(?<buffers>[0-9]+) +(?<cache>[0-9]+) +(?<avaiable>[0-9]+)\\s*" +
            "Swap: +(?<swapOsszes>[0-9]+) +(?<swapFelhasznalt>[0-9]+) +(?<swapFree>[0-9]+)\\s*$",
        Pattern.DOTALL
    );
    @Override
    public MemoriaAllapot getAllapot() {
        return feldolgoz(shell.call("free --wide --mebi", null), LocalDateTime.now());
    }

    MemoriaAllapot feldolgoz(String output, LocalDateTime now) {
        var matcher = pattern.matcher(output);
        if (matcher.matches()) {
            return new MemoriaAllapot(
                now,
                Double.parseDouble(matcher.group("memOsszes")),
                Double.parseDouble(matcher.group("swapOsszes")),
                Double.parseDouble(matcher.group("memFelhasznalt")),
                Double.parseDouble(matcher.group("swapFelhasznalt")),
                Double.parseDouble(matcher.group("shared")),
                Double.parseDouble(matcher.group("buffers")),
                Double.parseDouble(matcher.group("cache")),
                Double.parseDouble(matcher.group("avaiable")),
                Double.parseDouble(matcher.group("free"))
            );
        }
        throw new HwMonException("Értelmezhetetlen kimenet: " + output);
    }
}
