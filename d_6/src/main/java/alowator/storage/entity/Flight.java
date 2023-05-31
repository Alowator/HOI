package alowator.storage.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flight {
    public String flightNo;
    public Set<Integer> daysOfWeek;
    public String time;

    public String origin;
    public String destination;

    public Flight(String flightNo, int dayOfWeek, String time, String origin, String destination) {
        this.flightNo = flightNo;
        this.daysOfWeek = new HashSet<>(List.of(dayOfWeek));
        this.time = time;
        this.origin = origin;
        this.destination = destination;
    }

    public Flight(String flightNo, Set<Integer> daysOfWeek, String time, String origin, String destination) {
        this.flightNo = flightNo;
        this.daysOfWeek = daysOfWeek;
        this.time = time;
        this.origin = origin;
        this.destination = destination;
    }
}
