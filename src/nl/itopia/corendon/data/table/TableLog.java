package nl.itopia.corendon.data.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jeroentje
 */
public class TableLog {
    
    private SimpleIntegerProperty ID;
    private SimpleStringProperty user;
    private SimpleIntegerProperty userID;
    private SimpleStringProperty action;
    private SimpleStringProperty date;
    
    
    public TableLog(int ID, String user, int userID, String action, String date) {
        
        this.ID = new SimpleIntegerProperty(ID);
        this.user = new SimpleStringProperty(user);
        this.userID = new SimpleIntegerProperty(userID);
        this.action = new SimpleStringProperty(action);
        this.date = new SimpleStringProperty(date);
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID.get();
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID.set(ID);
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user.get();
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user.set(user);
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID.get();
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action.get();
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action.set(action);
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date.get();
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date.set(date);
    }
}
