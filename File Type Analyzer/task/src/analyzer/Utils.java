package analyzer;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    private static DecimalFormat df4 = new DecimalFormat("#.#####");

    public static byte[] readFileB(String filepath) {
        byte[] data;
        Path path = Paths.get(filepath);
        if (Files.exists(path) && !Files.isDirectory(path)) {
            try {
                data = Files.readAllBytes(path);
                return data;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data = new byte[0];
        return data;
    }

    public static String deltaTime(Long start, Long end) {
        Long delta = end - start;
        return df4.format(((double) delta / 1_000_000_000));
    }

    public static Map<String, Integer> loadDB(String filePath) {

        Path path = FileSystems.getDefault().getPath(filePath);
        Map<String, Integer> patternDb = null;
        try {
            patternDb = Files.lines(path)
                    .collect(Collectors.toMap(k -> k.split(";")[1] + ";" + k.split(";")[2], v -> Integer.parseInt(v.split(";")[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortByValue(patternDb);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

}
