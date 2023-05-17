package model;

/**
 * Represents a contact in the system.
 */
public class Contacts {
    /**
     * The ID of the contact.
     */
    private int contact_id;

    /**
     * The name of the contact.
     */
    private String contact_name;

    /**
     * The email address of the contact.
     */
    private String email;

    /**
     * Constructs a new Contacts object with the specified ID, name, and email.
     *
     * @param contact_id the ID of the contact
     * @param contact_name the name of the contact
     * @param email the email address of the contact
     */
    public Contacts(int contact_id, String contact_name, String email) {
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.email = email;
    }

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Sets the ID of the contact.
     *
     * @param contact_id the new ID of the contact
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Returns the name of the contact.
     *
     * @return the name of the contact
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * Sets the name of the contact.
     *
     * @param contact_name the new name of the contact
     */
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    /**
     * Returns the email address of the contact.
     *
     * @return the email address of the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the contact.
     *
     * @param email the new email address of the contact
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

