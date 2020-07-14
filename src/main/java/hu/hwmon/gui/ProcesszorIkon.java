package hu.hwmon.gui;

import hu.hwmon.dto.ProcesszorAllapot;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class ProcesszorIkon extends TaskbarIkon<ProcesszorAllapot> {
    private static final Color[] szinek = new Color[] {
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.YELLOW,
        Color.BLACK,
    };

    @Override
    protected List<Double> formatter(ProcesszorAllapot adat) {
        return Arrays.asList(
            adat.getIowait(),
            adat.getSys(),
            adat.getUser(),
            adat.getNice(),
            adat.getIdle()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(ProcesszorAllapot adat) {
        return String.format(
            "CPU: %s io, %s sys, %s usr, %s nice, %s idle",
            adat.getIowait(),
            adat.getSys(),
            adat.getUser(),
            adat.getNice(),
            adat.getIdle()
        );
    }
}
