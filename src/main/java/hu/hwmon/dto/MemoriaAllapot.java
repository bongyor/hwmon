package hu.hwmon.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MemoriaAllapot {
    @NonNull LocalDateTime idopont;
    double memOsszes;
    double swapOsszes;
    double memFelhasznalt;
    double swapFelhasznalt;
    double shared;
    double buffCache;
    double avaiable;
    double free;
}
