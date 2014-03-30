package denary.app.models;

/**
 * Created by gtkesh on 3/30/14.
 */
public class User {
    private String name;
    private String email;


    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String toString(){
        return this.name + " " + this.email;
    }
}
