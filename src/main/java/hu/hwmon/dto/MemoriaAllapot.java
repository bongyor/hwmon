package hu.hwmon.dto;

import java.time.LocalDateTime;

public record MemoriaAllapot(
    LocalDateTime idopont,
    double memOsszes,
    double swapOsszes,
    double memFelhasznalt,
    double swapFelhasznalt,
    double shared,
    double buffers,
    double cache,
    double avaiable,
    double free
) {
}
