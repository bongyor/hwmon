package hu.hwmon.gui;

import hu.hwmon.dto.MemoriaAllapot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class SwapIkon extends TaskbarIkon<MemoriaAllapot> {
    private static final Color[] szinek = new Color[] {
        Color.RED,
        new Color(181, 58, 225),
        new Color(200, 255, 0),
        Color.BLACK
    };

    @Override
    protected List<Double> formatter(MemoriaAllapot adat) {
        return Arrays.asList(
            adat.swapActiveAnon(),
            adat.swapFelhasznalt() - adat.swapActiveAnon(),
            adat.swapCache(),
            adat.swapOsszes() - adat.swapFelhasznalt() - adat.swapCache()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(MemoriaAllapot adat) {
        return String.format(
            "SWAP: %2.0f (%2.0f, +%2.0f)/%2.0f",
            adat.swapActiveAnon(),
            adat.swapFelhasznalt(),
            adat.swapCache(),
            adat.swapOsszes()
        );
    }
}
