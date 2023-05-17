package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Customers {
    /**
     * The ID of the customer.
     */
    private int customer_id;

    /**
     * The name of the customer.
     */
    private String customer_name;

    /**
     * The address of the customer.
     */
    private String address;

    /**
     * The postal code of the customer's address.
     */
    private String postal_code;

    /**
     * The phone number of the customer.
     */
    private String phone;

    /**
     * The date the customer was created.
     */
    private LocalDate create_date;

    /**
     * The user who created the customer.
     */
    private String created_by;

    /**
     * The timestamp of the last time the customer was updated.
     */
    private Timestamp last_update;

    /**
     * The user who last updated the customer.
     */
    private String last_updated_by;

    /**
     * The ID of the division that the customer is associated with.
     */
    private int division_id;


    /**
     * Creates a new customer object with the specified parameters.
     *
     * @param customer_id The unique ID of the customer.
     * @param customer_name The name of the customer.
     * @param address The address of the customer.
     * @param postal_code The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param create_date The date when the customer was created.
     * @param created_by The name of the user who created the customer.
     * @param last_update The timestamp when the customer was last updated.
     * @param last_updated The name of the user who last updated the customer.
     * @param division_id The ID of the division where the customer is located.
     */

    public Customers(int customer_id, String customer_name, String address, String postal_code, String phone, LocalDate create_date, String created_by, Timestamp last_update, String last_updated, int division_id) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated;
        this.division_id = division_id;
    }

    /**
     * Returns the ID of the customer.
     * @return the ID of the customer
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Sets the ID of the customer.
     * @param customer_id the ID of the customer to set
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Returns the name of the customer.
     * @return the name of the customer
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * Sets the name of the customer.
     * @param customer_name the name of the customer to set
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * Returns the address of the customer.
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address the address of the customer to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the postal code of the customer.
     * @return the postal code of the customer
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Sets the postal code of the customer.
     * @param postal_code the postal code of the customer to set
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Returns the phone number of the customer.
     * @return the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     * @param phone the phone number of the customer to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the creation date of the customer.
     * @return the creation date of the customer
     */
    public LocalDate getCreate_date() {
        return create_date;
    }

    /**
     * Sets the creation date of the customer.
     * @param create_date the creation date of the customer to set
     */
    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    /**
     * Returns the username of the user who created the customer.
     * @return the username of the user who created the customer
     */
    public String getCreated_by() {
        return created_by;
    }


    /**
     * Sets the name of the user who created the customer.
     * @param created_by the name of the user who created the customer
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Returns the timestamp of the last update made to the customer.
     * @return the timestamp of the last update made to the customer
     */
    public Timestamp getLast_update() {
        return last_update;
    }
    /**
     * Sets the timestamp of the last update made to the customer.
     * @param last_update the timestamp of the last update made to the customer
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
    /**
     * Returns the name of the user who last updated the customer.
     * @return the name of the user who last updated the customer
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }
    /**
     * Sets the name of the user who last updated the customer.
     * @param last_updated_by the name of the user who last updated the customer
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
    /**
     * Returns the ID of the division to which the customer belongs.
     * @return the ID of the division to which the customer belongs
     */
    public int getDivision_id() {
        return division_id;
    }
    /**
     * Sets the ID of the division to which the customer belongs.
     * @param division_id the ID of the division to which the customer belongs
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }
}
