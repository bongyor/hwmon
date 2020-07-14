package hu.hwmon.io;

import hu.hwmon.dto.HwMonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShellTest {

    @Test
    void call() {
        var shell = new Shell();
        assertEquals("valami", shell.call("echo valami", null));
        assertThrows(HwMonException.class, () -> shell.call("hülyeség", null));
    }
}