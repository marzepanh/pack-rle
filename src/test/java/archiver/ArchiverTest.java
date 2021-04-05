package archiver;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.contentEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArchiverTest {
    private File getFile(String name) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(name);
        return new File(url.toURI());
    }

    private String main(String [] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PrintStream old = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        ArchiverLauncher.main(args);

        System.out.flush();
        System.setOut(old);
        System.out.println(baos.toString());
        return baos.toString();
}

    @Test
    public void archiver() throws IOException, URISyntaxException {
        Archiver pack = new Archiver();
        File in = getFile("input.txt");
        File temp = new File("temp.pack");
        File res = new File("result.txt");

        pack.encode(in, temp);
        pack.decode(temp, res);
        assertTrue(contentEquals(in, res));
        res.delete();
        temp.delete();
    }

    @Test
    public void archiverLauncher() throws IOException, URISyntaxException {
        File in = getFile("input.txt");
        File temp = new File("temp.pack");
        File res = new File("result.txt");
        assertEquals(main("-z -out temp.pack src/main/resources/input.txt".split(" ")),
                "Successful packing" + System.lineSeparator());
        assertEquals(main("-u -out result.txt temp.pack".split(" ")),
                "Successful unpacking" + System.lineSeparator());
        assertTrue(contentEquals(in, res));

        String expected = "File extension .pack is required" + System.lineSeparator()
                + "Command Line: pack-rle [-z|-u] [-out outputname.pack] inputname.txt" +
                System.lineSeparator();

        String actual = main("-z -out temp.rar src/main/resources/input.txt".split(" "));
        assertEquals(expected, actual);


        res.delete();
        temp.delete();
    }
}
