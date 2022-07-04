package hu.hwmon.gui;

import hu.hwmon.dto.MemoriaAllapot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class MemoriaIkon extends TaskbarIkon<MemoriaAllapot> {
    private static final Color[] szinek = new Color[] {
        Color.GREEN,
        Color.BLACK,
        Color.YELLOW,
        Color.BLUE,
        new Color(255, 0, 0),
        new Color(159, 76, 17)
    };

    @Override
    protected List<Double> formatter(MemoriaAllapot adat) {
        return Arrays.asList(
            adat.memFelhasznalt(),
            adat.free(),
            adat.cache(),
            adat.buffers(),
            adat.dirty(),
            adat.writeback()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(MemoriaAllapot adat) {
        return String.format(
            """
            usr:%2.0f,cache:%2.0f,free:%2.0f,buff:%2.0f,dirt:%2.0f/%2.0f,av:%2.0f""",
            adat.memFelhasznalt(),
            adat.cache(),
            adat.free(),
            adat.buffers(),
            adat.dirty(),
            adat.writeback(),
            adat.avaiable()
        );
    }
}
