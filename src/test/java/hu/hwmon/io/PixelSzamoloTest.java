package hu.hwmon.io;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PixelSzamoloTest {

    @Test
    void getPixelszamok() {
        var ikon = new PixelSzamolo(24);
        var pixelSzamok = ikon.getPixelszamok(Arrays.asList(333.0, 666.0, 999.0));
        assertArrayEquals(
            new int[] {4, 7, 11},
            pixelSzamok
        );
        assertEquals(22, Arrays.stream(pixelSzamok).sum());

        assertArrayEquals(
            new int[] {0, 0, 4, 9, 9},
            ikon.getPixelszamok(Arrays.asList(0.0, 1.0, 100.0, 200.0, 200.0))
        );

    }
}