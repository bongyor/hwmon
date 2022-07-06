package hu.hwmon.gui;

import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Chart3DFactory;
import org.jfree.chart3d.Chart3DPanel;
import org.jfree.chart3d.TitleAnchor;
import org.jfree.chart3d.data.PieDataset3D;
import org.jfree.chart3d.graphics3d.swing.DisplayPanel3D;
import org.jfree.chart3d.label.StandardPieLabelGenerator;
import org.jfree.chart3d.plot.PiePlot3D;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class Diagram extends JFrame {
  public static final Dimension DEFAULT_CONTENT_SIZE = new Dimension(500, 400);
  private final transient Supplier<PieDataset3D<String>> dataSupplier;
  private final PiePlot3D plot;

  public void update() {
    plot.setDataset(dataSupplier.get());
  }

  public Diagram(
      String title,
      Supplier<PieDataset3D<String>> dataSupplier,
      Color[] szinek,
      String diagramErtekTemplate
  ) {
    super(title);
    this.dataSupplier = dataSupplier;
    JPanel content = new JPanel(new BorderLayout());
    content.setPreferredSize(DEFAULT_CONTENT_SIZE);
    PieDataset3D<String> dataset = dataSupplier.get();
    final Chart3D chart = Chart3DFactory.createPieChart(
        title,
        "A projekt honlapja: https://github.com/bongyor/hwmon",
        dataset
    );
    chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
    PiePlot3D plotLocal = (PiePlot3D) chart.getPlot();
    plotLocal.setLegendLabelGenerator(new StandardPieLabelGenerator(diagramErtekTemplate));
    feketeSzinCserejeFeherreMertJobbanNezKi(szinek);
    plotLocal.setSectionColors(szinek);
    Chart3DPanel chartPanel = new Chart3DPanel(chart);
    chartPanel.setMargin(0.15);
    chartPanel.zoomToFit(DEFAULT_CONTENT_SIZE);
    content.add(new DisplayPanel3D(chartPanel));
    plot = (PiePlot3D) chart.getPlot();
    getContentPane().add(content);
  }

  private void feketeSzinCserejeFeherreMertJobbanNezKi(Color[] szinek) {
    for (int i = 0; i < szinek.length; i++) {
      if (szinek[i].equals(Color.BLACK)) {
        szinek[i] = Color.WHITE;
      }
    }
  }
}