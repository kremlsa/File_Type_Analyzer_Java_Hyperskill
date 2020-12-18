package analyzer;

import java.util.ArrayList;


public class Kmp {
    public static int[] prefixFunction(String str) {

        int[] prefixFunc = new int[str.length()];

        for (int i = 1; i < str.length(); i++) {
            int j = prefixFunc[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }
            prefixFunc[i] = j;
        }
        return prefixFunc;
    }

    public static String search(String fileName, String pattern, String type) {

        byte[] data = Utils.readFileB(fileName);
        Long startTime;
        String text  = new String(data);
        startTime = System.nanoTime();
        int[] prefixFunc = prefixFunction(pattern);
        ArrayList<Integer> occurrences = new ArrayList<Integer>();
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }
            if (j == pattern.length()) {
                occurrences.add(i - j + 1);
                j = prefixFunc[j - 1];
                break;
            }
        }
        if (occurrences.size() > 0) {
            //System.out.println(type);
            //System.out.println("It took " + Utils.deltaTime(startTime, System.nanoTime()) + " seconds");
            return type;
        }
        else {
            //System.out.println("Unknown file type");
            //System.out.println("It took " + Utils.deltaTime(startTime, System.nanoTime()) + " seconds");
            return "Unknown file type";
        }

    }
}
