package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;
import hu.hwmon.gui.Action;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.MemoryImageSource;
import java.util.List;

public class IconBackend {
    private final PixelSzamolo pixelSzamolo;
    private final Color[] szinek;
    private static SystemTray tray = null;
    private final TrayIcon trayIcon;
    private final MemoryImageSource source;
    private final int[] pixels;
    private final int[][] oszlopok;

    public static IconBackend build(Color[] szinekTomb, Action onClickAction) {
        if (!SystemTray.isSupported()) {
            throw new HwMonException("SystemTray is not supported");
        }
        if (tray == null) {
            tray = SystemTray.getSystemTray();
        }
        IconBackend iconBackend = new IconBackend(szinekTomb, onClickAction);
        try {
            tray.add(iconBackend.trayIcon);
        } catch (AWTException e) {
            throw new HwMonException("TrayIcon could not be added.");
        }

        return iconBackend;
    }
    IconBackend(Color[] szinekTomb, Action onClickAction) {
        this.szinek = szinekTomb;
        this.pixelSzamolo = new PixelSzamolo(getTrayIconSize().height);
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
        trayIcon = new TrayIcon(image);
        trayIcon.addMouseListener(
            new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onClickAction.action();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //nem kell
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //nem kell
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //nem kell
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //nem kell
                }
            }
        );
    }

    public void ujraRajzol(List<Double> aranyok) {
        var pixelSzamok = pixelSzamolo.getPixelszamok(aranyok);
        var ujOszlop = getUjOszlop(pixelSzamok);
        addOszlop(ujOszlop);
    }

    private int[] getUjOszlop(int[] pixelSzamok) {
        var oszlop = new int[getTrayIconSize().height];
        var pixelIndex = 0;
        for (int i = 0; i < PixelSzamolo.getAlsoSav(); i++) {
            oszlop[pixelIndex++] = Color.magenta.getRGB();
        }
        for (int i = 0; i < pixelSzamok.length; i++) {
            for (int oszlopMagassag = 0; oszlopMagassag < pixelSzamok[i]; oszlopMagassag++) {
                oszlop[pixelIndex++] = szinek[i].getRGB();
            }
        }
        return oszlop;
    }



    private Dimension getTrayIconSize() {
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
