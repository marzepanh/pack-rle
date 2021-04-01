package archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Archiver {
    // AAABAB
    //  repetitive part: 3A. positive byte of length
    // not-repetitive: 3BAB. negative byte of length

    public void encode(File inputFileName, File outputFileName) throws IOException {
        try (FileInputStream in = new FileInputStream(inputFileName);
             FileOutputStream out = new FileOutputStream(outputFileName)) {
                int rLength = 1;
                int nrLength = 0;
                int current = in.read();
                int next = in.read();
                ArrayList<Integer> buffer = new ArrayList<>();
                final int MAX_LENGTH = 127;

                while (current != -1) {
                    if (current == next) {
                        if (nrLength != 0) {
                            out.write(128 + nrLength);
                            for (Integer element: buffer) out.write(element);
                            nrLength = 0;
                            buffer.clear();
                        }
                        rLength++;
                        if (rLength == MAX_LENGTH) {
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
                        if (nrLength == MAX_LENGTH) {
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
                if (nrLength != 0) {
                    out.write(128 + nrLength);
                    for (Integer element: buffer) out.write(element);
                }
            }
        }

    public void decode(File inputFileName, File outputFileName) throws IOException {
        try (FileInputStream in = new FileInputStream(inputFileName);
             FileOutputStream out = new FileOutputStream(outputFileName)) {
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
}