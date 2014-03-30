package denary.app.models;

/**
 * Created by gtkesh on 3/30/14.
 */
public class Account {
    private String name;
    private double balance;

    public Account(String name, double balance){
        this.name = name;
        this.balance = balance;
    }

    public String toString(){
        return name + ": " + balance;
    }

}
