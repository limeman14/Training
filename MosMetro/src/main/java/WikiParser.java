import core.Line;
import core.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class WikiParser {

    private final String REGEX_LINE_NUMBER = "(0).+";
    private final int ZERO_POSITION_WITH_NUMBER = 1;
    private final int DOUBLE_LINE_POSITION1 = 8;
    private final int DOUBLE_LINE_POSITION2 = 11;

    private ArrayList<Line> lines = new ArrayList<>();
    private Document wiki;

    //*****************************************************\\
    public WikiParser(String URL) {
        try {
            wiki = Jsoup.connect(URL).maxBodySize(Integer.MAX_VALUE).get();
        }
        catch (IOException e) {e.printStackTrace();}
    }

    void parseLines() {
        for (Element row : wiki.select("table.standard:nth-child(7) tr")) {
            if (row.select("td:nth-of-type(1)").text().equals("")) {continue;}
            else {
                String name = row.select("td:nth-of-type(1) a").attr("title");
                String number = row.selectFirst("td:nth-child(1) span").text();
                if (number.matches(REGEX_LINE_NUMBER)) {number = number.substring(ZERO_POSITION_WITH_NUMBER);}
                if (!lines.contains(new Line(number, name))) {
                    lines.add(new Line(number, name));
                }
            }
        }
        lines.add(new Line("13", "Московский монорельс"));
        lines.add(new Line("14", "Московское центральное кольцо"));
        System.out.println("Список линий создан.");
    }

    void parseStations() {
        ///***Станции Московского Метро***\\\
        for (Element row : wiki.select("table.standard:nth-child(7) tr")) {
            if (row.select("td:nth-of-type(2)").text().equals("")) {continue;}
            else {
                String nameStation = row.select("td:nth-of-type(2) span a").text();
                //Единственная станция, которая не парсится указанным селектором, пришлось добавить вручную
                if (nameStation.equals("")) {nameStation = "Коммунарка";}

                //Первый блок необходим для станций которые находятся сразу на 2х линиях (8 и 11)
                if (row.select("td:nth-of-type(1)").attr("data-sort-value").equals("8.9")) {
                    lines.get(DOUBLE_LINE_POSITION1).addStation(new Station(nameStation, lines.get(DOUBLE_LINE_POSITION1)));
                    lines.get(DOUBLE_LINE_POSITION2).addStation(new Station(nameStation, lines.get(DOUBLE_LINE_POSITION2)));
                }
                else {
                    String nameLine = row.select("td:nth-of-type(1) a").attr("title");
                    for (Line line : lines) {
                        if (line.getName().equals(nameLine)) {
                            line.addStation(new Station(nameStation, line));
                        }
                    }
                }
            }
        }
        ///***Станции Московского Монорельса и МЦК***\\\
        for (Line line : lines) {
            if (line.getName().equals("Московский монорельс")) {
                for (Element row : wiki.select("table.standard:nth-child(9) tr")) {
                    if (row.select("td:nth-of-type(2)").text().equals("")) {continue;}
                    else {
                        String nameStation = row.select("td:nth-of-type(2) a").text();
                        line.addStation(new Station(nameStation, line));
                    }
                }
            }
            else if (line.getName().equals("Московское центральное кольцо")) {
                for (Element row : wiki.select("table.standard:nth-child(11) tr")) {
                    if (row.select("td:nth-of-type(2)").text().equals("")) {continue;}
                    else {
                        String nameStation = row.select("td:nth-of-type(2) a").text();
                        line.addStation(new Station(nameStation, line));
                    }
                }
            }
        }
        System.out.println("Список станций по линиям создан.");
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
}
