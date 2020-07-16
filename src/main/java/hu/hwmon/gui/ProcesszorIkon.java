package hu.hwmon.gui;

import hu.hwmon.dto.ProcesszorAllapot;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class ProcesszorIkon extends TaskbarIkon<ProcesszorAllapot> {
    private static final Color[] szinek = new Color[] {
        Color.BLUE,
        Color.RED,
        Color.GREEN,
        Color.YELLOW,
        Color.WHITE,
        Color.BLACK,
    };

    private String toolTip = "";

    @Override
    protected List<Double> formatter(ProcesszorAllapot adat) {
        var szazalekos = adat.getSzazalekos();

        toolTip = String.format(
            "CPU: %2.2f sys, %2.2f io, %2.2f usr, %2.2f nice, %2.2f other, %2.2f idle",
            szazalekos.getSystem(),
            szazalekos.getIowait(),
            szazalekos.getUser(),
            szazalekos.getNice(),
            szazalekos.getOther(),
            szazalekos.getIdle()
        );

        return Arrays.asList(
            szazalekos.getSystem(),
            szazalekos.getIowait(),
            szazalekos.getUser(),
            szazalekos.getNice(),
            szazalekos.getOther(),
            szazalekos.getIdle()
        );
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(ProcesszorAllapot adat) {
        return toolTip;
    }
}
