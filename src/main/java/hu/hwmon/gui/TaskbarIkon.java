package hu.hwmon.gui;

import hu.hwmon.io.IconBackend;
import org.jfree.chart3d.data.PieDataset3D;
import org.jfree.chart3d.data.StandardPieDataset3D;

import java.awt.*;
import java.util.List;
import java.util.function.Function;

public abstract class TaskbarIkon<T> {
  public record SavMetaAdatok<T>(
      String megnevezes,
      Function<T, Double> getter,
      Color szin
  ) {
  }

  private IconBackend iconBackend;

  private List<SavMetaAdatok<T>> savMetaAdatok;

  private Diagram diagram;

  private T lastData;

  private void init() {
    savMetaAdatok = getSavMetaAdatok();
    iconBackend = IconBackend.build(getSzinekTomb(), this::toggleDiagram);
  }

  private void toggleDiagram() {
    if (diagram == null) {
      diagram = new Diagram(
          getTitle(),
          this::getDiagramData,
          getSzinekTomb(),
          getDiagramErtekTemplate()
      );
      diagram.pack();
      diagram.setVisible(true);
    } else {
      diagram.setVisible(!diagram.isVisible());
    }
  }

  protected abstract String getDiagramErtekTemplate();

  protected abstract String getTitle();

  private PieDataset3D<String> getDiagramData() {
    StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();
    savMetaAdatok.forEach(
        meta -> dataset.add(meta.megnevezes(), meta.getter().apply(lastData))
    );

    return dataset;
  }
  private Color[] getSzinekTomb() {
    Color[] colors = new Color[savMetaAdatok.size()];
    for (int i = 0; i < savMetaAdatok.size(); i++) {
      colors[i] = savMetaAdatok.get(i).szin();
    }
    return colors;
  }

  protected abstract List<SavMetaAdatok<T>> getSavMetaAdatok();

  public void ujraRajzolas(T adat) {
    lastData = adat;
    if (iconBackend == null) {
      init();
    }
    iconBackend.ujraRajzol(getAranyok(adat));
    iconBackend.setToolTip(getToolTip(adat));
    diagramUjrarajzolasaHaSzukseges();
  }

  private void diagramUjrarajzolasaHaSzukseges() {
    if (diagram != null && diagram.isVisible()) {
      diagram.update();
    }
  }

  private List<Double> getAranyok(T adat) {
    return savMetaAdatok
        .stream()
        .map(meta -> meta.getter.apply(adat))
        .toList();
  }


  protected abstract String getToolTip(T adat);
}
