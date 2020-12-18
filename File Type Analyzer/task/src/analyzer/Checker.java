package analyzer;

import javafx.util.Pair;

import java.util.Map;

public class Checker {
    Pair<String, String> fileType;
    String fileName;
    String searchType;
    Map<String, Integer> map;

    public void setFileType(String dbName) {
        this.map = Utils.loadDB(dbName);
        /*for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }*/
    }

    public void setSearchType(String setSearchType) {
        this.searchType = setSearchType;
    }

    public String getPattern() {
        String p = fileType.getKey();
        p = p.replace("\"", "");
        return p;
    }

    public String getType() {
        String t = fileType.getValue();
        t = t.replace("\"", "");
        return t;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void startCheck(String[] args) {
        setFileName(args[0]);
        setFileType(args[1]);
        MTchecker mt = new MTchecker();
        mt.start(fileName, map);
    }


}
