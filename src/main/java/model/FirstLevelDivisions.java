package model;

import java.sql.Timestamp;

public class FirstLevelDivisions {
    /**
     * The ID of the division.
     */
    private int division_id;

    /**
     * The name of the division.
     */
    private String division;

    /**
     * The date the division was created.
     */
    private Timestamp create_date;

    /**
     * The user who created the division.
     */
    private String created_by;

    /**
     * The date the division was last updated.
     */
    private Timestamp last_update;

    /**
     * The user who last updated the division.
     */
    private String last_updated_by;

    /**
     * The ID of the country the division belongs to.
     */
    private int country_id;


    /**
     * Constructs a new instance of the {@code FirstLevelDivisions} class with the given properties.
     *
     * @param division_id The ID of the first-level division.
     * @param division The name of the first-level division.
     * @param create_date The date and time when the first-level division was created.
     * @param created_by The name of the user who created the first-level division.
     * @param last_update The date and time when the first-level division was last updated.
     * @param last_updated_by The name of the user who last updated the first-level division.
     * @param country_id The ID of the country to which the first-level division belongs.
     */
    public FirstLevelDivisions(int division_id, String division, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int country_id) {
        this.division_id = division_id;
        this.division = division;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.country_id = country_id;
    }

    /**
     * Gets the ID of the first-level division.
     *
     * @return The ID of the first-level division.
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * Sets the ID of the first-level division.
     *
     * @param division_id The ID of the first-level division.
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    /**
     * Gets the name of the first-level division.
     *
     * @return The name of the first-level division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the first-level division.
     *
     * @param division The name of the first-level division.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the date and time when the first-level division was created.
     *
     * @return The date and time when the first-level division was created.
     */
    public Timestamp getCreate_date() {
        return create_date;
    }

    /**
     * Sets the date and time when the first-level division was created.
     *
     * @param create_date The date and time when the first-level division was created.
     */
    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    /**
     * Gets the name of the user who created the first-level division.
     *
     * @return The name of the user who created the first-level division.
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * Sets the name of the user who created the first-level division.
     *
     * @param created_by The name of the user who created the first-level division.
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Gets the date and time when the first-level division was last updated.
     *
     * @return The date and time when the first-level division was last updated.
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * Sets the last update timestamp of this FirstLevelDivisions object.
     *
     * @param last_update the new last update timestamp
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * Returns the name of the user who last updated this FirstLevelDivisions object.
     *
     * @return the last updated by user name
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * Sets the name of the user who last updated this FirstLevelDivisions object.
     *
     * @param last_updated_by the new last updated by user name
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * Returns the ID of the country associated with this FirstLevelDivisions object.
     *
     * @return the country ID
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     * Sets the ID of the country associated with this FirstLevelDivisions object.
     *
     * @param country_id the new country ID
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

}
