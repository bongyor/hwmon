package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Induláskor lefutó ellenőrzés a Jigsaw átállás idejére.
 * Jelenleg néhány, a modularizálás állapotával kapcsolatos statisztikát jelenít csak meg a logban.
 */
public class VerifyModularization {
    private static final System.Logger logger = System.getLogger("hwmon");

    private static final boolean MODULARIZATION_EXCEPTIONS = false;

    private VerifyModularization() {}

    public static void verify() {
        final int modules = ModuleLayer.boot().modules().size();
        final List<String> autoModules = getAutoModules();
        Collections.sort(autoModules);
        logger.log(
            System.Logger.Level.INFO,
            String.format(
                "Modules: %s, Auto modules: %s (%s)",
                modules,
                autoModules.size(),
                (int) ((autoModules.size() / (float) modules) * 100)
            )
        );
        if (!autoModules.isEmpty()) {
            final String message = String.format("Automatic modules: %s", String.join(", ", autoModules));
            if (MODULARIZATION_EXCEPTIONS) {
                throw new HwMonException(message);
            } else {
                logger.log(System.Logger.Level.INFO, message);
            }
        }
    }


    private static List<String> getAutoModules() {
        return ModuleLayer.boot().modules().stream()
            .filter(m -> m.getDescriptor().isAutomatic())
            .map(Module::getName)
            .collect(Collectors.toList());
    }
}