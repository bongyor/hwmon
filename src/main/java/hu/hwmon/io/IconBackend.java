package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;

import java.awt.*;

public class IconBackend {
    private static SystemTray tray = null;
    private final TrayIcon trayIcon;

    public static IconBackend build(Image image) {
        if (!SystemTray.isSupported()) {
            throw new HwMonException("SystemTray is not supported");
        }
        if (tray == null) {
            tray = SystemTray.getSystemTray();
        }
        return new IconBackend(image);
    }

    public IconBackend(Image image) {
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

    public void setImage(Image image) {
        trayIcon.setImage(image);
    }
}
