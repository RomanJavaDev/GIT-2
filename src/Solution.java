
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
Читайте с консоли имена файлов, пока не будет введено слово "exit".
Передайте имя файла в нить ReadThread.
Нить ReadThread должна найти байт, который встречается в файле максимальное число раз, и добавить его в словарь resultMap,
где параметр String - это имя файла, параметр Integer - это искомый байт.
Закрыть потоки.
 */

public class Solution {
    public static Map<String, Integer> resultMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String fileName = reader.readLine();
            if (fileName.equals("exit")) {
                break;
            }
            ReadThread thread = new ReadThread(fileName);
            thread.start();
        }
        reader.close();
        for (Map.Entry<String, Integer> pair : resultMap.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }

    }
    public static class ReadThread extends Thread {
        private String fileName = "";
        public ReadThread(String fileName) {
            this.fileName = fileName;
        }
        public void run() {
            try (FileInputStream fis = new FileInputStream(fileName)) {
                ArrayList<Integer> bytes = new ArrayList<>();
                int max = 0;
                int y = -1;
                while (fis.available() > 0) {
                    int data = fis.read();
                    bytes.add(data);
                }
                for (Integer x : bytes) {
                    int count = Collections.frequency(bytes, x);
                    if (count > max) {
                        y = x;
                        max = count;
                    }
                }
                resultMap.put(fileName, y);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
