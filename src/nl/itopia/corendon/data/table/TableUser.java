package nl.itopia.corendon.data.table;

import javafx.beans.property.SimpleStringProperty;

/**
 * © 2014, Biodiscus.net Robin
 */
public class TableUser {
    
    private final SimpleStringProperty userID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    
    //private final SimpleStringProperty userName;
    private final SimpleStringProperty telephoneNumber;


    public TableUser(String fName, String lName, String tName, String uID) {
        
        userID = new SimpleStringProperty(uID);
        firstName = new SimpleStringProperty(fName);
        lastName = new SimpleStringProperty(lName);
        //userName = new SimpleStringProperty(uName);
        telephoneNumber = new SimpleStringProperty(tName);
    }
    
    public String getUserID()
    {
        return userID.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
//    public String getUserID() {
//        return firstName.get();
//    }
//
//    public void setUserID(String userID) {
//        this.userID.set(userID);
//    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
//    public String getUserName() {
//        return userName.get();
//    }
//
//    public void setUserName(String userName) {
//        this.userName.set(userName);
//    }

    public String getTelephoneNumber() {
        return telephoneNumber.get();
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(telephoneNumber);
    }
}
