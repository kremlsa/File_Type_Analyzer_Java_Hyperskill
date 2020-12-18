package analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MTchecker {
    ExecutorService executor;
    ConcurrentLinkedQueue<String> processingQueue = new ConcurrentLinkedQueue<>();
    public List<String> results = new CopyOnWriteArrayList<>();

    public void addResult (String result) {
        results.add(result);
    }

    public void printResults() {
        results.forEach(x -> System.out.println(x));
    }

    public void start(String path, Map<String, Integer> map) {
        String[] pathnames;
        File f = new File(path);
        pathnames = f.list();
        for (String s : pathnames) {
            processingQueue.offer(s);
        }
        checking(processingQueue, path, map);
    }




    public void checking(ConcurrentLinkedQueue<String> processingQueue, String path, Map<String, Integer> map) {
        List<Future<Boolean>> futures = new ArrayList<>();
        executor = Executors.newFixedThreadPool(processingQueue.size());
        boolean result;
        List<Callable<Boolean>> callables = new ArrayList<>();

        while (!processingQueue.isEmpty()) {
            callables.add(new CheckerThread(processingQueue.poll(), path, map, this));
        }
        try {
            futures = executor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future<Boolean> future : futures) {
                try {
                    result = future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        for (Future<Boolean> future : futures) {
            future.cancel(true);
        }
        executor.shutdown();
        printResults();
    }

}

class CheckerThread implements Callable<Boolean> {
    String name;
    String path;
    MTchecker mt;
    Map<String, Integer> map;

    public CheckerThread(String name, String path, Map<String, Integer> map, MTchecker mt){
        this.path = path;
        this.map = map;
        this.name = name;
        this.mt = mt;

    }
    @Override
    public Boolean call() {
        String result = "Unknown file type";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String pattern = entry.getKey().split(";")[0];
            pattern = pattern.replace("\"", "");
            String type = entry.getKey().split(";")[1];
            type = type.replace("\"", "");
            //result = Kmp.search(path + "/" + name, pattern, type);
            result = RabinKarp.search(path + "/" + name, pattern, type);
            if (!result.equals("Unknown file type")) {
                break;
            }
        }
        mt.addResult(name + ": " + result);

        return true;
    }

}
