package hu.hwmon.dto;

import java.time.LocalDateTime;

public record MemoriaAllapot(
    LocalDateTime idopont,
    double memOsszes,
    double swapOsszes,
    double memFelhasznalt,
    double swapFelhasznalt,
    double swapCache,
    double swapActiveAnon,
    double buffers,
    double cache,
    double dirty,
    double writeback,
    double avaiable,
    double free
) {
}
