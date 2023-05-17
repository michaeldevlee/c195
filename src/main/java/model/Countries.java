package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Countries {

    /**
     * The unique identifier for the country.
     */
    private int country_id;

    /**
     * The name of the country.
     */
    private String country;

    /**
     * The date when this country was added to the database.
     */
    private LocalDate create_date;

    /**
     * The user who added this country to the database.
     */
    private String created_by;

    /**
     * The date when this country was last updated in the database.
     */
    private Timestamp last_update;

    /**
     * The user who last updated this country in the database.
     */
    private String last_updated;



    /**

     Creates a new country with the specified information.
     @param country_id the ID of the country
     @param country the name of the country
     @param create_date the date the country was created
     @param created_by the user who created the country
     @param last_update the timestamp of the last update to the country
     @param last_updated the user who last updated the country
     */
    public Countries(int country_id, String country, LocalDate create_date, String created_by, Timestamp last_update, String last_updated) {
        this.country_id = country_id;
        this.country = country;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated = last_updated;
    }

    /**
     * Gets the country ID.
     * @return the country ID
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     * Sets the country ID.
     * @param country_id the country ID to set
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    /**
     * Gets the country name.
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country name.
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the date this country was created.
     * @return the create date
     */
    public LocalDate getCreate_date() {
        return create_date;
    }

    /**
     * Sets the date this country was created.
     * @param create_date the create date to set
     */
    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    /**
     * Gets the name of the user who created this country.
     * @return the name of the user who created this country
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * Sets the name of the user who created this country.
     * @param created_by the name of the user who created this country to set
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets the timestamp when this country was last updated.
     * @return the last update timestamp
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * Sets the timestamp when this country was last updated.
     * @param last_update the last update timestamp to set
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * Gets the name of the user who last updated this country.
     * @return the name of the user who last updated this country
     */
    public String getLast_updated() {
        return last_updated;
    }

    /**
     * Sets the name of the user who last updated this country.
     * @param last_updated the name of the user who last updated this country to set
     */
    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }
}
