import core.Line;
import core.Station;
import junit.framework.TestCase;
import java.util.List;


public class RouteCalculatorTest extends TestCase {
    private StationIndex stationIndex = new StationIndex();
    private RouteCalculator routeCalculator = new RouteCalculator(stationIndex);

    @Override
    protected void setUp() throws Exception {
        //Добавили линии
        stationIndex.addLine(new Line(1, "Красная"));
        stationIndex.addLine(new Line(2, "Синяя"));
        stationIndex.addLine(new Line(3, "Зелёная"));
        //Добавили станции
        stationIndex.getLine(1).addStation(new Station("Вокзальная", stationIndex.getLine(1)));
        stationIndex.getLine(1).addStation(new Station("Школьная", stationIndex.getLine(1)));
        stationIndex.getLine(1).addStation(new Station("Центральный стадион", stationIndex.getLine(1)));
        stationIndex.getLine(1).addStation(new Station("Волково", stationIndex.getLine(1)));
        stationIndex.getLine(2).addStation(new Station("Больница", stationIndex.getLine(2)));
        stationIndex.getLine(2).addStation(new Station("Марково", stationIndex.getLine(2)));
        stationIndex.getLine(2).addStation(new Station("Заводская", stationIndex.getLine(2)));
        stationIndex.getLine(3).addStation(new Station("Песочная", stationIndex.getLine(3)));
        stationIndex.getLine(3).addStation(new Station("Электротехническая", stationIndex.getLine(3)));
        stationIndex.getLine(3).addStation(new Station("Шоссейная", stationIndex.getLine(3)));
        //Добавление в индекс
        for (Station station:stationIndex.getLine(1).getStations()) {
            stationIndex.addStation(station);
        }
        for (Station station:stationIndex.getLine(2).getStations()) {
            stationIndex.addStation(station);
        }
        for (Station station:stationIndex.getLine(3).getStations()) {
            stationIndex.addStation(station);
        }
        //Добавили пересадки
        stationIndex.addConnection(List.of(stationIndex.getStation("Заводская"), stationIndex.getStation("Электротехническая")));
        stationIndex.addConnection(List.of(stationIndex.getStation("Центральный стадион"), stationIndex.getStation("Больница")));

    }
    public void testGetRouteOnTheLine() {
        Station from = stationIndex.getStation("Вокзальная");
        Station to = stationIndex.getStation("Волково");
        List<Station> expected = List.copyOf(stationIndex.getLine(1).getStations());
        List<Station> actual = routeCalculator.getShortestRoute(from, to);
        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnections() {
        Station from = stationIndex.getStation("Вокзальная");
        Station to = stationIndex.getStation("Марково");
        List<Station> expected = List.of(stationIndex.getStation("Вокзальная"), stationIndex.getStation("Школьная"),
                                         stationIndex.getStation("Центральный стадион"), stationIndex.getStation("Больница"),
                                         stationIndex.getStation("Марково"));
        List<Station> actual = routeCalculator.getShortestRoute(from, to);
        assertEquals(expected, actual);
    }

    public void testGetRouteWithTwoConnections() {
        Station from = stationIndex.getStation("Волково");
        Station to = stationIndex.getStation("Шоссейная");
        List<Station> expected = List.of(stationIndex.getStation("Волково"), stationIndex.getStation("Центральный стадион"),
                stationIndex.getStation("Больница"), stationIndex.getStation("Марково"),
                stationIndex.getStation("Заводская"), stationIndex.getStation("Электротехническая"),
                stationIndex.getStation("Шоссейная"));
        List<Station> actual = routeCalculator.getShortestRoute(from, to);
        assertEquals(expected, actual);
    }

    public void testCalculateDurationOnLine() {
        List<Station> route = routeCalculator.getShortestRoute(stationIndex.getStation("Вокзальная"), stationIndex.getStation("Волково"));
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 7.5;
        assertEquals(expected, actual);
    }

    public void testCalculateDurationOneConnection() {
        List<Station> route = routeCalculator.getShortestRoute(stationIndex.getStation("Вокзальная"), stationIndex.getStation("Марково"));
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 11.0;
        assertEquals(expected, actual);
    }

    public void testCalculateDurationTwoConnections() {
        List<Station> route = routeCalculator.getShortestRoute(stationIndex.getStation("Вокзальная"), stationIndex.getStation("Песочная"));
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 19.5;
        assertEquals(expected, actual);
    }
}
