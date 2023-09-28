package models;

/**
 * User class for user objects.
 * @author Omar Ahmad
 */
public class user {
    /**
     * User id field.
     */
    private int id;
    /**
     * User name field.
     */
    private String name;

    /**
     * Constructor for creating new users.
     * @param id user id
     * @param name user name
     */
    public user (int id, String name) {
        this.id =  id;
        this.name = name;
    }
    /**
     * Setter for user id.
     * @param id user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter user name.
     * @param name user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for user id.
     * @return user id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for user name
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * It shows user id and name  as a string in the user dropDown.
     * @return user id and name string
     */
    @Override
    public String toString() {
        return("User ID: " +id +" " + name );
    }
}
