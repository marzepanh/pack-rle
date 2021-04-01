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
    private File outputFileName;

    @Argument(metaVar = "InputName", required = true, usage = "Input file name")
    private File inputFileName;

//Command Line: pack-rle [-z|-u] [-out outputname.txt] inputname.txt
    public static void main(String[] args) {
        new ArchiverLauncher().launch(args);
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
                if (outputFileName == null)
                    outputFileName = new File(inputFileName.getName().split("\\.")[0] + ".pack");
                if (!outputFileName.getName().split("\\.")[1].equals("pack")) {
                    throw new IOException("File extension .pack is required");
                }
                rle.encode(inputFileName, outputFileName);
            }
            if (unpack) {
                if (outputFileName == null)
                    outputFileName = new File(inputFileName.getName().split("\\.")[0] + "_res.txt");
                rle.decode(inputFileName, outputFileName);
                if (!inputFileName.getName().split("\\.")[1].equals("pack")) {
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
//5. Вынести повторяющийся код в отдельную функцию
// есть ли название с точками в других операционках?
