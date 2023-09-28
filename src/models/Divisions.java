package models;
// Division class inheriting from Location
public class Divisions extends Location {
    private int country;

    public Divisions(int id, String name, int country){

        super( id, name);
        this.country = country;

    }

    public int getCountry() {
        return country;
    }
}
