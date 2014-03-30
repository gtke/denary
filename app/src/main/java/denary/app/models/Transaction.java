package denary.app.models;

/**
 * Created by gtkesh on 3/30/14.
 */
public class Transaction {
    private String name;
    private String tag;
    private double amount;
    private String type;

    public Transaction(String name, String tag, double amount, String type){
        this.name = name;
        this.tag = tag;
        this.amount = amount;
        this.type = type;
    }
}
