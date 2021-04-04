package archiver;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.apache.commons.io.FileUtils.contentEquals;
import static org.junit.Assert.assertTrue;

public class ArchiverTest {
/*    private File getFile(String name) throws URISyntaxException {
        URL url =  getClass().getResource(name);
        return new File(url.toURI());
    }*/
    private void main(String [] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PrintStream old = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        ArchiverLauncher.main(args);

        System.out.flush();
        System.setOut(old);
        System.out.println("" + baos.toString());
}

    @Test
    public void archiver() throws IOException {
        Archiver pack = new Archiver();

        File temp = new File("src/main/resources/temp.pack");
        File res = new File("src/main/resources/result.txt");
        File in = new File("src/main/resources/input.txt");
        pack.encode(in, temp);
        pack.decode(temp, res);
        assertTrue(contentEquals(in, res));
        res.delete();
        temp.delete();
    }

    @Test
    public void archiverLauncher() throws IOException {
        File in = new File("src/main/resources/input.txt");
        File temp = new File("temp.pack");
        File res = new File("result.txt");
        main("-z -out temp.pack src/main/resources/input.txt".split(" "));
        main("-u -out result.txt temp.pack".split(" "));
        assertTrue(contentEquals(in, res));
        res.delete();
        temp.delete();
    }
}
