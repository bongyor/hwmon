package hu.hwmon.io;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PixelSzamolo {
    private static int alsoSav = 2;
    private final int oszlopPixelszam;

    public static void setAlsoSav(int alsoSav) {
        PixelSzamolo.alsoSav = alsoSav;
    }

    public static int getAlsoSav() {
        return alsoSav;
    }
    public PixelSzamolo(int oszlopPixelszam) {
        this.oszlopPixelszam = oszlopPixelszam;
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

}
