package hu.hwmon.gui;

import hu.hwmon.io.IconBackend;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TaskbarIkon<T> {
    private IconBackend iconBackend;
    private int oszlopPixelszam = 24;
    private final int alsoSav = 2;

    private void init() {
        iconBackend = IconBackend.build();
        oszlopPixelszam = iconBackend.getTrayIconSize().height;
    }

    public void ujraRajzolas(T adat) {
        if (iconBackend == null) {
            init();
        }
        var aranyok = formatter(adat);
        var pixelSzamok = getPixelszamok(aranyok);
        var ujOszlop = getUjOszlop(pixelSzamok);
        iconBackend.addOszlop(ujOszlop);
        iconBackend.setToolTip(getToolTip(adat));
    }

    private int[] getUjOszlop(int[] pixelSzamok) {
        var oszlop = new int[iconBackend.getTrayIconSize().height];
        var pixelIndex = 0;
        for (int i = 0; i < alsoSav; i++) {
            oszlop[pixelIndex++] = Color.magenta.getRGB();
        }
        for (int i = 0; i < pixelSzamok.length; i++) {
            for (int oszlopMagassag = 0; oszlopMagassag < pixelSzamok[i]; oszlopMagassag++) {
                oszlop[pixelIndex++] = szinek()[i].getRGB();
            }
        }
        return oszlop;
    }

    int[] getPixelszamok(List<Double> aranyok) {
        var summ = aranyok.stream().mapToDouble(Double::doubleValue).sum();
        var egyPixelErteke = summ / (oszlopPixelszam - alsoSav);
        var pixelek = aranyok
            .stream()
            .map(arany -> Math.round(arany / egyPixelErteke))
            .collect(Collectors.toList());

        if (pixelek.stream().mapToLong(Long::longValue).sum() < oszlopPixelszam - alsoSav) {
            var legnagyobbArany = Collections.max(aranyok);
            var legnagyobbAranyIndex = aranyok.indexOf(legnagyobbArany);
            pixelek.set(legnagyobbAranyIndex, pixelek.get(legnagyobbAranyIndex) + 1);
        } else if (pixelek.stream().mapToLong(Long::longValue).sum() > oszlopPixelszam - alsoSav) {
            var legnagyobbArany = Collections.max(aranyok);
            var legnagyobbAranyIndex = aranyok.indexOf(legnagyobbArany);
            pixelek.set(legnagyobbAranyIndex, pixelek.get(legnagyobbAranyIndex) - 1);
        }

        return pixelek.stream().mapToInt(Long::intValue).toArray();
    }

    protected abstract java.util.List<Double> formatter(T adat);
    protected abstract Color[] szinek();
    protected abstract String getToolTip(T adat);
}
