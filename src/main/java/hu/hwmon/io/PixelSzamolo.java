package hu.hwmon.io;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PixelSzamolo {
    private static final int ALSO_SAV = 2;
    private final int oszlopPixelszam;

    public PixelSzamolo(int oszlopPixelszam) {
        this.oszlopPixelszam = oszlopPixelszam;
    }


    int[] getPixelszamok(List<Double> aranyok) {
        var summ = aranyok.stream().mapToDouble(Double::doubleValue).sum();
        var egyPixelErteke = summ / (oszlopPixelszam - ALSO_SAV);
        var pixelek = aranyok
            .stream()
            .map(arany -> Math.round(arany / egyPixelErteke))
            .collect(Collectors.toList());

        if (pixelek.stream().mapToLong(Long::longValue).sum() < oszlopPixelszam - ALSO_SAV) {
            var legnagyobbArany = Collections.max(aranyok);
            var legnagyobbAranyIndex = aranyok.indexOf(legnagyobbArany);
            pixelek.set(legnagyobbAranyIndex, pixelek.get(legnagyobbAranyIndex) + 1);
        } else if (pixelek.stream().mapToLong(Long::longValue).sum() > oszlopPixelszam - ALSO_SAV) {
            var legnagyobbArany = Collections.max(aranyok);
            var legnagyobbAranyIndex = aranyok.indexOf(legnagyobbArany);
            pixelek.set(legnagyobbAranyIndex, pixelek.get(legnagyobbAranyIndex) - 1);
        }

        return pixelek.stream().mapToInt(Long::intValue).toArray();
    }

}
