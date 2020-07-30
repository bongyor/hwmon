package hu.hwmon.io;

import hu.hwmon.dto.MemoriaAllapot;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoriaFigyeloTest {
    @Test
    void feldolgoz() {
        var instance = new MemoriaFigyelo();
        var now = LocalDateTime.now();
        var allapot = instance.feldolgoz(
            "              total        used        free      shared     buffers       cache   available\n" +
                "Mem:          15937        6257        4672         685         743        4262        8664\n" +
                "Swap:         16279           0       16279\n",
            now
        );

        assertEquals(
            new MemoriaAllapot(
                now,
                15937,
                16279,
                6257,
                0,
                685,
                743,
                4262,
                8664,
                4672
            ),
            allapot
        );
    }
}