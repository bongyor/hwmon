package hu.hwmon.gui.linux;

import hu.hwmon.dto.linux.MemoriaAllapot;
import hu.hwmon.gui.TaskbarIkon;

import java.awt.*;
import java.util.List;


public class SwapIkon extends TaskbarIkon<MemoriaAllapot> {
    @Override
    protected String getDiagramErtekTemplate() {
        return "%s (%2$,.0f Mb)";
    }

    @Override
    protected String getTitle() {
        return "Swap diagram";
    }

    @Override
    protected List<SavMetaAdatok<MemoriaAllapot>> getSavMetaAdatok() {
        return List.of(
            new SavMetaAdatok<>(
                "Aktív",
                MemoriaAllapot::swapActiveAnon,
                Color.RED
            ),
            new SavMetaAdatok<>(
                "Inaktív",
                MemoriaAllapot::swapInactive,
                new Color(50, 110, 255)
            ),
            new SavMetaAdatok<>(
                "Swap cached",
                MemoriaAllapot::swapCache,
                new Color(200, 255, 0)
            ),
            new SavMetaAdatok<>(
                "Szabad",
                MemoriaAllapot::swapFree,
                Color.BLACK
            )
        );
    }


    @Override
    protected String getToolTip(MemoriaAllapot adat) {
        return String.format(
            "SWAP: %2.0f (inactive: %2.0f, cache:  %2.0f)/%2.0f",
            adat.swapActiveAnon(),
            adat.swapInactive(),
            adat.swapCache(),
            adat.swapOsszes()
        );
    }
}
