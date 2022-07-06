package hu.hwmon.gui;

import hu.hwmon.dto.ProcesszorAllapot;

import java.awt.*;
import java.util.List;

public class ProcesszorIkon extends TaskbarIkon<ProcesszorAllapot.Szazalekos> {
  @Override
  protected String getDiagramErtekTemplate() {
    return "%s (%3$,.0f%%)";
  }

  @Override
  protected String getTitle() {
    return "Processzor használat diagram";
  }

  @Override
  protected List<SavMetaAdatok<ProcesszorAllapot.Szazalekos>> getSavMetaAdatok() {
    return List.of(
        new SavMetaAdatok<>(
            "Rendszer",
            ProcesszorAllapot.Szazalekos::system,
            Color.BLUE
        ),
        new SavMetaAdatok<>(
            "IO",
            ProcesszorAllapot.Szazalekos::iowait,
            Color.RED
        ),
        new SavMetaAdatok<>(
            "User",
            ProcesszorAllapot.Szazalekos::user,
            Color.GREEN
        ),
        new SavMetaAdatok<>(
            "Nice",
            ProcesszorAllapot.Szazalekos::nice,
            Color.YELLOW
        ),
        new SavMetaAdatok<>(
            "Egyéb",
            ProcesszorAllapot.Szazalekos::other,
            Color.MAGENTA
        ),
        new SavMetaAdatok<>(
            "IDLE",
            ProcesszorAllapot.Szazalekos::idle,
            Color.BLACK
        )
    );
  }

  @Override
  protected String getToolTip(ProcesszorAllapot.Szazalekos adat) {
    return String.format(
        "CPU: %2.2f sys, %2.2f io, %2.2f usr, %2.2f nice, %2.2f other, %2.2f idle",
        adat.system(),
        adat.iowait(),
        adat.user(),
        adat.nice(),
        adat.other(),
        adat.idle()
    );
  }
}
