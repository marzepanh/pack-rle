package archiver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;

public class ArchiverLauncher {

    @Option(name = "-z", metaVar = "Packing", forbids = {"-u"}, usage = "Packing file")
    private boolean pack;

    @Option(name = "-u", metaVar = "Unpacking", forbids = {"-z"}, usage = "Unpacking file")
    private boolean unpack;

    @Option(name = "-out", metaVar = "OutputName", usage = "Output file name")
    private File outputFile;

    @Argument(metaVar = "InputName", required = true, usage = "Input file name")
    private File inputFile;

//Command Line: pack-rle [-z|-u] [-out outputname.txt] inputname.txt
    public static void main(String[] args) {
        new ArchiverLauncher().launch(args);
    }

    private void foo(String fileName, String extension) throws IOException {
        if (outputFile == null)
            outputFile = new File(inputFile.getName().split("\\.")[0] + extension);
        if (!fileName.split("\\.")[1].equals("pack")) {
            throw new IOException("File extension .pack is required");
        }
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Archiver rle = new Archiver();
        try {
            if (pack) {
                foo(outputFile.getName(), ".pack");
                rle.encode(inputFile, outputFile);
            }
            if (unpack) {
                foo(inputFile.getName(), "_res.txt");
                rle.decode(inputFile, outputFile);
            }
            if (pack) System.out.println("Successful packing");
                else System.out.println("Successful unpacking");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Line: pack-rle [-z|-u] [-out outputname.pack] inputname.txt");
        }
    }
}