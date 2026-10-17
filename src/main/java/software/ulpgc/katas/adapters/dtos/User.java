package software.ulpgc.katas.adapters.dtos;

public class User {
    public String gender;
    public Name name;
    public Location location;
    public String email;
    public String phone;
    public Picture picture;

    public class Name {
        public String title;
        public String first;
        public String last;
    }

    public class Location {
        public Street street;
        public String city;
        public String state;
        public String country;
        public String postCode;
    }

    public class Street {
        public int number;
        public String name;
    }

    public class Picture {
        public String large;
        public String medium;
    }
}
