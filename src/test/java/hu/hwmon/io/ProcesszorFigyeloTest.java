package hu.hwmon.io;

import hu.hwmon.dto.ProcesszorAllapot;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProcesszorFigyeloTest {

    @Test
    void feldolgoz() {
        var instance = new ProcesszorFigyelo();
        var now = LocalDateTime.now();
        assertEquals(
            new ProcesszorAllapot(
                now,
                8,
                8.97,
                0.01,
                1.96,
                0.08,
                0.00,
                0.24,
                0.00,
                0.00,
                0.00,
                88.74
            ),
            instance.feldolgoz(
                "Linux 4.15.0-109-generic (bongyor-magnet) \t07/11/20 \t_x86_64_\t(8 CPU)\n" +
                    "\n" +
                    "19:50:29     CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle\n" +
                    "19:50:29     all    8.97    0.01    1.96    0.08    0.00    0.24    0.00    0.00    0.00   88.74\n" +
                    "19:50:29       0    8.75    0.02    1.89    0.06    0.00    0.42    0.00    0.00    0.00   88.87\n" +
                    "19:50:29       1    8.98    0.01    2.09    0.07    0.00    0.25    0.00    0.00    0.00   88.60\n" +
                    "19:50:29       2    9.28    0.01    1.85    0.06    0.00    0.14    0.00    0.00    0.00   88.66\n" +
                    "19:50:29       3    8.75    0.01    1.73    0.06    0.00    0.09    0.00    0.00    0.00   89.36\n" +
                    "19:50:29       4    8.61    0.01    1.66    0.07    0.00    0.06    0.00    0.00    0.00   89.59\n" +
                    "19:50:29       5    8.86    0.01    1.67    0.07    0.00    0.05    0.00    0.00    0.00   89.34\n" +
                    "19:50:29       6    9.99    0.01    2.88    0.19    0.00    0.74    0.00    0.00    0.00   86.19\n" +
                    "19:50:29       7    8.53    0.02    1.94    0.07    0.00    0.16    0.00    0.00    0.00   89.28",
                now
            )
        );
    }
}