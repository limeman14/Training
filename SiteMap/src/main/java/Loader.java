import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader
{
    private static final int TABS_SUBTRAHEND = 3;
    final static String URL = "https://skillbox.ru/";

    @SneakyThrows
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool.commonPool().invoke(new MapCreator(URL));

        ArrayList<String> urlsToFile = new ArrayList<>();

        //Добавление табуляции
        Pattern pattern = Pattern.compile("/");
        for (String element : URLStorage.uniqueUrlSet) {
            int counter = 0;
            Matcher matcher = pattern.matcher(element);
            while (matcher.find()) {
                counter++;
            }
            urlsToFile.add(addTabs(counter) + element);
        }

        //запись в файл
        PrintWriter writer = new PrintWriter("file.txt");
        for (String str : urlsToFile){
            writer.write(str + "\n");
        }
        writer.flush();
        writer.close();
    }

    private static String addTabs(int oblique) {
        return "\t".repeat(oblique - TABS_SUBTRAHEND);
    }
}
