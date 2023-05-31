package alowator.storage.entity;

public class Airport {
    public String code;
    public AirportName name;
    public City city;
    public String timezone;

    public Airport(String code, AirportName name, City city, String timezone) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.timezone = timezone;
    }

    public static class AirportName {
        public String en;
        public String ru;
    }
}
