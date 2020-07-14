package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;
import hu.hwmon.dto.ProcesszorAllapot;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcesszorFigyelo extends Figyelo<ProcesszorAllapot> {
    private final Shell shell = new Shell();
    private final Pattern pattern = Pattern.compile(
        "^.*\\((?<magok>[0-9]+) CPU\\).* +all +(?<usr>[0-9,.]+) +(?<nice>[0-9,.]+) +(?<sys>[0-9,.]+) +" +
            "(?<iowait>[0-9,.]+) +(?<irq>[0-9,.]+) +(?<soft>[0-9,.]+) +(?<steal>[0-9,.]+) +(?<guest>[0-9,.]+) +" +
            "(?<gnice>[0-9,.]+) +(?<idle>[0-9,.]+)\\s+.*$",
        Pattern.DOTALL
    );
    @Override
    public ProcesszorAllapot getAllapot() {
        return feldolgoz(shell.call("mpstat -P ALL 2 1", new String[]{"S_COLORS=never"}), LocalDateTime.now());
    }

    ProcesszorAllapot feldolgoz(String output, LocalDateTime now) {
        var matcher = pattern.matcher(output);
        if (matcher.matches()) {
            return new ProcesszorAllapot(
                now,
                Integer.parseInt(matcher.group("magok")),
                getGroupAsDouble(matcher, "usr"),
                getGroupAsDouble(matcher, "nice"),
                getGroupAsDouble(matcher, "sys"),
                getGroupAsDouble(matcher, "iowait"),
                getGroupAsDouble(matcher, "irq"),
                getGroupAsDouble(matcher, "soft"),
                getGroupAsDouble(matcher, "steal"),
                getGroupAsDouble(matcher, "guest"),
                getGroupAsDouble(matcher, "gnice"),
                getGroupAsDouble(matcher, "idle")
            );
        }
        throw new HwMonException("Értelmezhetetlen kimenet: " + output);
    }

    private double getGroupAsDouble(Matcher matcher, String groupName) {
        return Double.parseDouble(matcher.group(groupName).replace(",", "."));
    }
}
