package hu.hwmon.io;

import hu.hwmon.dto.MemoriaAllapot;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoriaFigyeloTest {
  @Test
  void feldolgoz() {
    var instance = new MemoriaFigyelo();
    var now = LocalDateTime.now();
    var allapot = instance.feldolgoz(
        """
            MemTotal:       15166044 kB
            MemFree:          955888 kB
            MemAvailable:    3714356 kB
            Buffers:           42400 kB
            Cached:          4035500 kB
            SwapCached:        70484 kB
            Active:          1989180 kB
            Inactive:       11317900 kB
            Active(anon):     140552 kB
            Inactive(anon): 10263808 kB
            Active(file):    1848628 kB
            Inactive(file):  1054092 kB
            Unevictable:          32 kB
            Mlocked:              32 kB
            SwapTotal:       1003516 kB
            SwapFree:         585468 kB
            Dirty:            213940 kB
            Writeback:            12 kB
            AnonPages:       9160024 kB
            Mapped:          2851544 kB
            Shmem:           1175384 kB
            KReclaimable:     188052 kB
            Slab:             365956 kB
            SReclaimable:     188052 kB
            SUnreclaim:       177904 kB
            KernelStack:       26700 kB
            PageTables:        72332 kB
            NFS_Unstable:          0 kB
            Bounce:                0 kB
            WritebackTmp:          0 kB
            CommitLimit:     8586536 kB
            Committed_AS:   20315092 kB
            VmallocTotal:   34359738367 kB
            VmallocUsed:       57280 kB
            VmallocChunk:          0 kB
            Percpu:            15552 kB
            HardwareCorrupted:     0 kB
            AnonHugePages:      2048 kB
            ShmemHugePages:        0 kB
            ShmemPmdMapped:        0 kB
            FileHugePages:         0 kB
            FilePmdMapped:         0 kB
            HugePages_Total:       0
            HugePages_Free:        0
            HugePages_Rsvd:        0
            HugePages_Surp:        0
            Hugepagesize:       2048 kB
            Hugetlb:               0 kB
            DirectMap4k:     1047360 kB
            DirectMap2M:    14495744 kB
            DirectMap1G:           0 kB
            """,
        now
    );

    assertEquals(
        new MemoriaAllapot(
            now,
            14810.58984375,
            979.99609375,
            9894.78125,
            339.41796875,
            68.83203125,
            0,
            41.40625,
            3731.98046875,
            208.92578125,
            0.01171875,
            3627.30078125,
            933.484375
        ),
        allapot
    );
    assertEquals(
        allapot.memOsszes(),
        allapot.memFelhasznalt() +
            allapot.cache() +
            allapot.dirty() +
            allapot.writeback() +
            allapot.free() +
            allapot.buffers(),
        1.0
    );
  }
}