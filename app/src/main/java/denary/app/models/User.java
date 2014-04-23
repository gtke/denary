package denary.app.models;

/**
 * Created by gtkesh on 3/30/14.
 */
public class User {
    private String name;
    private String email;
    private String password;
    private String hint;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public User(String name, String email, String password, String hint) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email){
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return this.name + " " + this.email;
    }
}
