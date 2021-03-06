package hu.hwmon.gui;

import hu.hwmon.dto.HalozatAllapot;
import lombok.NonNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class HalozatIkon extends TaskbarIkon<HalozatAllapot> {
    private String tooltip = "";
    private HalozatAllapot utolsoAllapot = null;
    private double maxSavszelesseg = 1_000_000;
    private static final Color[] szinek = new Color[] {
        Color.ORANGE,
        Color.CYAN,
        Color.BLACK
    };

    @Override
    protected List<Double> formatter(HalozatAllapot adat) {
        if (utolsoAllapot == null) {
            utolsoAllapot = adat;
        }

        var diff = utolsoAllapot.diff(adat);
        maxSavszelessegFrissitese(diff);
        toolTipFrissitese(diff, utolsoAllapot.getIdopont(), adat.getIdopont());

        var result =  Arrays.asList(
            (double)diff.getReceiveBytes(),
            (double)diff.getTransmitBytes(),
            maxSavszelesseg - (double)diff.getReceiveBytes() - (double)diff.getTransmitBytes()
        );
        utolsoAllapot = adat;
        return result;
    }

    private void toolTipFrissitese(
        @NonNull HalozatAllapot diff,
        @NonNull LocalDateTime utolsoIdopont,
        @NonNull LocalDateTime adatIdopont
    ) {
        long miliseconds = ChronoUnit.MILLIS.between(utolsoIdopont, adatIdopont);

        tooltip = String.format(
            "Halozat: r: %2.2f Mb/s t: %2.2f Mb/s",
            getMbPerSec(miliseconds, diff.getReceiveBytes()),
            getMbPerSec(miliseconds, diff.getTransmitBytes())
        );
    }

    private Double getMbPerSec(long miliseconds, long bytes) {
        double megaByte = (double) bytes / (1024 * 1024);
        double second = (double) miliseconds / 1000;

        return megaByte / second;
    }

    private void maxSavszelessegFrissitese(HalozatAllapot diff) {
        if (diff.getReceiveBytes() + diff.getTransmitBytes() > maxSavszelesseg) {
            maxSavszelesseg = (double) diff.getReceiveBytes() + diff.getTransmitBytes();
        }
    }

    @Override
    protected Color[] szinek() {
        return szinek;
    }

    @Override
    protected String getToolTip(HalozatAllapot adat) {
        return tooltip;
    }
}
