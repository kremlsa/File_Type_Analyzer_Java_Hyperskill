package analyzer;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {

    private static long charToLong(char ch) {
        return ch - 'A' + 1;
    }

    public static String search(String fileName, String pattern, String type) {
        byte[] data = Utils.readFileB(fileName);
        String text  = new String(data);

            //https://www.geeksforgeeks.org/java-program-for-rabin-karp-algorithm-for-pattern-searching/

            List<Integer> occurrences = new ArrayList<>();
            int d = 53;
            long q = 1_000_000_000 + 9;

            if (text.length() < pattern.length()) {
                if (occurrences.size() > 0) {
                    return type;
                }
                else {
                    return "Unknown file type";
                }
            }

            int M = pattern.length();
            int N = text.length();
            int i, j;
            long p = 0;
            long t = 0;
            long h = 1;

            for (i = 0; i < M - 1; i++)
                h = (h * d) % q;
            for (i = 0; i < M; i++) {
                p = (d * p + pattern.charAt(i)) % q;
                t = (d * t + text.charAt(i)) % q;
            }
            for (i = 0; i <= N - M; i++) {
                if (p == t) {
                    for (j = 0; j < M; j++) {
                        if (text.charAt(i + j) != pattern.charAt(j))
                            break;
                    }
                    if (j == M)
                        occurrences.add(i);
                }
                if (i < N - M) {
                    t = (d * (t - text.charAt(i) * h) + text.charAt(i + M)) % q;
                    if (t < 0)
                        t = (t + q);
                }
            }

        if (occurrences.size() > 0) {
            return type;
        }
        else {
            return "Unknown file type";
        }
    }
}
