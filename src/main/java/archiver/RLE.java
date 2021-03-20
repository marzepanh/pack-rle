package archiver;

public class RLE {

    public static String encode(String input) {
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
    }

}
