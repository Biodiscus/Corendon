
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
    private SimpleIntegerProperty date;
    
    public TableLog(int ID, String user, int userID, String action, int date) {
        this.ID = new SimpleIntegerProperty(ID);
        this.user = new SimpleStringProperty(user);
        this.userID = new SimpleIntegerProperty(userID);
        this.action = new SimpleStringProperty(action);
        this.date = new SimpleIntegerProperty(date);
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
    public void setID(SimpleIntegerProperty ID) {
        this.ID = ID;
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
    public void setUser(SimpleStringProperty user) {
        this.user = user;
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
    public void setUserID(SimpleIntegerProperty userID) {
        this.userID = userID;
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
    public void setAction(SimpleStringProperty action) {
        this.action = action;
    }

    /**
     * @return the date
     */
    public int getDate() {
        return date.get();
    }

    /**
     * @param date the date to set
     */
    public void setDate(SimpleIntegerProperty date) {
        this.date = date;
    }
}
