package hu.hwmon.io;

import hu.hwmon.dto.HalozatAllapot;
import hu.hwmon.dto.HwMonException;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class HalozatFigyelo extends Figyelo<HalozatAllapot> {
    private final Shell shell = new Shell();
    private final Pattern pattern = Pattern.compile(
        "^\\s*(?<interface>.+:)\\s+(?<rbytes>[0-9]+)\\s+(?<rpackets>[0-9]+)\\s+(?<rerrs>[0-9]+)\\s+" +
            "(?<rdrop>[0-9]+)\\s+(?<rfifo>[0-9]+)\\s+(?<rframe>[0-9]+)\\s+(?<rcompressed>[0-9]+)\\s+" +
            "(?<rmulticast>[0-9]+)\\s+(?<tbytes>[0-9]+)\\s+(?<tpackets>[0-9]+)\\s+(?<terrs>[0-9]+)\\s+" +
            "(?<tdrop>[0-9]+)\\s+(?<tfifo>[0-9]+)\\s+(?<tcolls>[0-9]+)\\s+(?<tcarrier>[0-9]+)\\s+" +
            "(?<tcompressed>[0-9]+)$");
    @Override
    public HalozatAllapot getAllapot() {
        return feldolgoz(shell.call("cat /proc/net/dev"), LocalDateTime.now());
    }

    HalozatAllapot feldolgoz(String output, LocalDateTime now) {
        var halozatAllapot = output
            .lines()
            .filter(line -> line.matches("^[0-9a-z ]+[0-9]:.*$") && !line.startsWith("    lo"))
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
        if (halozatAllapot == null) {
            throw new HwMonException("Értelmezhetetlen kimenet: " + output);
        }
        return halozatAllapot;
    }
}
