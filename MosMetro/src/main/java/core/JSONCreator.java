package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JSONCreator {

    transient private String json = "";
    private List<Line> lines = new ArrayList<>();
    private Map<String, String[]> stations = new TreeMap<>(new LineComparator());

    public JSONCreator(List<Line> lines) {
        this.lines.addAll(lines);
        for (Line line : lines) {
            String[] stationsOnLine = line.getStations()
                    .stream()
                    .map(Station::getName)
                    .toArray(String[]::new);
            stations.put(line.getNumber(), stationsOnLine);
        }
    }

    public void createJSON() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        json = gson.toJson(this);
    }

    public void writeJSONtoFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.close();
            System.out.println("Файл успешно создан.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String[]> getStations() {
        return stations;
    }
}
