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
        File in = new File("files/input.txt"); //maven resource folder, default maven layout
        pack.encode("files/input.txt", "files/output.txt");
        pack.decode("files/output.txt", "files/result.txt");
        File res = new File("files/result.txt");
        File out = new File("files/output.txt");
        List<String> c1 = Files.readAllLines(in.toPath());
        List<String> c2 = Files.readAllLines(res.toPath());
        assertTrue(c1.containsAll(c2));
        res.delete();
        out.delete();
    }

}
