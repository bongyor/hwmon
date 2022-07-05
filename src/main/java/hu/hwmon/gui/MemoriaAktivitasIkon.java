package hu.hwmon.gui;

import hu.hwmon.dto.MemoriaAllapot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class MemoriaAktivitasIkon extends TaskbarIkon<MemoriaAllapot> {
    private static final Color[] szinek = new Color[] {
        new Color(5, 106, 5),
        new Color(50, 255, 153, 255),
        Color.BLACK,
        new Color(0, 216, 225),
        Color.ORANGE
    };

    @Override
    protected List<Double> formatter(MemoriaAllapot adat) {
        return Arrays.asList(
            adat.activeAnon(),
            adat.inactiveAnon(),
            adat.free(),
            adat.inactiveFile(),
            adat.activeFile()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(MemoriaAllapot adat) {
        return String.format(
            "Anonymus: %2.0f (%2.0f), File: %2.0f (%2.0f)",
            adat.activeAnon(),
            adat.inactiveAnon(),
            adat.activeFile(),
            adat.inactiveFile()
            );
    }
}
