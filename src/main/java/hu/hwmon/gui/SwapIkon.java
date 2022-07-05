package hu.hwmon.gui;

import hu.hwmon.dto.MemoriaAllapot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class SwapIkon extends TaskbarIkon<MemoriaAllapot> {
    private static final Color[] szinek = new Color[] {
        Color.RED,
        new Color(50, 110, 255),
        new Color(200, 255, 0),
        Color.BLACK
    };

    @Override
    protected List<Double> formatter(MemoriaAllapot adat) {
        return Arrays.asList(
            adat.swapActiveAnon(),
            adat.swapInactive(),
            adat.swapCache(),
            adat.swapFree()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(MemoriaAllapot adat) {
        return String.format(
            "SWAP: %2.0f (inactive: %2.0f, cache:  %2.0f)/%2.0f",
            adat.swapActiveAnon(),
            adat.swapInactive(),
            adat.swapCache(),
            adat.swapOsszes()
        );
    }
}
