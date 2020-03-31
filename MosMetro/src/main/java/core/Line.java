package core;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>
{
    private String number;
    transient private Double realNumber;
    private String name;
    transient private List<Station> stations;

    public Line(String number, String name)
    {
        this.number = number;
        this.name = name;
        if (number.endsWith("А")) {realNumber = Double.parseDouble(number.replaceAll("А", "\\.5"));}
        else {realNumber = Double.parseDouble(number);}
        stations = new ArrayList<>();
    }

    public String getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public Double getRealNumber() {
        return realNumber;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public List<Station> getStations()
    {
        return stations;
    }

    @Override
    public int compareTo(Line line)
    {
        return realNumber.compareTo(line.getRealNumber());
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Line) obj) == 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
