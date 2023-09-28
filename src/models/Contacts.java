package models;

/**
 * Contacts class for contact objects.
 * @author Omar Ahmad.
 */
public class Contacts {
    /**
     *Contact id field.
     */
    private int contactId;
    /**
     *Contact name field.
     */
    private String contactName;

    /**
     * The constructor is used for creating new contacts.
     * @param contactId contact id
     * @param contactName contact name
     */

    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Getter and setter for contact id.
     * @return contact id.
     */
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for contact name.
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This is used to show contact name in contact combo box.
     * @return name string
     */
    @Override
    public String toString() {
        return( contactName );
    }
}
