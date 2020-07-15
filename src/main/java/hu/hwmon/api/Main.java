package hu.hwmon.api;

import hu.hwmon.gui.HalozatIkon;
import hu.hwmon.gui.MemoriaIkon;
import hu.hwmon.gui.ProcesszorIkon;
import hu.hwmon.gui.SwapIkon;
import hu.hwmon.io.HalozatFigyelo;
import hu.hwmon.io.MemoriaFigyelo;
import hu.hwmon.io.ProcesszorFigyelo;
import hu.hwmon.io.VerifyModularization;

public class Main {
    private static final System.Logger logger = System.getLogger("hwmon");

    private static final ProcesszorIkon processzorIkon = new ProcesszorIkon();
    private static final MemoriaIkon memoriaIkon = new MemoriaIkon();
    private static final SwapIkon swapIkon = new SwapIkon();
    private static final HalozatIkon halozatIkon = new HalozatIkon();

    private static final MemoriaFigyelo memoriaFigyelo = new MemoriaFigyelo();
    private static final ProcesszorFigyelo processzorFigyelo = new ProcesszorFigyelo();
    private static final HalozatFigyelo halozatFigyelo = new HalozatFigyelo();

    public static void main(String[] args) {
        VerifyModularization.verify();
        while (true) {
            frissites();
        }
    }

    private static void frissites() {
        try {
            processzorIkon.ujraRajzolas(processzorFigyelo.getAllapot());
            var memoriaAllapot = memoriaFigyelo.getAllapot();
            memoriaIkon.ujraRajzolas(memoriaAllapot);
            swapIkon.ujraRajzolas(memoriaAllapot);
            halozatIkon.ujraRajzolas(halozatFigyelo.getAllapot());
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Hiba a frissítés közben: " + e.getMessage(), e);
        }
    }

}
