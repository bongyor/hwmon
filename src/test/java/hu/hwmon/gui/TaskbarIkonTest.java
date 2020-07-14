package hu.hwmon.gui;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TaskbarIkonTest {

    @Test
    void getPixelszamok() {
        var ikon = new MemoriaIkon();

        var pixelSzamok = ikon.getPixelszamok(Arrays.asList(333.0, 666.0, 999.0));
        assertArrayEquals(
            new int[] {4, 8, 12},
            pixelSzamok
        );
        assertEquals(TaskbarIkon.PIXELSZAM, Arrays.stream(pixelSzamok).sum());

        assertArrayEquals(
            new int[] {0, 0, 5, 9, 10},
            ikon.getPixelszamok(Arrays.asList(0.0, 1.0, 100.0, 200.0, 200.0))
        );

    }
}