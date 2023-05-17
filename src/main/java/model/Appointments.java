package model;

import java.sql.Timestamp;

/**

 Represents an appointment object.
 */
public class Appointments {
    /**

     The appointment ID.
     */
    private int appointment_id;
    /**

     The appointment title.
     */
    private String title;
    /**

     The appointment description.
     */
    private String description;
    /**

     The appointment location.
     */
    private String location;
    /**

     The appointment type.
     */
    private String type;
    /**

     The appointment start time.
     */
    private Timestamp start;
    /**

     The appointment end time.
     */
    private Timestamp end;
    /**

     The appointment create date.
     */
    private Timestamp create_date;
    /**

     The appointment created by.
     */
    private String created_by;
    /**

     The appointment last update.
     */
    private Timestamp last_update;
    /**

     The appointment last updated by.
     */
    private String last_updated_by;
    /**

     The customer ID associated with the appointment.
     */
    private int customer_id;
    /**

     The user ID associated with the appointment.
     */
    private int user_id;
    /**

     The contact ID associated with the appointment.
     */
    private int contact_id;
    /**

     Constructs an Appointment object with the given parameters.
     @param appointment_id the appointment ID
     @param title the appointment title
     @param description the appointment description
     @param location the appointment location
     @param type the appointment type
     @param start the appointment start time
     @param end the appointment end time
     @param create_date the appointment create date
     @param created_by the appointment created by
     @param last_update the appointment last update
     @param last_updated_by the appointment last updated by
     @param customer_id the customer ID associated with the appointment
     @param user_id the user ID associated with the appointment
     @param contact_id the contact ID associated with the appointment
     */
    public Appointments(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int customer_id, int user_id, int contact_id) {
        this.appointment_id = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }
    /**
     * Returns the appointment ID.
     *
     * @return the appointment ID
     */
    public int getAppointment_id() {
        return appointment_id;
    }


    /**
     * Sets the appointment ID.
     *
     * @param appointment_id the appointment ID to set
     */
    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the start timestamp.
     *
     * @return the start timestamp
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Sets the start timestamp.
     *
     * @param start the start timestamp to set
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Returns the end timestamp.
     *
     * @return the end timestamp
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Sets the end timestamp.
     *
     * @param end the end timestamp to set
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Returns the create date timestamp.
     *
     * @return the create date timestamp
     */
    public Timestamp getCreate_date() {
        return create_date;
    }

    /**
     * Sets the create date timestamp.
     *
     * @param create_date the create date timestamp to set
     */
    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    /**
     * Returns the created by user.
     *
     * @return the created by user
     */
    public String getCreated_by() {
        return created_by;
    }
    /**
     * Sets the name of the user who created this appointment.
     * @param created_by the name of the user who created this appointment.
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Returns the timestamp when this appointment was last updated.
     * @return the timestamp when this appointment was last updated.
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * Sets the timestamp when this appointment was last updated.
     * @param last_update the timestamp when this appointment was last updated.
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * Returns the name of the user who last updated this appointment.
     * @return the name of the user who last updated this appointment.
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * Sets the name of the user who last updated this appointment.
     * @param last_updated_by the name of the user who last updated this appointment.
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * Returns the ID of the customer associated with this appointment.
     * @return the ID of the customer associated with this appointment.
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Sets the ID of the customer associated with this appointment.
     * @param customer_id the ID of the customer associated with this appointment.
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Returns the ID of the user who created this appointment.
     * @return the ID of the user who created this appointment.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets the ID of the user who created this appointment.
     * @param user_id the ID of the user who created this appointment.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Returns the ID of the contact associated with this appointment.
     * @return the ID of the contact associated with this appointment.
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Sets the ID of the contact associated with this appointment.
     * @param contact_id the ID of the contact associated with this appointment.
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

}
