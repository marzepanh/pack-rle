package archiver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.IOException;

public class ArchiverLauncher {

    @Option(name = "-z", metaVar = "Packing", forbids = {"-u"}, usage = "Packing file")
    private boolean pack;

    @Option(name = "-u", metaVar = "Unpacking", forbids = {"-z"}, usage = "Unpacking file")
    private boolean unpack;

    @Option(name = "-out", metaVar = "OutputName", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "InputName", required = true, usage = "Input file name")
    private String inputFileName; //file

//Command Line: pack-rle [-z|-u] [-out outputname.txt] inputname.txt
    public static void main(String[] args) {
        new ArchiverLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (Exception e) {
            System.out.println(e.getMessage()); //?
        }

        Archiver rle = new Archiver();
        try {
            if (pack) {
                if (outputFileName == null)
                    outputFileName = inputFileName.split("\\.")[0] + "_result.pack";
                if (!outputFileName.split("\\.")[1].equals("pack")) {
                    throw new IOException("File extension .pack is required");
                }
                rle.encode(inputFileName, outputFileName);
            }
            if (unpack) {
                if (outputFileName == null)
                    outputFileName = inputFileName.split("\\.")[0] + "_result.txt";
                rle.decode(inputFileName, outputFileName);
                if (!inputFileName.split("\\.")[1].equals("pack")) {
                    throw new IOException("File extension .pack is required");
                }
            }
            if (pack) System.out.println("Successful packing");
                else System.out.println("Successful unpacking");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Line: pack-rle [-z|-u] [-out outputname.pack] inputname.txt");
        }
    }
}