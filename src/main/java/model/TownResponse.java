package model;

public class TownResponse {
    public static class Address {
        public String town;
        public String city;
        public String municipality;
        public String county;
        public String country;
        public String postcode;
    }

    public Address address;
}
