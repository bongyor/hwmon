package hu.hwmon.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProcesszorAllapot {
    @Value
    public static class Szazalekos {
        double system;
        double iowait;
        double user;
        double nice;
        double other;
        double idle;
    }

    @NonNull LocalDateTime idopont;
    double user;
    double nice;
    double system;
    double idle;
    double iowait;
    double irq;
    double softirq;
    double steal;
    double guest;
    double guestNice;

    public ProcesszorAllapot diff(ProcesszorAllapot other) {
        return new ProcesszorAllapot(
            idopont,
            diff(user, other.user),
            diff(nice, other.nice),
            diff(system, other.system),
            diff(idle, other.idle),
            diff(iowait, other.iowait),
            diff(irq, other.irq),
            diff(softirq, other.softirq),
            diff(steal, other.steal),
            diff(guest, other.guest),
            diff(guestNice, other.guestNice)
        );
    }

    public Szazalekos getSzazalekos() {
        var sum = user + nice + system + idle + iowait + irq + softirq + steal + guest + guestNice;
        if (sum == 0) {
            return new Szazalekos(0, 0, 0, 0, 0, 0);
        }
        return new Szazalekos(
            szazalek(sum, system),
            szazalek(sum, iowait),
            szazalek(sum, user),
            szazalek(sum, nice),
            szazalek(sum, irq + softirq + steal + guest + guestNice),
            szazalek(sum, idle)
        );
    }

    private double szazalek(double sum, double current) {
        return (current / sum) * 100;
    }

    private double diff(double a, double b) {
        return Math.abs(a - b);
    }
}
