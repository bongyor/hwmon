package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;

import java.awt.*;
import java.awt.image.MemoryImageSource;

public class IconBackend {
    private static SystemTray tray = null;
    private final TrayIcon trayIcon;
    private final MemoryImageSource source;
    private final int[] pixels;
    private final int[][] oszlopok;

    public static IconBackend build() {
        if (!SystemTray.isSupported()) {
            throw new HwMonException("SystemTray is not supported");
        }
        if (tray == null) {
            tray = SystemTray.getSystemTray();
        }
        return new IconBackend();
    }

    public IconBackend() {
        int width = getTrayIconSize().width;
        int height = getTrayIconSize().height;
        int iconPixelBufferSize = width * height;
        pixels = new int[iconPixelBufferSize];
        oszlopok = new int[width][height];
        int value = Color.GRAY.getRGB();
        for (int i = 0; i < iconPixelBufferSize; i++) {
            pixels[i] = value;
        }
        source = new MemoryImageSource(width, height, pixels, 0, width);
        source.setAnimated(true);
        var image = Toolkit.getDefaultToolkit().createImage(source);
        this.trayIcon = new TrayIcon(image);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new HwMonException("TrayIcon could not be added.");
        }
    }

    public Dimension getTrayIconSize() {
        return tray.getTrayIconSize();
    }

    public void setToolTip(String toolTip) {
        trayIcon.setToolTip(toolTip);
    }

    public void addOszlop(int[] newPixels) {
        System.arraycopy(oszlopok, 1, oszlopok, 0, oszlopok.length - 1);
        oszlopok[oszlopok.length - 1] = newPixels;
        oszlopokBeirasa();
        source.newPixels(0, 0, getTrayIconSize().width, getTrayIconSize().height);
    }

    private void oszlopokBeirasa() {
        var transposedOszlopok = new int[getTrayIconSize().height][getTrayIconSize().width];
        for(int pixelIdx=0; pixelIdx < getTrayIconSize().height; pixelIdx++) {
            for(int oszlopIdx=0; oszlopIdx < getTrayIconSize().width; oszlopIdx++) {
                transposedOszlopok[pixelIdx][oszlopIdx] = oszlopok[oszlopIdx][getTrayIconSize().height - pixelIdx - 1];
            }
        }
        int pixelIdx = 0;
        for (int[] sor : transposedOszlopok) {
            for (int pixel : sor) {
                pixels[pixelIdx++] = pixel;
            }
        }
    }
}
