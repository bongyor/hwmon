package hu.hwmon.gui.linux;

import hu.hwmon.dto.linux.MemoriaAllapot;
import hu.hwmon.gui.TaskbarIkon;

import java.awt.*;
import java.util.List;


public class MemoriaIkon extends TaskbarIkon<MemoriaAllapot> {
  @Override
  protected String getDiagramErtekTemplate() {
    return "%s (%2$,.0f Mb)";
  }

  @Override
  protected String getTitle() {
    return "Memória használat diagram";
  }

  @Override
  protected List<SavMetaAdatok<MemoriaAllapot>> getSavMetaAdatok() {
    return List.of(
        new SavMetaAdatok<>(
            "Felhasznált anonymus",
            MemoriaAllapot::memFelhasznalt,
            Color.GREEN
        ),
        new SavMetaAdatok<>(
            "Szabad",
            MemoriaAllapot::free,
            Color.BLACK
        ),
        new SavMetaAdatok<>(
            "Cache",
            MemoriaAllapot::cache,
            Color.YELLOW
        ),
        new SavMetaAdatok<>(
            "Buffer",
            MemoriaAllapot::buffers,
            Color.BLUE
        ),
        new SavMetaAdatok<>(
            "Dirty",
            MemoriaAllapot::dirty,
            new Color(255, 0, 0)
        ),
        new SavMetaAdatok<>(
            "Writeback",
            MemoriaAllapot::writeback,
            new Color(159, 76, 17)
        )
    );
  }

  @Override
  protected String getToolTip(MemoriaAllapot adat) {
    return String.format(
        "usr:%2.0f,cache:%2.0f,free:%2.0f,buff:%2.0f,dirt:%2.0f/%2.0f,av:%2.0f",
        adat.memFelhasznalt(),
        adat.cache(),
        adat.free(),
        adat.buffers(),
        adat.dirty(),
        adat.writeback(),
        adat.avaiable()
    );
  }
}
