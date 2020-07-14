package hu.hwmon.gui;

import hu.hwmon.dto.MemoriaAllapot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class MemoriaIkon extends TaskbarIkon<MemoriaAllapot> {
    private static final Color[] szinek = new Color[] {
        Color.GREEN,
        Color.CYAN,
        Color.BLACK,
        Color.YELLOW,
    };

    @Override
    protected List<Double> formatter(MemoriaAllapot adat) {
        return Arrays.asList(
            adat.getMemFelhasznalt(),
            adat.getShared(),
            adat.getFree(),
            adat.getBuffCache()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(MemoriaAllapot adat) {
        return String.format(
            "Memória: %s usr, %s shr, %s free, %s buff, %s avaiable",
            adat.getMemFelhasznalt(),
            adat.getShared(),
            adat.getFree(),
            adat.getBuffCache(),
            adat.getAvaiable()
        );
    }
}
