package archiver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.IOException;

public class ArchiverLauncher {

    @Option(name = "-z", metaVar = "Packing", required = true, forbids = {"-u"}, usage = "Packing file")
    private boolean pack;

    @Option(name = "-u", metaVar = "Unpacking", required = true, forbids = {"-z"}, usage = "Unpacking file")
    private boolean unpack;

    @Option(name = "-out", metaVar = "OutputName", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "InputName", required = true, usage = "Input file name")
    private String inputFileName;

//Command Line: pack-rle [-z|-u] [-out outputname.txt] inputname.txt
    public static void main(String[] args) {
        new ArchiverLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Archiver rle = new Archiver();
        try {
            if (outputFileName == null) outputFileName = inputFileName + "_result";
            if (pack) rle.encode(inputFileName, outputFileName);
                else rle.decode(inputFileName, outputFileName);
            if (pack) System.out.println("Successful packing");
                else System.out.println("Successful unpacking");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}