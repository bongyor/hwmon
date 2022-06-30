package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Shell {
    public String call(String command) { return call(command, null); }
    public String call(String command, String[] envp) {
        try {
            var process = Runtime.getRuntime().exec(command, envp);
            process.waitFor(10, TimeUnit.SECONDS);
            if (process.exitValue() != 0) {
                var errorScanner = new Scanner(process.getErrorStream()).useDelimiter("\\A");
                var errorString = errorScanner.hasNext() ? errorScanner.next() : "";
                throw new HwMonException(
                    String.format("Hiba a parancs '%s' futtatása közben: %s", command, errorString)
                );
            } else {
                var stdOutScanner = new Scanner(process.getInputStream()).useDelimiter("\\A");
                return stdOutScanner.hasNext() ? stdOutScanner.next().trim() : "";
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HwMonException(
                String.format("Hiba a parancs '%s' futtatása közben: %s", command, e.getMessage())
            );
        }
    }
}