import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

public class MapCreator extends RecursiveAction
{
    final static String URL = "https://skillbox.ru/";

    private String url;
    private static Random random = new Random();

    public MapCreator(String url) {
        this.url = url;
    }

    @Override
    protected void compute()
    {
        try {
            Document document = getDocument(url);
            Elements links = document.getElementsByTag("a");

            if (links.isEmpty()) {return;}

            List<MapCreator> tasks = links.stream()
                    .map((link) -> link.absUrl("href"))
                    .filter(MapCreator::isThisSite)
                    .filter(URLStorage.uniqueUrlSet::add)
                    .peek(System.out::println)
                    .map(MapCreator::new)
                    .peek(MapCreator::fork)
                    .collect(Collectors.toList());

            tasks.forEach(ForkJoinTask::quietlyJoin);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static Document getDocument(String url) throws IOException, InterruptedException
    {
        Thread.sleep(50 + random.nextInt(100));
        return Jsoup.connect(url).userAgent("Mozilla").maxBodySize(0).execute().parse();
    }

    private static boolean isThisSite(String url) {
        return url.startsWith(URL) && url.endsWith("/");
    }
}
