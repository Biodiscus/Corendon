//This class represents the User that makes use of the system with 3 acceslevels
package nl.itopia.corendon.model;

public class User {
    String accountName;
    String password;
    int accessLevel;
    String description;
    User(String accountName, String password, int accessLevel, String description){
        this.accountName = accountName;
        this.password = password;
        this.accessLevel = accessLevel;
        this.description = description;
    }
    
    void addUser(){
        
    }
    
    void deleteUser(User x){
    }
    
}
