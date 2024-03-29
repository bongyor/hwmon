package hu.hwmon.io.linux;

import hu.hwmon.dto.HalozatAllapot;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HalozatFigyeloTest {

    @Test
    void feldolgoz() {
        var halozatFigyelo = new HalozatFigyelo();
        var now = LocalDateTime.now();
        var allapot = halozatFigyelo.feldolgoz(
            """
                Inter-|   Receive                                                |  Transmit
                 face |bytes    packets errs drop fifo frame compressed multicast|bytes    packets errs drop fifo colls carrier compressed
                    lo:  784712    3217    0    0    0     0          0         0   784712    3217    0    0    0     0       0          0
                enp2s0: 668305910  520084    0    0    0     0          0       581 17363916  215216    0    0    0     0       0          0
                """,
            now
        );

        assertEquals(
            new HalozatAllapot(
                now,
                668305910,
                520084,
                17363916,
                215216
            ),
            allapot
        );
    }
}