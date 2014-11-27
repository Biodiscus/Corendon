package nl.itopia.corendon.data.table;

import javafx.beans.property.SimpleStringProperty;

/**
 * © 2014, Biodiscus.net Robin
 */
public class TableUser {
    
    private final SimpleStringProperty userID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty userName;


    public TableUser(String userID, String userName, String firstName, String lastName) {
        
        this.userID = new SimpleStringProperty(userID);
        this.userName = new SimpleStringProperty(userName);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID.get();
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }
}
