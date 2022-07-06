package hu.hwmon.gui;

import hu.hwmon.dto.HalozatDiff;

import java.awt.*;
import java.util.List;

public class HalozatIkon extends TaskbarIkon<HalozatDiff> {
  @Override
  protected String getDiagramErtekTemplate() {
    return "%s (%2$,.0f Mbps)";
  }

  @Override
  protected String getTitle() {
    return "Hálózat diagram";
  }

  @Override
  protected List<SavMetaAdatok<HalozatDiff>> getSavMetaAdatok() {
    return List.of(
        new SavMetaAdatok<>(
            "Bejövő sávszélesség",
            HalozatDiff::fogadottSavszelesseg,
            Color.ORANGE
        ),
        new SavMetaAdatok<>(
            "Kimenő sávszélesség",
            HalozatDiff::kuldottSavszelesseg,
            Color.CYAN
        ),
        new SavMetaAdatok<>(
            "Becsült szabad sávszélesség",
            HalozatDiff::becsultSzabadSavszelesseg,
            Color.BLACK
        )
    );
  }

  @Override
  protected String getToolTip(HalozatDiff adat) {
    return String.format(
        "Halozat: r: %2.2f Mbps t: %2.2f Mbps",
        adat.fogadottSavszelesseg(),
        adat.kuldottSavszelesseg()
    );
  }
}
