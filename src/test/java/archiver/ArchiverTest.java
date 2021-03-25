package archiver;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class ArchiverTest {

    @Test
    public void archiver() throws IOException {
        Archiver pack = new Archiver();
        File in = new File("files/input.txt");
        pack.encode("files/input.txt", "files/output.txt");
        pack.decode("files/output.txt", "files/result.txt");
        File out = new File("files/result.txt");
        List<String> c1 = Files.readAllLines(in.toPath());
        List<String> c2 = Files.readAllLines(out.toPath());
        assertTrue(c1.containsAll(c2));
    }
}
