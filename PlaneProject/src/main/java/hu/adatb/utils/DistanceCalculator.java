package hu.adatb.utils;

import hu.adatb.model.Airport;

import java.util.List;
import java.util.stream.Collectors;

public class DistanceCalculator {

    public static double Distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            return dist;
        }
    }


    public static double GetLatitudeByName(List<Airport> airports, String airportName) {
        var airport = airports.stream()
                .filter(airport1 -> airport1.getName().equals(airportName)).collect(Collectors.toList()).get(0);

        return airport.getLatitude();
    }

    public static double GetLongitudeByName(List<Airport> airports, String airportName) {
        var airport = airports.stream()
                .filter(airport1 -> airport1.getName().equals(airportName)).collect(Collectors.toList()).get(0);

        return airport.getLongitude();
    }
}
