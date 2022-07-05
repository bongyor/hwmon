package hu.hwmon.dto;

import java.time.LocalDateTime;

public record MemoriaAllapot(
    LocalDateTime idopont,
    double memOsszes,
    double swapOsszes,
    double memFelhasznalt,
    double swapInactive,
    double swapCache,
    double swapActiveAnon,
    double swapFree,
    double buffers,
    double cache,
    double dirty,
    double writeback,
    double avaiable,
    double free
) {
}
