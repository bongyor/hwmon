package hu.hwmon.dto;

public record HalozatDiff(
    double fogadottSavszelesseg,
    double kuldottSavszelesseg,
    double becsultSzabadSavszelesseg
) {
}