package hu.hwmon.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProcesszorAllapot {
    @NonNull LocalDateTime idopont;
    int magok;
    double user;
    double nice;
    double sys;
    double iowait;
    double irq;
    double soft;
    double steal;
    double guest;
    double gnice;
    double idle;
}
