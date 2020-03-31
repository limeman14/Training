import com.google.gson.Gson;
import core.JSONCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Loader {

    public static void main(String[] args) {

        final String PATH = "MosMetro/output.json";
        final String URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

        //Парсинг страницы Википедии
        WikiParser parser = new WikiParser(URL);
        parser.parseLines();
        parser.parseStations();

        //Запись результата в JSON-файл
        JSONCreator metro = new JSONCreator(parser.getLines());
        metro.createJSON();
        metro.writeJSONtoFile(PATH);

        //Подсчёт станций из JSON-файла
        countStations(readFile(PATH));
    }

    private static String readFile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private static void countStations(String input) {
        Gson gson = new Gson();
        JSONCreator jsonFile = gson.fromJson(input, JSONCreator.class);
        Map<String, String[]> stations = jsonFile.getStations();
        System.out.println("Количество станций по линиям: \n");
        for (String number : stations.keySet()) {
            System.out.println(number + " - " + stations.get(number).length + " станций.");
        }
    }
}
