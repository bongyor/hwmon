package hu.hwmon.gui.linux;

import hu.hwmon.dto.linux.MemoriaAllapot;
import hu.hwmon.gui.TaskbarIkon;

import java.awt.*;
import java.util.List;


public class MemoriaAktivitasIkon extends TaskbarIkon<MemoriaAllapot> {
  @Override
  protected String getDiagramErtekTemplate() {
    return "%s (%2$,.0f Mb)";
  }

  @Override
  protected String getTitle() {
    return "Memória aktivitás diagram";
  }

  @Override
  protected List<SavMetaAdatok<MemoriaAllapot>> getSavMetaAdatok() {
    return List.of(
        new SavMetaAdatok<>(
            "Aktív anonymus",
            MemoriaAllapot::activeAnon,
            new Color(5, 106, 5)
        ),
        new SavMetaAdatok<>(
            "Inaktív anonymus",
            MemoriaAllapot::inactiveAnon,
            new Color(50, 255, 153, 255)
        ),
        new SavMetaAdatok<>(
            "Szabad",
            MemoriaAllapot::free,
            Color.BLACK
        ),
        new SavMetaAdatok<>(
            "Inaktív file",
            MemoriaAllapot::inactiveFile,
            new Color(0, 216, 225)
        ),
        new SavMetaAdatok<>(
            "Aktív File",
            MemoriaAllapot::activeFile,
            Color.ORANGE
        )
    );
  }

  @Override
  protected String getToolTip(MemoriaAllapot adat) {
    return String.format(
        "Anonymus: %2.0f (%2.0f), File: %2.0f (%2.0f)",
        adat.activeAnon(),
        adat.inactiveAnon(),
        adat.activeFile(),
        adat.inactiveFile()
    );
  }
}
