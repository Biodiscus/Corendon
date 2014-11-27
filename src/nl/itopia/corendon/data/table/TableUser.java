package nl.itopia.corendon.data.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * © 2014, Biodiscus.net Robin
 */
public class TableUser {
    
    private final SimpleIntegerProperty userID;
    private final SimpleStringProperty firstName, lastName, userName, role, airport;

    public TableUser(int uID, String uName, String fName, String lName, String role, String airport) {
        userID = new SimpleIntegerProperty(uID);
        userName = new SimpleStringProperty(uName);
        firstName = new SimpleStringProperty(fName);
        lastName = new SimpleStringProperty(lName);
        this.role = new SimpleStringProperty(role);
        this.airport = new SimpleStringProperty(airport);
    }
    
    public int getUserID()
    {
        return userID.get();
    }

    public String getAirport() {
        return airport.get();
    }

    public void setAirport(String airport) {
        this.airport.set(airport);
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }
}
