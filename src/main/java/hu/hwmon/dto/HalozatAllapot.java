package hu.hwmon.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class HalozatAllapot {
    @NonNull LocalDateTime idopont;
    long receiveBytes;
    long receivePackets;
    long transmitBytes;
    long transmitPackets;

    public HalozatAllapot add(HalozatAllapot other) {
        return new HalozatAllapot(
            idopont,
            receiveBytes + other.receiveBytes,
            receivePackets + other.receivePackets,
            transmitBytes + other.transmitBytes,
            transmitPackets + other.transmitPackets
        );
    }

    public HalozatAllapot diff(HalozatAllapot other) {
        return new HalozatAllapot(
            idopont,
            Math.abs(receiveBytes - other.receiveBytes),
            Math.abs(receivePackets - other.receivePackets),
            Math.abs(transmitBytes - other.transmitBytes),
            Math.abs(transmitPackets - other.transmitPackets)
        );
    }
}
