package archiver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RLE {
    public static void main(String[] args) throws IOException {

        RLE pack =  new RLE();
        pack.encode("src/main/java/archiver/input.txt",
                "src/main/java/archiver/output.txt");
        pack.decode(
                "src/main/java/archiver/output.txt",
                "src/main/java/archiver/result.txt");
    }
    // AAABAB
    //  repetitive part: 3A. positive byte of length
    // not-repetitive: 3BAB. negative byte of length

    public boolean encode(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream in = new FileInputStream(inputFileName)) {
            try (FileOutputStream out = new FileOutputStream(outputFileName)) {
                int rLength = 1;
                int nrLength = 0;
                int current = in.read();
                int next = in.read();
                ArrayList<Integer> buffer = new ArrayList<>();

                while (current != -1) {
                    if (current == next) {
                        if (nrLength != 0) {
                            out.write(128 + nrLength);
                            for (Integer element: buffer) out.write(element);
                            nrLength = 0;
                            buffer.clear();
                        }
                        rLength++;
                        //length <= 127
                        if (rLength == 127) {
                            out.write(rLength);
                            out.write(current);
                            rLength = 1;
                            current = in.read();
                            next = in.read();
                            continue;
                        }
                    }
                    if (current != next && rLength != 1) {
                        out.write(rLength);
                        out.write(current);
                        rLength = 1;
                        current = next;
                        next = in.read();
                        continue;
                    }
                    if (current != next) {
                        nrLength++;
                        buffer.add(current);
                        //nrLength == 127
                        if (nrLength == 127) {
                            out.write(128 + nrLength);
                            for (Integer element: buffer) out.write(element);
                            buffer.clear();
                            nrLength = 0;
                            current = next;
                            next = in.read();
                            continue;
                        }
                    }
                    current = next;
                    next = in.read();
                }
                //clean buffer
/*                if (rLength != 1) {
                    out.write(rLength);
                    out.write(current);
                }*/
                if (nrLength != 0) {
                    out.write(128 + nrLength);
                    for (Integer element: buffer) out.write(element);
                }
            }
        }
        return true;
    }

    public boolean decode(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream in = new FileInputStream(inputFileName)) {
            try (FileOutputStream out = new FileOutputStream(outputFileName)) {
                while (in.available() > 0) {
                    int len = in.read();
                    if (len < 128) {
                        int b = in.read();
                        for (int i = 1; i <= len; i++) out.write(b);
                    }
                    if (len > 128) {
                        for (int i = 1; i <= len - 128; i++) out.write(in.read());
                    }
                }
            }
        }
        return true;
    }
}
/*

*/


/*public static String encode(String input) {
        StringBuilder result = new StringBuilder();
        int rLength = 1;
        int nrLength = 0;
        StringBuilder nrBuffer = new StringBuilder();
        for (int i = 0; i < input.length() - 1; i++) {
            boolean nextIsEquals = input.charAt(i) == input.charAt(i + 1);
            if (nextIsEquals) {
                if (nrLength != 0) {
                    result.append(-1 * nrLength);
                    result.append(nrBuffer);
                    nrBuffer.setLength(0); //clear nrBuffer
                    nrLength = 0;
                }
                rLength++;
            }
            if (!nextIsEquals && rLength != 1) {
                result.append(rLength);
                result.append(input.charAt(i));
                rLength = 1;
                continue;
            }
            if (!nextIsEquals) {
                nrLength++;
                nrBuffer.append(input.charAt(i));
                }
            }
        char last = input.charAt(input.length() - 1);
        if (rLength != 1) result.append(rLength).append(last);
        if (nrLength != 0) result.append(-1 * (nrLength + 1)).append(nrBuffer.append(last));

        return result.toString();
    }*/