package hu.hwmon.gui;

import hu.hwmon.dto.HwMonException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TaskbarIkon<T> {
    private Dimension trayIconSize = new Dimension(24, 24);
    private SystemTray tray = null;
    private BufferedImage image = null;
    private TrayIcon trayIcon;

    private void init() {
        tray = SystemTray.getSystemTray();
        trayIconSize = tray.getTrayIconSize();
        image = new BufferedImage(trayIconSize.width, trayIconSize.height, BufferedImage.TYPE_INT_RGB);
        if (!SystemTray.isSupported()) {
            throw new HwMonException("SystemTray is not supported");
        }
        trayIcon = new TrayIcon(image);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new HwMonException("TrayIcon could not be added.");
        }
    }

    private BufferedImage regiMasolasaEltolva() {
        var newImage = new BufferedImage(trayIconSize.width, trayIconSize.height, BufferedImage.TYPE_INT_RGB);
        for (int x = 1; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                newImage.setRGB(x - 1, y, image.getRGB(x, y));
            }
        }
        return newImage;
    }

    public void ujraRajzolas(T adat) {
        if (tray == null) {
            init();
        }
        var newImage = regiMasolasaEltolva();
        var aranyok = formatter(adat);
        var ujOszlop = getPixelszamok(aranyok);
        image = pixelekBerajzolasa(newImage, ujOszlop);
        trayIcon.setImage(image);
        trayIcon.setToolTip(getToolTip(adat));
    }

    protected BufferedImage pixelekBerajzolasa(BufferedImage newImage, int[] ujOszlop) {
        var kovetkezoPixel = 0;
        var oszlopIndex = newImage.getWidth() - 1;
        for (int grafikonSav = szinek().length - 1; grafikonSav >= 0 ; grafikonSav--) {
            var utolsoPixel = kovetkezoPixel + ujOszlop[grafikonSav];
            for (;kovetkezoPixel < utolsoPixel && kovetkezoPixel < newImage.getHeight(); kovetkezoPixel++) {
                newImage.setRGB(oszlopIndex, kovetkezoPixel, szinek()[grafikonSav].getRGB());
            }
        }

        return newImage;
    }

    int[] getPixelszamok(List<Double> aranyok) {
        var summ = aranyok.stream().mapToDouble(Double::doubleValue).sum();
        var egyPixelErteke = summ / trayIconSize.getHeight();
        var pixelek = aranyok
            .stream()
            .map(arany -> Math.round(arany / egyPixelErteke))
            .collect(Collectors.toList());

        if (pixelek.stream().mapToLong(Long::longValue).sum() < trayIconSize.getHeight()) {
            var legnagyobbArany = Collections.max(aranyok);
            var legnagyobbAranyIndex = aranyok.indexOf(legnagyobbArany);
            pixelek.set(legnagyobbAranyIndex, pixelek.get(legnagyobbAranyIndex) + 1);
        } else if (pixelek.stream().mapToLong(Long::longValue).sum() > trayIconSize.getHeight()) {
            var legnagyobbArany = Collections.max(aranyok);
            var legnagyobbAranyIndex = aranyok.indexOf(legnagyobbArany);
            pixelek.set(legnagyobbAranyIndex, pixelek.get(legnagyobbAranyIndex) - 1);
        }

        return pixelek.stream().mapToInt(Long::intValue).toArray();
    }

    protected abstract java.util.List<Double> formatter(T adat);
    protected abstract Color[] szinek();
    protected abstract String getToolTip(T adat);
}
