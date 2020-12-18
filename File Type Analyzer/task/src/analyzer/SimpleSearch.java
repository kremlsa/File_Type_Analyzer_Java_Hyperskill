package analyzer;


public class SimpleSearch {

    public static void search(String fileName, String pattern, String type) {
        byte[] data = Utils.readFileB(fileName);
        Long startTime;
        String body = new String(data);
        startTime = System.nanoTime();
        if (containsPattern(body, pattern)) {
            System.out.println(type);
            System.out.println("It took " + Utils.deltaTime(startTime, System.nanoTime()) + " seconds");
        } else {
            System.out.println("Unknown file type");
            System.out.println("It took " + Utils.deltaTime(startTime, System.nanoTime()) + " seconds");
        }
    }

    public static boolean containsPattern(String text, String pattern) {
        if (text.length() < pattern.length()) {
            return false;
        }

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            boolean patternIsFound = true;

            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    patternIsFound = false;
                    break;
                }
            }

            if (patternIsFound) {
                return true;
            }
        }

        return false;
    }
}