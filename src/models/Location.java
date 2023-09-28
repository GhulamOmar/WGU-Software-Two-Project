package models;
// Parent class
public class Location {
    private int id;
    private String name;
    private int country;

    public Location(int country) {
        this.country = country;
    }

    public int getCountry() {
        return country;
    }

    public Location setCountry(int country) {
        this.country = country;
        return this;
    }

    /**
     * The constructor is used to create new country and division objects.
     * The id and name for both child classes.
     *
     * @param id   the id
     * @param name the name
     */

    public Location(int id, String name) {
        this.id = id;
        this.name = name;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return "ID: " + id + " " + name;
    }



    }





