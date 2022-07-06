package hu.hwmon.io;

import java.util.regex.Pattern;

public class OsDetect {
  public enum OS {
    LINUX,
    WINDOWS,
    MACOS
  }

  public static OS detect() {
    var osName = System.getProperty("os.name");
    if (Pattern.compile("^linux.*$", Pattern.CASE_INSENSITIVE).matcher(osName).matches()) {
      return OS.LINUX;
    }
    if (Pattern.compile("^windows.*$", Pattern.CASE_INSENSITIVE).matcher(osName).matches()) {
      return OS.WINDOWS;
    }
    return OS.MACOS;
  }
}
