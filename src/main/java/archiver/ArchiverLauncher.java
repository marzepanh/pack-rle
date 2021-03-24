package archiver;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class ArchiverLauncher {

    @Option(name = "-z", metaVar = "Packing", required = true, forbids = {"-u"}, usage = "Packing file")
    private boolean pack;

    @Option(name = "-u", metaVar = "Unpacking", required = true, forbids = {"-z"}, usage = "Unpacking file")
    private boolean unpack;

    @Option(name = "-out", metaVar = "OutputName", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "InputName", required = true, usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new ArchiverLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
