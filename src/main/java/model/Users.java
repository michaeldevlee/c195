package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Users {
    /**

     The user ID.
     */
    private int user_id;
    /**

     The user name.
     */
    private String user_name;
    /**

     The user password.
     */
    private String password;
    /**

     The date when the user was created.
     */
    private LocalDate create_date;
    /**

     The user who created this user.
     */
    private String created_by;
    /**

     The timestamp of the last update of this user.
     */
    private Timestamp last_update;
    /**

     The user who made the last update of this user.
     */
    private String last_updated_by;
    /**

     Creates a new instance of the Users class with the given properties.
     @param user_id The user ID.
     @param user_name The user name.
     @param password The user password.
     @param create_date The date when the user was created.
     @param created_by The user who created this user.
     @param last_update The timestamp of the last update of this user.
     @param last_updated_by The user who made the last update of this user.
     */
    public Users(int user_id, String user_name, String password, LocalDate create_date, String created_by, Timestamp last_update, String last_updated_by) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
    }
    /**

     Returns the user ID.
     @return The user ID.
     */
    public int getUser_id() {
        return user_id;
    }
    /**

     Sets the user ID.
     @param user_id The user ID to set.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    /**

     Returns the user name.
     @return The user name.
     */
    public String getUser_name() {
        return user_name;
    }
    /**

     Sets the user name.
     @param user_name The user name to set.
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    /**

     Returns the user password.
     @return The user password.
     */
    public String getPassword() {
        return password;
    }
    /**

     Sets the user password.
     @param password The user password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**

     Returns the date when the user was created.
     @return The date when the user was created.
     */
    public LocalDate getCreate_date() {
        return create_date;
    }
    /**

     Sets the date when the user was created.
     @param create_date The date when the user was created to set.
     */
    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }
    /**

     Returns the user who created this user.
     @return The user who created this user.
     */
    public String getCreated_by() {
        return created_by;
    }
    /**

     Sets the user who created this user.
     @param created_by The user who created this user to set.
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * Returns the last update timestamp of this user.
     * @return The last update timestamp of this user.
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * Sets the last update timestamp of this user.
     * @param last_update The last update timestamp of this user.
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * Returns the name of the user who last updated this user's record.
     * @return The name of the user who last updated this user's record.
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * Sets the name of the user who last updated this user's record.
     * @param last_updated_by The name of the user who last updated this user's record.
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

}
